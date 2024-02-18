package sa57.team01.adproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sa57.team01.adproject.models.Preferences;
import sa57.team01.adproject.models.Property;

import java.util.List;
import java.util.Optional;

@Repository
public interface PreferencesRepository extends JpaRepository<Preferences, Long> {
    @Query("select p from Preferences p where p.customer.customerId = :customerid")
    Optional<Preferences> findByCustomerId(long customerid);

}
