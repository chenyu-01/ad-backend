package sa57.team01.adproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Appointment {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long AppointmentId;

    @NotNull
    private LocalDate date;

    @NotNull
    @ManyToOne
    private Owner contactCustomer;

    @NotNull
    @OneToOne
    private Property property;

    @NotNull
    @ManyToOne
    private Customer requestCustomer;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    public Appointment(Owner owner, Buyer buyer, Property saleProperty, String appointmentDate) {

    }

    public Appointment() {
        this.status = AppointmentStatus.pending;
    }




}
