package sa57.team01.adproject.reposity;

import org.springframework.data.jpa.repository.JpaRepository;
import sa57.team01.adproject.models.Buyer;

public interface BuyerReposity extends JpaRepository<Buyer, Long> {
}
