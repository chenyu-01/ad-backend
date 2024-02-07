package sa57.team01.adproject.services;

import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.Preferences;

@Service
public interface PreferencesService {
    void savePreference(Preferences preferences);
}
