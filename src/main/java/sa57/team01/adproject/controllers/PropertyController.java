package sa57.team01.adproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa57.team01.adproject.DTO.RentalPropertyDTO;
import sa57.team01.adproject.DTO.SalePropertyDTO;
import sa57.team01.adproject.models.RentalProperty;
import sa57.team01.adproject.models.SaleProperty;
import sa57.team01.adproject.services.RentalPropertyService;
import sa57.team01.adproject.services.SalePropertyService;

import java.util.List;

@RestController
@RequestMapping("/api/property")
public class PropertyController {

    private final RentalPropertyService rentalPropertyService;
    private final SalePropertyService salePropertyService;
    @Autowired
    public PropertyController(RentalPropertyService rentalPropertyService, SalePropertyService salePropertyService) {
        this.rentalPropertyService = rentalPropertyService;
        this.salePropertyService = salePropertyService;
    }
    @GetMapping("/rentlist/{page}")
    public ResponseEntity<?> getRentPropertiesInPage(@PathVariable int page) {
        // each page has 10 items
        List<RentalProperty> rentalProperties = rentalPropertyService.findRentalPropertyInPage(page);
        // convert to DTO
        List<RentalPropertyDTO> propertyDTOS = RentalPropertyDTO.from(rentalProperties);
        return ResponseEntity.ok(propertyDTOS);
    }

    @GetMapping("/salelist/{page}")
    public ResponseEntity<?> getSalePropertiesInPage(@PathVariable int page) {
        // each page has 10 items
        List<SaleProperty> saleProperties = salePropertyService.findSalePropertyInPage(page);
        List<SalePropertyDTO> salePropertyDTOS = SalePropertyDTO.from(saleProperties);
        return ResponseEntity.ok(salePropertyDTOS);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchProperties(@PathVariable String keyword) {
        // Todo: implement search
        return null;
    }

}
