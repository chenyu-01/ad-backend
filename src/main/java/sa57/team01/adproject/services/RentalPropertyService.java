package sa57.team01.adproject.services;

import org.springframework.stereotype.Service;
import sa57.team01.adproject.DTO.MixPropertyDTO;
import sa57.team01.adproject.models.RentalProperty;

import java.util.List;

@Service
public interface RentalPropertyService {
    void saveRentalProperty(RentalProperty rentalProperty);


    RentalProperty findRentalPropertyById(Long id);


    void updateRentalProperty(RentalProperty rentalProperty, MixPropertyDTO propertyDTO);
}
