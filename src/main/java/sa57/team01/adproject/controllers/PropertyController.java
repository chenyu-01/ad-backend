package sa57.team01.adproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

@RestController
@RequestMapping("/api/property")
public class PropertyController {

    private final RentalPropertyService rentalPropertyService;
    private final SalePropertyService salePropertyService;

    private final PropertyService propertyService;
    @Autowired
    public PropertyController(RentalPropertyService rentalPropertyService, SalePropertyService salePropertyService,PropertyService propertyService) {
        this.rentalPropertyService = rentalPropertyService;
        this.salePropertyService = salePropertyService;
        this.propertyService=propertyService;
    }
    @GetMapping("/rentlist/{page}")
    public ResponseEntity<?> getRentPropertiesInPage(@PathVariable int page) {
        // each page has 10 items
        List<RentalProperty> rentalProperties = rentalPropertyService.findRentalPropertyInPage(page);
        // convert to DTO
        List<RentalPropertyDTO> propertyDTOS = RentalPropertyDTO.from(rentalProperties);
        return ResponseEntity.ok(propertyDTOS);
    }

    @GetMapping("/salelist/{page}")
    public ResponseEntity<?> getSalePropertiesInPage(@PathVariable int page) {
        // each page has 10 items
        List<SaleProperty> saleProperties = salePropertyService.findSalePropertyInPage(page);
        List<SalePropertyDTO> salePropertyDTOS = SalePropertyDTO.from(saleProperties);
        return ResponseEntity.ok(salePropertyDTOS);
    }



    @PostMapping("/list/search/")
    public ResponseEntity<?> getListByPrice(@RequestBody SearchDTO searchDTO){

        try {
            List<Property> properties = propertyService.getAllPropertiesWithSearchDTO(searchDTO);
            if(properties.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // return 204 No Content
            }
            List<PropertyDTO> propertyDTOS = properties.stream().map(PropertyDTO::new).toList();
            return new ResponseEntity<>(propertyDTOS,HttpStatus.OK); // return 200 OK
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
}
