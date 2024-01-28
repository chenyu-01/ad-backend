package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.Appointment;
import sa57.team01.adproject.models.Customer;
import sa57.team01.adproject.models.Owner;
import sa57.team01.adproject.repositories.AppointmentReposity;

import java.util.List;
import java.util.Optional;

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

    /*
    @Override
    public void addCommentToAppointment(Long id,String comment){

    }

     */
}
