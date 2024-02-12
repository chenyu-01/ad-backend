package sa57.team01.adproject.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class AppointmentDTO implements Serializable {
    private Long id;
    private LocalDate date;
    private Long contactCustomerId;
    private Long propertyId;
    private Long requestCustomerId;
    private String status;

    public AppointmentDTO(Long id, LocalDate date, Long contactCustomerId, Long propertyId, Long requestCustomerId, String status) {
        this.id = id;
        this.date = date;
        this.contactCustomerId = contactCustomerId;
        this.propertyId = propertyId;
        this.requestCustomerId = requestCustomerId;
        this.status = status;
    }

    public AppointmentDTO(){}
    public AppointmentDTO(long appointmentId, LocalDate date) {
        this.id = appointmentId;
        this.date = date;
    }
}
