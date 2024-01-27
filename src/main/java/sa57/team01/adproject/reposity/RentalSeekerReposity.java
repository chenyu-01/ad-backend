package sa57.team01.adproject.reposity;

import org.springframework.data.jpa.repository.JpaRepository;
import sa57.team01.adproject.models.RentalSeeker;

public interface RentalSeekerReposity extends JpaRepository<RentalSeeker, Long> {
}
