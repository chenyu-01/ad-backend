package sa57.team01.adproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa57.team01.adproject.DTO.PropertyDTO;
import sa57.team01.adproject.DTO.RentalPropertyDTO;
import sa57.team01.adproject.DTO.SalePropertyDTO;
import sa57.team01.adproject.models.Property;
import sa57.team01.adproject.models.RentalProperty;
import sa57.team01.adproject.models.SaleProperty;
import sa57.team01.adproject.repositories.PropertyReposity;
import sa57.team01.adproject.services.PropertyService;
import sa57.team01.adproject.services.RentalPropertyService;
import sa57.team01.adproject.services.SalePropertyService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

//    @GetMapping("/listbydate")
//    public ResponseEntity<List<Property>> getListByDate(Date date){
//        List<Property>properties=propertyService.getListByDate(date);
//        if(properties.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(properties,HttpStatus.OK);
//    }

    @GetMapping("/list/price/{price}")
    public ResponseEntity<?> getListByPrice(@PathVariable double price){
        List<Property>properties=propertyService.getListByPrice(price);
//        List<RentalProperty> rentalProperties = rentalPropertyService.findRentalPropertyByPrice(price);
        if(properties.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<PropertyDTO> propertyDTOS = properties.stream().map(PropertyDTO::new).toList();
        return new ResponseEntity<>(propertyDTOS,HttpStatus.OK);
    }
    @GetMapping("/list/town/{town}")
    public ResponseEntity<?> getListByTown(@PathVariable String town){
        List<Property>properties=propertyService.getListByTown(town);
        if(properties.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<PropertyDTO> propertyDTOS = properties.stream().map(PropertyDTO::new).toList();
        return new ResponseEntity<>(propertyDTOS,HttpStatus.OK);
    }

    @GetMapping("/list/floorarea/{floorarea}")
    public ResponseEntity<?> getListByFloorarea(@PathVariable int floorarea){
        List<Property>properties=propertyService.getListByFloorArea(floorarea);
        if(properties.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<PropertyDTO> propertyDTOS = properties.stream().map(PropertyDTO::new).toList();
        return new ResponseEntity<>(propertyDTOS,HttpStatus.OK);
    }

    @GetMapping("/list/storey/{storey}")
    public ResponseEntity<?> getListByStorey(@PathVariable String storey){
        List<Property>properties=propertyService.getListByStoreyRange(storey);
        if(properties.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<PropertyDTO> propertyDTOS = properties.stream().map(PropertyDTO::new).toList();
        return new ResponseEntity<>(propertyDTOS,HttpStatus.OK);

    }

    @GetMapping("/list/street/{street}")
    public ResponseEntity<?> getListByStreet(@PathVariable String street){
        List<Property>properties=propertyService.getListByStreet(street);

        if(properties.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<PropertyDTO> propertyDTOS = properties.stream().map(PropertyDTO::new).toList();
        return new ResponseEntity<>(propertyDTOS,HttpStatus.OK);
    }



    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchProperties(@PathVariable String keyword) {
        // Todo: implement search
        return null;
    }

}
