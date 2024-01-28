package sa57.team01.adproject.services;

import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.RentalProperty;

@Service
public interface RentalPropertyService {
    void saveRentalProperty(RentalProperty rentalProperty);
}
