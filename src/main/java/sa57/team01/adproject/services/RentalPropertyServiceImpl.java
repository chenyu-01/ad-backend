package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sa57.team01.adproject.DTO.MixPropertyDTO;
import sa57.team01.adproject.models.RentalProperty;
import sa57.team01.adproject.repositories.RentalPropertyReposity;

import java.util.List;

@Transactional
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


    @Override
    public RentalProperty findRentalPropertyById(Long id) {
        return rentalPropertyReposity.findById(id).orElse(null);
    }

    @Override
    public void updateRentalProperty(RentalProperty rentalProperty, MixPropertyDTO propertyDTO) {
        rentalProperty.updateProperty(propertyDTO);
        rentalPropertyReposity.save(rentalProperty);
    }


}
