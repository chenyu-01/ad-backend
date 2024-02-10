package sa57.team01.adproject.services;


import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.*;

import java.util.List;

@Service
public interface AppointmentService {
    List<Appointment> getAppointmentsByOwnerId(Owner owner);

    List<Appointment> getAppointmentsByCustomerId(Customer customer);

    List<Appointment> getAppointments();



    void saveAppointment(Appointment appointment);

    void rejectAppointment(Long id);

    void createAppointment(Owner owner, Buyer buyer, Property saleProperty, String appointmentDate);



    boolean cancelAppointment(long appointmentId);

    //void addCommentToAppointment(Long id,String comment);

}
