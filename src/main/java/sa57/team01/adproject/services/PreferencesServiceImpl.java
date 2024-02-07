package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.Preferences;
import sa57.team01.adproject.repositories.PreferencesReposity;

@Service
public class PreferencesServiceImpl implements PreferencesService {
    private final PreferencesReposity preferencesReposity;

    @Autowired
    public PreferencesServiceImpl(PreferencesReposity preferencesReposity){
        this.preferencesReposity = preferencesReposity;
    }

    @Override
    public void savePreference(Preferences preferences){
        preferencesReposity.save(preferences);
    }
}
