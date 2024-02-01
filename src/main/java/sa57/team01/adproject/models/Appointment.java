package sa57.team01.adproject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long AppointmentId;

    private LocalDate date;

    @ManyToOne
    private Owner contactCustomer;

    @OneToOne
    private Property property;

    @ManyToOne
    private Customer requestCustomer;

    private AppointmentStatus status;
}
