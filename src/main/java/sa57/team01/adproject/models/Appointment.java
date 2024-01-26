package sa57.team01.adproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity
public class Appointment {

    @Id
    private long AppointmentId;

    private Date date;
    private String requestPerson;
    private String contactPerson;

    @OneToOne
    private Property property;

    @ManyToOne
    private Customer customer;
}
