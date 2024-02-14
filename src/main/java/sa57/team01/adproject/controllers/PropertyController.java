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
import sa57.team01.adproject.models.Property;
import sa57.team01.adproject.models.RentalProperty;
import sa57.team01.adproject.models.SaleProperty;
import sa57.team01.adproject.services.PropertyService;
import sa57.team01.adproject.services.RentalPropertyService;
import sa57.team01.adproject.services.SalePropertyService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/property")
public class PropertyController {

    @Value("${upload.path}") // Define this property in your application.properties
    private String uploadDir;
    private final RentalPropertyService rentalPropertyService;
    private final SalePropertyService salePropertyService;

    private final PropertyService propertyService;
    @Autowired
    public PropertyController(RentalPropertyService rentalPropertyService, SalePropertyService salePropertyService,PropertyService propertyService) {
        this.rentalPropertyService = rentalPropertyService;
        this.salePropertyService = salePropertyService;
        this.propertyService=propertyService;
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

    @PostMapping("/list/sort/")
    public ResponseEntity<?> sortProperties(@RequestParam String sortBy, @RequestParam(defaultValue = "asc") String sortDirection) {
        try {
            List<Property> properties = propertyService.getAllProperties();
            if (properties.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // return 204 No Content
            }
            properties = propertyService.sortProperties(properties, sortBy, sortDirection);
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


}
