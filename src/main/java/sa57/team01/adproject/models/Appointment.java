package sa57.team01.adproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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

    public Appointment(Owner owner, Customer buyer, Property saleProperty, String appointmentDate) {
        this.contactCustomer = owner;
        this.requestCustomer = buyer;
        this.property = saleProperty;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        this.date = ZonedDateTime.parse(appointmentDate, formatter).toLocalDate();
        this.status = AppointmentStatus.pending;
    }

    public Appointment() {
        this.status = AppointmentStatus.pending;
    }




}
