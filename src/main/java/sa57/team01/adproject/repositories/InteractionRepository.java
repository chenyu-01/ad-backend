package sa57.team01.adproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sa57.team01.adproject.models.Interaction;

import java.util.List;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    @Query("Select i from Interaction i where i.userId=:userId and i.propertyId=:propertyId")
    Interaction findByUserIdAndPropertyId(Long userId, Long propertyId);

    List<Interaction> findByUserId(Long userId);
}
