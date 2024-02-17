package sa57.team01.adproject.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import sa57.team01.adproject.DTO.SearchDTO;
import sa57.team01.adproject.models.Property;

import java.util.List;

public interface PropertyService {
    List<Property> getAllPropertiesWithSearchDTO(SearchDTO searchDTO);

    Property findPropertyById(Long id);


    Property saveProperty(Property property);

    ResponseEntity<?> uploadImage(long propertyId, MultipartFile file);

    void deleteAll();

    Property findById(Long id);
}
