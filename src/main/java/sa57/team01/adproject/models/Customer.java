package sa57.team01.adproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long customerId;
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;

    private String contactNumber;
    @OneToOne
    private Preferences preferences;
    @NotNull
    private String Role;

    @OneToMany(mappedBy = "requestCustomer")
    private List<Appointment> appointmentRequestList;



    public Customer() {
        super();
    }

    public Customer(String name, String email, String password, String contactNumber, Preferences preferences) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.preferences = preferences;
    }

    public void randomize() {
        this.name = "Customer" + (int) (Math.random() * 1000);
        this.email = "customer" + (int) (Math.random() * 1000) + "@gmail.com";
        this.password = "password";
        this.contactNumber = 12345678 + (int) (Math.random() * 100000) + "";
    }

    public long getId() {
        return this.customerId;
    }
}
