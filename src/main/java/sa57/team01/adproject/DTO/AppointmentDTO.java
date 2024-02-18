package sa57.team01.adproject.DTO;

import lombok.Getter;
import lombok.Setter;
import sa57.team01.adproject.models.Appointment;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class AppointmentDTO implements Serializable {
    private Long id;
    private LocalDate date;
    private String contactCustomerName;
    private String requestCustomerName;
    private Long propertyId;
    private String contactNumber;
    private String requestNumber;
    private String status;
    private String propertyName;




    public AppointmentDTO(Appointment appointment) {
        this.id = appointment.getAppointmentId();
        this.date = appointment.getDate();
        this.contactCustomerName = appointment.getContactCustomer().getName();
        this.requestCustomerName = appointment.getRequestCustomer().getName();
        this.propertyId = appointment.getProperty().getPropertyid();
        this.contactNumber = appointment.getContactCustomer().getContactNumber();
        this.requestNumber = appointment.getRequestCustomer().getContactNumber();
        this.status = appointment.getStatus().toString();
        this.propertyName = appointment.getProperty().getStreetName();
    }
}
