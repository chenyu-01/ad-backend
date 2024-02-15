package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sa57.team01.adproject.models.Preferences;
import sa57.team01.adproject.repositories.PreferencesRepository;

@Transactional
@Service
public class PreferencesServiceImpl implements PreferencesService {
    private final PreferencesRepository preferencesRepository;

    @Autowired
    public PreferencesServiceImpl(PreferencesRepository preferencesRepository){
        this.preferencesRepository = preferencesRepository;
    }

    @Override
    public void savePreference(Preferences preferences){
        preferencesRepository.save(preferences);
    }
}
