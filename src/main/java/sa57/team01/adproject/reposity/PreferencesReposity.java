package sa57.team01.adproject.reposity;

import org.springframework.data.jpa.repository.JpaRepository;
import sa57.team01.adproject.models.Preferences;

public interface PreferencesReposity extends JpaRepository<Preferences, Long> {
}
