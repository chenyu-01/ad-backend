package sa57.team01.adproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Table(name = "PropertyOwner")
@Entity
public class Owner extends Customer{

    @OneToMany(mappedBy = "owner") // owner is column name in Property table
    private List<Property> properties;

    @OneToMany(mappedBy = "contactCustomer") // contactCustomer is column name in Appointment table
    private List<Appointment> receivedAppointments;

    public Owner() {
        super();
    }

}
