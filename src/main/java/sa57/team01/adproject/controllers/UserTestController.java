package sa57.team01.adproject.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sa57.team01.adproject.DTO.MixPropertyDTO;
import sa57.team01.adproject.DTO.PreferencesDTO;
import sa57.team01.adproject.DTO.ProfileDTO;
import sa57.team01.adproject.models.PropertyStatus;
import sa57.team01.adproject.models.TownName;
import sa57.team01.adproject.services.CustomerService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usertest")
public class UserTestController {
    public final CustomerService customerService;

    @Autowired
    public UserTestController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping("/getProfile")
    public ResponseEntity<?> getProfileByCustomerId() {


        return customerService.getProfile(2);
    }

    @PostMapping("/saveProfile")
    public ResponseEntity<?> saveProfileByCustomerId(
            @RequestBody ProfileDTO profileDTO, BindingResult result) {

        Map<String , Object> response = new HashMap<>();

        try{
            return customerService.saveProfile(2,profileDTO,result);

        }catch (Exception e){
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/getPreferences")
    public ResponseEntity<?> getPreferencesByCustomerId() {
        return customerService.getPreferences(2);
    }


    @PostMapping("/savePreferences")
    public ResponseEntity<?> savePreferencesByCustomerId(@RequestBody PreferencesDTO preferenceDTO, BindingResult result) {
        Map<String , Object> response = new HashMap<>();

        try{
            return customerService.savePreferences(1,preferenceDTO,result);

        }catch (Exception e){
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @PostMapping("/saveProperty")
    public ResponseEntity<?> savePropertyByCustomerId(@RequestBody MixPropertyDTO mixPropertyDTO, BindingResult result) {
        Map<String , Object> response = new HashMap<>();

        try{
            return customerService.saveProperty(2,mixPropertyDTO,result);

        }catch (Exception e){
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/getTownName")
    public ResponseEntity<?> getTownName(){
        return new ResponseEntity<>(Arrays.asList(TownName.values()).stream().map(Enum::name).toList(),HttpStatus.OK);
    }

    @GetMapping("/getPropertyStatus")
    public ResponseEntity<?> getPropertyStatus(){
        return new ResponseEntity<>(Arrays.asList(PropertyStatus.values()).stream().map(Enum::name).toList(),HttpStatus.OK);
    }


    @GetMapping("/getPropertyList")
    public ResponseEntity<?> getProperty(){
        return customerService.getPropertyLists(2);
    }

    @GetMapping("/savePropertyInfo/{propertyid}&{propertyStatus}")
    public ResponseEntity<?> savePropertyInfo(@PathVariable("propertyid") long propertyid,@PathVariable("propertyStatus") String propertyStatus,HttpSession session){
        Map<String, Object> response = new HashMap<>();
        session.setAttribute("propertyId",propertyid);
        session.setAttribute("propertyStatus",propertyStatus);
        if(session.getAttribute("propertyId") == null){
            response.put("message","No property");
            return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/getProperty")
    public ResponseEntity<?> getProperty(HttpSession session){
        Map<String, Object> response = new HashMap<>();
        if (session.getAttribute("customerId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first");
        }
        if(session.getAttribute("propertyId") == null){
            response.put("message","No property");
            return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
        }
        long propertyId = (long) session.getAttribute("propertyId");
        String propertyStatus = (String) session.getAttribute("propertyStatus");
        session.setAttribute("propertyId",null);
        session.setAttribute("propertyStatus",null);
        return customerService.getProperty(propertyId,propertyStatus);
    }


    @GetMapping("/getPropertyList/{page}&{itemsPerPage}")
    public ResponseEntity<?> getPropertyListByOwnerIdDivide(@PathVariable("page") int page,@PathVariable("itemsPerPage") int itemsPerPage,HttpSession session){
        if (session.getAttribute("customerId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first");
        }
        long id = (long) session.getAttribute("customerId");
        return customerService.getPropertyListsDivide(id,page,itemsPerPage);
    }


}