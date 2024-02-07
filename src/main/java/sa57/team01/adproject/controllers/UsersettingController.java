package sa57.team01.adproject.controllers;

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
@RequestMapping("/api/usersetting")
public class UsersettingController {
    public final CustomerService customerService;

    @Autowired
    public UsersettingController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping("/getProfile/{id}")
    public ResponseEntity<?> getProfileByCustomerId(@PathVariable long id) {
        return customerService.getProfile(id);
    }

    @PostMapping("/saveProfile/{id}")
    public ResponseEntity<?> saveProfileByCustomerId(@PathVariable long id,
                                                    @RequestBody ProfileDTO profileDTO, BindingResult result) {
        Map<String , Object> response = new HashMap<>();
        try{
            return customerService.saveProfile(id,profileDTO,result);

        }catch (Exception e){
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/getPreferences/{id}")
    public ResponseEntity<?> getPreferencesByCustomerId(@PathVariable long id) {
        return customerService.getPreferences(id);
    }

    @PostMapping("/savePreferences/{id}")
    public ResponseEntity<?> savePreferencesByCustomerId(@PathVariable long id,
                                                         @RequestBody PreferencesDTO preferenceDTO, BindingResult result) {
        Map<String , Object> response = new HashMap<>();
        try{
            return customerService.savePreferences(id,preferenceDTO,result);

        }catch (Exception e){
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/saveProperty/{id}")
    public ResponseEntity<?> savePropertyByCustomerId(@PathVariable long id,
                                                      @RequestBody MixPropertyDTO mixPropertyDTO, BindingResult result) {
        Map<String , Object> response = new HashMap<>();
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

    @GetMapping("/getPropertyList/{id}")
    public ResponseEntity<?> getPropertyListByOwnerId(@PathVariable long id){
        return customerService.getPropertyLists(id);
    }
}
