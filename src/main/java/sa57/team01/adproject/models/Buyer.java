package sa57.team01.adproject.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Buyer extends Customer{

    public Buyer() {
        super();
    }

    @Override
    public void randomize() {
        super.randomize();
        this.setRole("buyer");
    }
}
