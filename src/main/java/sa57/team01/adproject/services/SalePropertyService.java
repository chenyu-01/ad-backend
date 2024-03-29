package sa57.team01.adproject.services;

import org.springframework.stereotype.Service;
import sa57.team01.adproject.DTO.MixPropertyDTO;
import sa57.team01.adproject.models.SaleProperty;

import java.util.List;

@Service
public interface SalePropertyService {
    void saveSaleProperty(SaleProperty saleProperty);

    SaleProperty findSalePropertyById(Long id);

    void updateSaleProperty(SaleProperty saleProperty, MixPropertyDTO propertyDTO);
}
