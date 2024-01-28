package sa57.team01.adproject.services;

import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.Buyer;
import sa57.team01.adproject.repositories.BuyerReposity;

@Service
public interface BuyerService {
    void saveBuyer(Buyer buyer);

}
