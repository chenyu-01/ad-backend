package sa57.team01.adproject.services;

import sa57.team01.adproject.DTO.SearchDTO;
import sa57.team01.adproject.models.Property;

import java.util.List;

public interface PropertyService {
    List<Property> getAllPropertiesWithSearchDTO(SearchDTO searchDTO);




}
