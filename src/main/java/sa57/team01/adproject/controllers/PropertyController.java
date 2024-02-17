package sa57.team01.adproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sa57.team01.adproject.DTO.SearchDTO;
import sa57.team01.adproject.DTO.PropertyDTO;
import sa57.team01.adproject.DTO.RentalPropertyDTO;
import sa57.team01.adproject.DTO.SalePropertyDTO;
import sa57.team01.adproject.models.Customer;
import sa57.team01.adproject.models.Property;
import sa57.team01.adproject.models.RentalProperty;
import sa57.team01.adproject.models.SaleProperty;
import sa57.team01.adproject.services.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/property")
public class PropertyController {

    @Value("${upload.path}") // Define this property in your application.properties
    private String uploadDir;
    private final RentalPropertyService rentalPropertyService;
    private final SalePropertyService salePropertyService;
    private final CustomerService customerService;
    private final InteractionService interactionService;
    private final RecommendationService recommendationService;
    private final PredictorService predictorService;

    private final PropertyService propertyService;
    @Autowired
    public PropertyController(RentalPropertyService rentalPropertyService,
                              SalePropertyService salePropertyService,
                              PropertyService propertyService,
                              CustomerService customerService,
                              InteractionService interactionService,
                              RecommendationService recommendationService,
                              PredictorService predictorService)
    {
        this.rentalPropertyService = rentalPropertyService;
        this.salePropertyService = salePropertyService;
        this.propertyService=propertyService;
        this.customerService = customerService;
        this.interactionService = interactionService;
        this.recommendationService = recommendationService;
        this.predictorService = predictorService;
    }



    @PostMapping("/list/search/")
    public ResponseEntity<?> getListBySearchParams(@RequestBody SearchDTO searchDTO){
        try {
            List<Property> properties = propertyService.getAllPropertiesWithSearchDTO(searchDTO);
            if(properties.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // return 204 No Content
            }
            long page = searchDTO.getPage();
            List<PropertyDTO> propertyDTOS = properties.stream().skip(( page- 1) * 10).limit(10).map(PropertyDTO::new).toList();
            Map<String, Object> response = Map.of(
                    "properties", propertyDTOS,
                    "totalRecords", properties.size()
            );
            return new ResponseEntity<>(response,HttpStatus.OK); // return 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>( e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR); // return 500 Internal Server Error
        }
    }


    @GetMapping("/details/{id}")
    public ResponseEntity<?> getPropertyDetails(@PathVariable Long id) {
        RentalProperty rentalProperty = rentalPropertyService.findRentalPropertyById(id);
        if (rentalProperty == null) {
            SaleProperty saleProperty = salePropertyService.findSalePropertyById(id);
            if (saleProperty == null) {
                return ResponseEntity.notFound().build();
            }
            SalePropertyDTO salePropertyDTO = new SalePropertyDTO(saleProperty);
            return ResponseEntity.ok(salePropertyDTO);
        }
        RentalPropertyDTO rentalPropertyDTO = new RentalPropertyDTO(rentalProperty);
        return ResponseEntity.ok(rentalPropertyDTO);}

    @PostMapping("/upload/{propertyId}")
    public ResponseEntity<?> uploadImage(@PathVariable long propertyId, @RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }
        return propertyService.uploadImage(propertyId, file);
    }
    @GetMapping("/recommend/{id}")
    public ResponseEntity<?> getRecommendProperties(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        List<Long> result = null;

        List<Long> recommend_first = recommendationService.getRecommendationsForNewCustomer(customer);

        if (!interactionService.findByUserId(customer.getId()).isEmpty()) {
            List<Long> recommend_second = recommendationService.getRecommendationsForExistingCustomer(customer);

            //merge two list and remove duplicate, 3 from first and 7 from second
            Iterator<Long> it1 = recommend_first.iterator();
            Iterator<Long> it2 = recommend_second.iterator();
            result = Stream.generate(() -> {
                        if (it1.hasNext()) return it1.next();
                        if (it2.hasNext()) return it2.next();
                        return null;
                    })
                    .takeWhile(Objects::nonNull)
                    .distinct()
                    .limit(8)
                    .collect(Collectors.toList());
        }
        else{
            result = recommend_first;
        }

        List<Property> recommendedProperties = recommendationService.getRecommendedPropertiesByIds(result);
        List<PropertyDTO> propertyDTOS = recommendedProperties.stream()
                .map(PropertyDTO::new)
                .toList();

        Map<String, Object> response = Map.of(
                "properties", propertyDTOS,
                "totalRecords", propertyDTOS.size(),
                "status", HttpStatus.OK
        );
        return new ResponseEntity<>(response,HttpStatus.OK); // return 200 OK
    }

    @PostMapping("/list/sort/{sortDirection}")
    public ResponseEntity<?> sortProperties(@PathVariable("sortDirection") String sortDirection) {
        try {
            List<Property> properties = propertyService.getAllProperties();
            if (properties.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // return 204 No Content
            }

            // Sort properties by price
            properties = propertyService.sortProperties(properties, sortDirection);

            List<PropertyDTO> propertyDTOS = properties.stream()
                    .map(PropertyDTO::new)
                    .toList();
            Map<String, Object> response = Map.of(
                    "properties", propertyDTOS,
                    "totalRecords", properties.size()
            );
            return new ResponseEntity<>(response, HttpStatus.OK); // return 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // return 500 Internal Server Error
        }
    }

    @GetMapping("/predict/{id}")
    public ResponseEntity<?> getPredictProperties(@PathVariable Long id) {
        Property property = propertyService.findPropertyById(id);
        if (property == null) {
            return ResponseEntity.notFound().build();
        }
        // shall be sale property
        if (property instanceof SaleProperty) {

            List<Double> predict = predictorService.getPrediction((SaleProperty)property);
            return ResponseEntity.ok(predict);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dashboard/{id}")
    public ResponseEntity<?> getDashboard(@PathVariable Long id) {
        Property property = propertyService.findById(id);

        Map<String, Object> response = Map.of(
                "town", property.getTown().toString(),
                "price", property.getPrice(),
                "imageUrl", property.getImageUrl()
        );
        return ResponseEntity.ok(response);
    }
}
