package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sa57.team01.adproject.models.RentalSeeker;
import sa57.team01.adproject.repositories.RentalSeekerReposity;

@Transactional
@Service
public class RentalSeekerServiceImpl implements RentalSeekerService{
    private final RentalSeekerReposity rentalSeekerReposity;

    @Autowired
    public RentalSeekerServiceImpl(RentalSeekerReposity rentalSeekerReposity){
        this.rentalSeekerReposity = rentalSeekerReposity;
    }

    @Override
    public void saveRentalSeeker(RentalSeeker rentalSeeker){
        rentalSeekerReposity.save(rentalSeeker);
    }
}
