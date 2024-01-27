package sa57.team01.adproject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Appointment {

    @Id
    private long AppointmentId;

    private Date date;

    @ManyToOne
    private Owner targetCustomer;

    @OneToOne
    private Property property;

    @ManyToOne
    private Customer requestCustomer;
}
