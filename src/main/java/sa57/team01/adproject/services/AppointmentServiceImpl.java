package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sa57.team01.adproject.DTO.AppointmentDTO;
import sa57.team01.adproject.models.*;
import sa57.team01.adproject.repositories.AppointmentReposity;
import sa57.team01.adproject.repositories.CustomerReposity;

import java.util.*;

@Service
public class AppointmentServiceImpl implements AppointmentService{


    private AppointmentReposity appointmentReposity;
    public CustomerReposity customerReposity;


    @Autowired
    public AppointmentServiceImpl(AppointmentReposity appointmentReposity,CustomerReposity customerReposity){
        this.appointmentReposity = appointmentReposity;
        this.customerReposity = customerReposity;
    }
    @Override
    public List<Appointment> getAppointmentsByOwnerId(Owner owner){
        Long id = owner.getCustomerId();
        return appointmentReposity.findByOwnerId(id);
    }




    @Override
    public void saveAppointment(Appointment appointment){
        appointmentReposity.save(appointment);
    }
    @Override
        // TODO: delete or status
    public boolean cancelAppointment(long id) {
            if (appointmentReposity.existsById(id)) {
                appointmentReposity.deleteById(id);
                return true;
            } else {
                return false;
            }
        }

    @Override
    public void rejectAppointment(long id) {

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


    @Override
    public ResponseEntity<?> getAppointmentsByCustomerId(long customerId) {

        Customer customer = customerReposity.findByCustomerId(customerId);
        List<Appointment> appointmentList =  customer.getAppointmentRequestList();
        List<AppointmentDTO> appointmentDTOList = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            appointmentDTOList.add(new AppointmentDTO(appointment.getAppointmentId(), appointment.getDate()));
        }

        return new ResponseEntity<>(appointmentDTOList, HttpStatus.OK);
    }

    /*
    @Override
    public void addCommentToAppointment(Long id,String comment){

    }

     */
}
