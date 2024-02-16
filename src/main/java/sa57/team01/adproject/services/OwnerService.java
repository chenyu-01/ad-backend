package sa57.team01.adproject.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sa57.team01.adproject.models.Buyer;
import sa57.team01.adproject.models.Owner;

@Service
public interface OwnerService {
    void saveOwner(Owner owner);

    Owner findOwnerById(long id);
}
