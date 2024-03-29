package sa57.team01.adproject.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
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
    ResponseEntity<?> savePreferences(long id, Map<String,Object> preferencesDTO, BindingResult result);
    ResponseEntity<?> saveProperty(long id, MixPropertyDTO mixPropertyDTO, BindingResult result);
    ResponseEntity<?> getPropertyLists(long id);

    ResponseEntity<?> saveByRole(Map<String, String> credentials);

    ResponseEntity<?> getProperty(long id,String status);

    ResponseEntity<?> getPropertyListsDivide(long id, int page, int itemsPerPage);
    ResponseEntity<?> deleteProperty(long propertyid);


    Customer findById(long customerId);

    ResponseEntity<?> uploadImage(String propertyid,MultipartFile file);

    ResponseEntity<?> fetchImg(long propertyId);
}
