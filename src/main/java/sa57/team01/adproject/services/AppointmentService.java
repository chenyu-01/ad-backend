package sa57.team01.adproject.services;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sa57.team01.adproject.DTO.AppointmentDTO;
import sa57.team01.adproject.models.*;

import java.util.List;

@Service
public interface AppointmentService {
    List<AppointmentDTO> getAppointmentsByOwnerId(Owner owner);

    ResponseEntity<?> getAppointmentsByCustomerId(long customerId);

   // ResponseEntity<?> getAppointmentsByCustomerId(long id);


    void saveAppointment(Appointment appointment);
    boolean cancelAppointment(long id);
    void rejectAppointment(long id);


    void createAppointment(Owner owner, Customer buyer, Property saleProperty, String appointmentDate);

    void confirmAppointment(Long appointmentId);

    //void addCommentToAppointment(Long id,String comment);


}
