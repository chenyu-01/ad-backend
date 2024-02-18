package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sa57.team01.adproject.DTO.AppointmentDTO;
import sa57.team01.adproject.models.*;
import sa57.team01.adproject.repositories.AppointmentReposity;
import sa57.team01.adproject.repositories.CustomerReposity;

import java.util.*;

@Transactional
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
    public List<AppointmentDTO> getAppointmentsByOwnerId(Owner owner){
        Long id = owner.getCustomerId();
        List<Appointment> appointmentList = appointmentReposity.findByOwnerId(id);
        List<AppointmentDTO> appointmentDTOList = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            appointmentDTOList.add(new AppointmentDTO(appointment));
        }
        return appointmentDTOList;
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
    public void createAppointment(Owner owner, Customer buyer, Property saleProperty, String appointmentDate) {
        try {
            Appointment appointment = new Appointment(owner, buyer, saleProperty, appointmentDate);
            appointmentReposity.save(appointment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void confirmAppointment(Long appointmentId) {
        Appointment appointment = appointmentReposity.findById(appointmentId).orElse(null);
        if (appointment != null) {
            appointment.setStatus(AppointmentStatus.accepted);
            appointmentReposity.save(appointment);
        }
    }


    @Override
    @Transactional
    public ResponseEntity<?> getAppointmentsByCustomerId(long customerId) {

        Customer customer = customerReposity.findById(customerId).orElse(null);
        if (customer == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }
        List<Appointment> appointmentList =  customer.getAppointmentRequestList();
        List<AppointmentDTO> appointmentDTOList = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            appointmentDTOList.add(new AppointmentDTO(appointment));
        }

        return new ResponseEntity<>(appointmentDTOList, HttpStatus.OK);
    }

    /*
    @Override
    public void addCommentToAppointment(Long id,String comment){

    }

     */
}
