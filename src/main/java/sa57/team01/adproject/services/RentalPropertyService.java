package sa57.team01.adproject.services;

import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.RentalProperty;

import java.util.List;

@Service
public interface RentalPropertyService {
    void saveRentalProperty(RentalProperty rentalProperty);

    List<RentalProperty> findRentalPropertyInPage(int page);

    RentalProperty findRentalPropertyById(Long id);

    long countRentalProperty();
}
