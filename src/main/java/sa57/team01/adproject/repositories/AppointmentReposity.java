package sa57.team01.adproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sa57.team01.adproject.models.Appointment;

import java.util.List;

@Repository
public interface AppointmentReposity extends JpaRepository<Appointment, Long> {
    @Query("select a from Appointment as a where a.contactCustomer.customerId = :owner_id")
    List<Appointment> findByOwnerId(@Param("owner_id") Long id);


    @Query("select a.AppointmentId,a.date from Appointment as a where a.requestCustomer.customerId = :customer_id")
    List <Appointment> findByCustomerId(@Param("customer_id") Long id);


}
