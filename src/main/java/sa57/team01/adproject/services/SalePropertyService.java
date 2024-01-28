package sa57.team01.adproject.services;

import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.SaleProperty;

@Service
public interface SalePropertyService {
    void saveSaleProperty(SaleProperty saleProperty);
}
