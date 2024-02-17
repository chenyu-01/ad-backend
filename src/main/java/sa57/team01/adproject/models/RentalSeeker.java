package sa57.team01.adproject.models;

import jakarta.persistence.Entity;

@Entity
public class RentalSeeker extends Customer{

    public RentalSeeker() {
        super();
    }

    @Override
    public void randomize() {
        super.randomize();
        this.setRole("rentalSeeker");
    }
}
