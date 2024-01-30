package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.RentalProperty;
import sa57.team01.adproject.repositories.RentalPropertyReposity;

import java.util.List;

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
    public List<RentalProperty> findRentalPropertyInPage(int page){
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(page-1, pageSize);
        return rentalPropertyReposity.findAll(pageRequest).getContent();
    }
}
