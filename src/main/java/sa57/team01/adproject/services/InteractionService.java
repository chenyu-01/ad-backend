package sa57.team01.adproject.services;

import sa57.team01.adproject.models.Interaction;

import java.util.List;

public interface InteractionService {
    void saveInteraction(Interaction interaction);

    Interaction findByUserIdAndPropertyId(Long userId, Long propertyId);

    List<Interaction> findByUserId(Long userId);
}
