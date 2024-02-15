package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.Interaction;
import sa57.team01.adproject.repositories.InteractionRepository;

import java.util.List;

@Service
public class InteractionServiceImpl implements InteractionService {
    private final InteractionRepository interactionRepository;

    @Autowired
    public InteractionServiceImpl(InteractionRepository interactionRepository){
        this.interactionRepository = interactionRepository;
    }

    public void saveInteraction(Interaction interaction){
        interactionRepository.save(interaction);
    }

    public Interaction findByUserIdAndPropertyId(Long userId, Long propertyId){
        return interactionRepository.findByUserIdAndPropertyId(userId, propertyId);
    }

    @Override
    public List<Interaction> findByUserId(Long userId) {
        return interactionRepository.findByUserId(userId);
    }
}
