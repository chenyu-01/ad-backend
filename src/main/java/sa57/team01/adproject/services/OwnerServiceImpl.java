package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.Buyer;
import sa57.team01.adproject.models.Owner;
import sa57.team01.adproject.repositories.BuyerReposity;
import sa57.team01.adproject.repositories.OwnerReposity;

@Service
public class OwnerServiceImpl implements OwnerService{
    private final OwnerReposity ownerReposity;

    @Autowired
    public OwnerServiceImpl(OwnerReposity ownerReposity){
        this.ownerReposity = ownerReposity;
    }

    @Override
    public void saveOwner(Owner owner){
        ownerReposity.save(owner);
    }

    @Override
    public Owner findOwnerById(long id){
        return ownerReposity.findById(id).orElse(null);
    }
}
