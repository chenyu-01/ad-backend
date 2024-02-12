package sa57.team01.adproject.services;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.*;

import java.util.List;

@Service
public interface AppointmentService {
    List<Appointment> getAppointmentsByOwnerId(Owner owner);

    ResponseEntity<?> getAppointmentsByCustomerId(long customerId);

   // ResponseEntity<?> getAppointmentsByCustomerId(long id);


    void saveAppointment(Appointment appointment);
    boolean cancelAppointment(long id);
    void rejectAppointment(long id);


    void createAppointment(Owner owner, Buyer buyer, Property saleProperty, String appointmentDate);

    //void addCommentToAppointment(Long id,String comment);


}
