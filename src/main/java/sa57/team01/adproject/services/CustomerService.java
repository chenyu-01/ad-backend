package sa57.team01.adproject.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import sa57.team01.adproject.DTO.*;
import sa57.team01.adproject.models.Customer;

import java.util.Map;

@Service
public interface CustomerService {
    Customer findByEmail(String email);
    Customer save(Customer customer);
    ResponseEntity<?> getProfile(long id);
    ResponseEntity<?> saveProfile(long id,ProfileDTO profileDTO, BindingResult result);
    ResponseEntity<?> getPreferences(long id);
    ResponseEntity<?> savePreferences(long id, PreferencesDTO preferencesDTO, BindingResult result);
    ResponseEntity<?> saveProperty(long id, MixPropertyDTO mixPropertyDTO, BindingResult result);
    ResponseEntity<?> getPropertyLists(long id);

    ResponseEntity<?> saveByRole(Map<String, String> credentials);

    ResponseEntity<?> getProperty(long id,String status);


}
