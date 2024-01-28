package sa57.team01.adproject.services;

import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.RentalSeeker;

@Service
public interface RentalSeekerService {
    void saveRentalSeeker(RentalSeeker rentalSeeker);
}
