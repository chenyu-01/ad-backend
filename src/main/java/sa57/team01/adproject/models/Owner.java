package sa57.team01.adproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Table(name = "PropertyOwner")
@Entity
public class Owner extends Customer{

    private List<Property> properties;

    public Owner() {
        super();
    }

}
