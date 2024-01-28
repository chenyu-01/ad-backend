package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.Preferences;
import sa57.team01.adproject.models.RentalProperty;
import sa57.team01.adproject.repositories.PreferencesReposity;
import sa57.team01.adproject.repositories.RentalPropertyReposity;

@Service
public class RentalPropertyServiceImpl implements RentalPropertyService{
    private final RentalPropertyReposity rentalPropertyReposity;

    @Autowired
    public RentalPropertyServiceImpl(RentalPropertyReposity rentalPropertyReposity){
        this.rentalPropertyReposity = rentalPropertyReposity;
    }

    @Override
    public void saveRentalProperty(RentalProperty rentalProperty){
        rentalPropertyReposity.save(rentalProperty);
    }
}