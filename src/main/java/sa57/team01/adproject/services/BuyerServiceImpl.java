package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sa57.team01.adproject.models.Buyer;
import sa57.team01.adproject.repositories.BuyerReposity;

@Transactional
@Service
public class BuyerServiceImpl implements BuyerService {

    private final BuyerReposity buyerReposity;

    @Autowired
    public BuyerServiceImpl(BuyerReposity buyerReposity) {
        this.buyerReposity = buyerReposity;
    }

    @Override
    public void saveBuyer(Buyer buyer) {
        buyerReposity.save(buyer);
    }

    @Override
    public Buyer findBuyerById(long id) {
        return buyerReposity.findById(id).orElse(null);
    }
}
