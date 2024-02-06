package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.*;
import sa57.team01.adproject.repositories.AppointmentReposity;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService{


    private AppointmentReposity appointmentReposity;

    @Autowired
    public AppointmentServiceImpl(AppointmentReposity appointmentReposity){
        this.appointmentReposity = appointmentReposity;
    }
    @Override
    public List<Appointment> getAppointmentsByOwnerId(Owner owner){
        Long id = owner.getCustomerId();
        return appointmentReposity.findByOwnerId(id);
    }

    @Override
    public List<Appointment> getAppointmentsByCustomerId(Customer customer){
        Long id = customer.getCustomerId();
        return appointmentReposity.findByCustomerId(id);
    }



    @Override
    public void saveAppointment(Appointment appointment){
        appointmentReposity.save(appointment);
    }
    @Override
    public void cancelAppointment(Long id){
        // TODO: delete or status
    }
    @Override
    public void rejectAppointment(Long id){
        // TODO:  status
    }

    @Override
    public void createAppointment(Owner owner, Buyer buyer, Property saleProperty, String appointmentDate) {
        try {
            Appointment appointment = new Appointment(owner, buyer, saleProperty, appointmentDate);
            appointmentReposity.save(appointment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    @Override
    public void addCommentToAppointment(Long id,String comment){

    }

     */
}
