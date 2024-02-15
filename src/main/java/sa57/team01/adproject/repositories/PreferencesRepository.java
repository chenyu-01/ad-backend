package sa57.team01.adproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sa57.team01.adproject.models.Preferences;

@Repository
public interface PreferencesRepository extends JpaRepository<Preferences, Long> {
}
