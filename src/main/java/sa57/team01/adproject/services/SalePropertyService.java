package sa57.team01.adproject.services;

import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.RentalProperty;
import sa57.team01.adproject.models.SaleProperty;

import java.util.List;

@Service
public interface SalePropertyService {
    void saveSaleProperty(SaleProperty saleProperty);

    List<SaleProperty> findSalePropertyInPage(int page);
}
