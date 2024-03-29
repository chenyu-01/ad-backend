package sa57.team01.adproject.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sa57.team01.adproject.DTO.*;
import sa57.team01.adproject.models.*;
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
    public ResponseEntity<?> getPropertyDetails(@PathVariable Long id, HttpSession session) {
        RentalProperty rentalProperty = rentalPropertyService.findRentalPropertyById(id);
        long userId;
        long customerId = (long) session.getAttribute("customerId");
        //get user id from session
        if (session.getAttribute("customerId") != null) {
            userId = (long) session.getAttribute("customerId");
        }else{// not login , goto main page
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (rentalProperty == null) {
            SaleProperty saleProperty = salePropertyService.findSalePropertyById(id);
            if (saleProperty == null) {
                return ResponseEntity.notFound().build();
            }
            updateInteraction(userId, id);

            SalePropertyDTO salePropertyDTO = new SalePropertyDTO(saleProperty);
            return ResponseEntity.ok(salePropertyDTO);
        }
        RentalPropertyDTO rentalPropertyDTO = new RentalPropertyDTO(rentalProperty);
        //record interaction
        updateInteraction(userId, id);
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

        if (interactionService.findByUserId(customer.getId()).isEmpty()) {
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
                    .limit(10)
                    .collect(Collectors.toList());
        }
        else{
            result = recommend_first;
        }


        return ResponseEntity.ok(result);
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
                "imageUrl", property.getImageUrl(),
                "id", property.getPropertyid()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/fetchImg/{propertyId}")
    public ResponseEntity<?> fetchImg(@PathVariable long propertyId){
        try {
            String imageUrl = propertyService.findPropertyById(propertyId).getImageUrl();
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private void updateInteraction(long userId, long id){

        if (interactionService.findByUserIdAndPropertyId(userId, id) != null) {
            //record interaction
            Interaction interaction = interactionService.findByUserIdAndPropertyId(userId, id);
            interaction.setTimes(interaction.getTimes() + 1);
            interactionService.saveInteraction(interaction);
        }else{
            //record interaction
            Interaction interaction = new Interaction();
            interaction.setUserId(userId);
            interaction.setPropertyId(id);
            interaction.setTimes(1);
            interactionService.saveInteraction(interaction);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable long id, @RequestBody MixPropertyDTO propertyDTO) {
        Property property = propertyService.findPropertyById(id);
        if (property == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PropertyStatus propertyStatus = property.getPropertyStatus();
        if (propertyStatus == PropertyStatus.soldOut || propertyStatus == PropertyStatus.rented) {
            return new ResponseEntity<>("Property is not available for update", HttpStatus.BAD_REQUEST);
        }
        if (propertyStatus == PropertyStatus.forRent && propertyDTO.getPropertyStatus() .equals("forSale") ||
                propertyStatus == PropertyStatus.forSale && propertyDTO.getPropertyStatus() .equals("forRent")){
            return new ResponseEntity<>("cannot change property type", HttpStatus.BAD_REQUEST);
        }
        try {
            if (property instanceof SaleProperty) {
                SaleProperty saleProperty = (SaleProperty) property;
                salePropertyService.updateSaleProperty(saleProperty, propertyDTO);
            } else {
                RentalProperty rentalProperty = (RentalProperty) property;
                rentalPropertyService.updateRentalProperty(rentalProperty, propertyDTO);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
