package sa57.team01.adproject.services;


import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.*;

import java.util.List;
import java.util.Optional;

@Service
public interface AppointmentService {
    List<Appointment> getAppointmentsByOwnerId(Owner owner);

    List<Appointment> getAppointmentsByCustomerId(Customer customer);


    void saveAppointment(Appointment appointment);
    void cancelAppointment(Long id);
    void rejectAppointment(Long id);


    void createAppointment(Owner owner, Buyer buyer, Property saleProperty, String appointmentDate);

    //void addCommentToAppointment(Long id,String comment);


}
