package sa57.team01.adproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Customer {
    @Id
    private long customerId;

    private String name;
    @Email
    private String email;
    private String password;
}
