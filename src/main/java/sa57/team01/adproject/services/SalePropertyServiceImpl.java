package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.RentalProperty;
import sa57.team01.adproject.models.RentalSeeker;
import sa57.team01.adproject.models.SaleProperty;
import sa57.team01.adproject.repositories.RentalSeekerReposity;
import sa57.team01.adproject.repositories.SalePropertyReposity;

import java.util.List;

@Service
public class SalePropertyServiceImpl implements SalePropertyService{
    private final SalePropertyReposity salePropertyReposity;

    @Autowired
    public SalePropertyServiceImpl(SalePropertyReposity salePropertyReposity){
        this.salePropertyReposity = salePropertyReposity;
    }

    @Override
    public void saveSaleProperty(SaleProperty saleProperty){
        salePropertyReposity.save(saleProperty);
    }

    @Override
    public List<SaleProperty> findSalePropertyInPage(int page) {
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(page-1, pageSize);
        return salePropertyReposity.findAll(pageRequest).getContent();
    }
}
