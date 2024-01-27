package sa57.team01.adproject.reposity;

import org.springframework.data.jpa.repository.JpaRepository;
import sa57.team01.adproject.models.RentalProperty;

public interface RentalPropertyReposity extends JpaRepository<RentalProperty, Long> {
}
