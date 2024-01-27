package sa57.team01.adproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long customerId;

    private String name;
    @Email
    private String email;
    private String password;
    private String contactNumber;
    @OneToOne
    private Preferences preferences;

    @OneToMany(mappedBy = "requestCustomer")
    private List<Appointment> appointmentRequestList;



    public Customer() {
        super();
    }

    public Customer(long customerId, String name, String email, String password, String contactNumber, Preferences preferences) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.preferences = preferences;
    }


}
