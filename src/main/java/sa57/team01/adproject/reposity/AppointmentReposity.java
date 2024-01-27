package sa57.team01.adproject.reposity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sa57.team01.adproject.models.Appointment;

@Repository
public interface AppointmentReposity extends JpaRepository<Appointment, Long> {
}
