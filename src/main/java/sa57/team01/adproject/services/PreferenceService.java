package sa57.team01.adproject.services;

import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.Preferences;

@Service
public interface PreferenceService {
    void savePreference(Preferences preferences);
}
