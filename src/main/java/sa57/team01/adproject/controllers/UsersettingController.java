package sa57.team01.adproject.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sa57.team01.adproject.DTO.CustomerDTO;
import sa57.team01.adproject.DTO.MixPropertyDTO;
import sa57.team01.adproject.DTO.PreferencesDTO;
import sa57.team01.adproject.DTO.ProfileDTO;
import sa57.team01.adproject.models.Customer;
import sa57.team01.adproject.models.PropertyStatus;
import sa57.team01.adproject.models.TownName;
import sa57.team01.adproject.repositories.CustomerReposity;
import sa57.team01.adproject.services.CustomerService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usersetting")
public class UsersettingController {
    public final CustomerService customerService;
    public final CustomerReposity customerReposity;

    @Autowired
    public UsersettingController(CustomerService customerService,CustomerReposity customerReposity){

        this.customerService = customerService;
        this.customerReposity = customerReposity;
    }

    @GetMapping("/getProfile")
    public ResponseEntity<?> getProfileByCustomerId(HttpSession session) {
        if (session.getAttribute("customerId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first");
        }
        long id = (long) session.getAttribute("customerId");
        return customerService.getProfile(id);
    }


    @PostMapping("/saveProfile")
    public ResponseEntity<?> saveProfileByCustomerId(HttpSession session,
                                                    @RequestBody ProfileDTO profileDTO, BindingResult result) {
        if (session.getAttribute("customerId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first");
        }
        Map<String , Object> response = new HashMap<>();
        long id = (long) session.getAttribute("customerId");
        try{
            return customerService.saveProfile(id,profileDTO,result);

        }catch (Exception e){
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
    }



    @GetMapping("/getPreferences")
    public ResponseEntity<?> getPreferencesByCustomerId(HttpSession session) {
        if (session.getAttribute("customerId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first");
        }
        long id = (long) session.getAttribute("customerId");
        return customerService.getPreferences(id);
    }


    @PostMapping("/savePreferences")
    public ResponseEntity<?> savePreferencesByCustomerId(HttpSession session,
                                                         @RequestBody PreferencesDTO preferenceDTO, BindingResult result) {
        Map<String , Object> response = new HashMap<>();
        if (session.getAttribute("customerId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first");
        }
        long id = (long) session.getAttribute("customerId");
        try{
            return customerService.savePreferences(id,preferenceDTO,result);

        }catch (Exception e){
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
    }



    @PostMapping("/saveProperty")
    public ResponseEntity<?> savePropertyByCustomerId(HttpSession session,
                                                      @RequestBody MixPropertyDTO mixPropertyDTO, BindingResult result) {
        Map<String , Object> response = new HashMap<>();
        if (session.getAttribute("customerId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first");
        }
        long id = (long) session.getAttribute("customerId");
        try{
            return customerService.saveProperty(id,mixPropertyDTO,result);

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
    public ResponseEntity<?> getPropertyListByOwnerId(HttpSession session){
        if (session.getAttribute("customerId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first");
        }
        long id = (long) session.getAttribute("customerId");
        return customerService.getPropertyLists(id);
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


    @GetMapping("/getRole")
    public ResponseEntity<?> getPropertyListByOwnerIdDivide(HttpSession session){
        Map<String, Object> response = new HashMap<>();
        if (session.getAttribute("customerId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first");
        }
        long id = (long) session.getAttribute("customerId");
        Customer customer = customerReposity.findById(id).get();
        String role = customer.getRole();
        response.put("role",role);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/delProperty/{propertyid}")
    public ResponseEntity<?> deleteProperty(@PathVariable("propertyid") long propertyid){
        return customerService.deleteProperty(propertyid);
    }


    @PostMapping("/upload/{propertyId}")
    public ResponseEntity<?> uploadImage(@PathVariable String propertyId, @RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }
        return customerService.uploadImage(propertyId, file);
    }

    @GetMapping("/fetchImg/{propertyId}")
    public ResponseEntity<?> fetchImg(@PathVariable long propertyId){
        return customerService.fetchImg(propertyId);
    }

}
