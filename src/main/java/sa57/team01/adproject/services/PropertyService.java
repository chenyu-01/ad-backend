package sa57.team01.adproject.services;

import org.springframework.http.ResponseEntity;
import sa57.team01.adproject.models.Property;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PropertyService {
    List<Property> getAllProperties();

    Optional<Property> getPropertyById(Long id);

//    List<Property> getListByDate(Date date);

    List<Property> getListByPrice(double price);

    List<Property> getListByTown(String town);

    List<Property> getListByStreet(String street);
    List<Property> getListByStoreyRange(String storey);

    List<Property> getListByFloorArea(int floorarea);


}
