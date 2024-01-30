package sa57.team01.adproject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa57.team01.adproject.models.Property;
import sa57.team01.adproject.services.PropertyService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/property")
public class PropertyController {

    @Autowired
    public PropertyService propertyService;

    @GetMapping("/list")
    public ResponseEntity<List<Property>> getAllProperties(){
        List<Property>properties = propertyService.getAllProperties();
        if(properties.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(properties,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id){
        Optional <Property> propertyOptional = propertyService.getPropertyById(id);
        return propertyOptional
                .map(property -> ResponseEntity.ok(property))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/listbydate")
    public ResponseEntity<List<Property>> getListByDate(Date date){
        List<Property>properties=propertyService.getListByDate(date);
        if(properties.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(properties,HttpStatus.OK);
    }

    @GetMapping("/listbyprice")
    public ResponseEntity<List<Property>> getListByPrice(double price){
        List<Property>properties=propertyService.getListByPrice(price);
        if(properties.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(properties,HttpStatus.OK);
    }
    @GetMapping("/listbytown")
    public ResponseEntity<List<Property>> getListByTown(String town){
        List<Property>properties=propertyService.getListByTown(town);
        if(properties.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(properties,HttpStatus.OK);
    }

    @GetMapping("/listbyfloorarea")
    public ResponseEntity<List<Property>> getListByFloorarea(int floorarea){
        List<Property>properties=propertyService.getListByFloorArea(floorarea);
        if(properties.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(properties,HttpStatus.OK);
    }

    @GetMapping("/listbystorey")
    public ResponseEntity<List<Property>> getListByStorey(String storey){
        List<Property>properties=propertyService.getListByStoreyRange(storey);
        if(properties.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(properties,HttpStatus.OK);

    }

    @GetMapping("/listbystreet")
    public ResponseEntity<List<Property>> getListByStreet(String street){
        List<Property>properties=propertyService.getListByStreet(street);

        if(properties.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(properties,HttpStatus.OK);
    }


}
