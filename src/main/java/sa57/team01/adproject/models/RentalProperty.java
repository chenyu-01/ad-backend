package sa57.team01.adproject.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RentalProperty extends Property {
    private double rentalPrice;
    private int contractMonthPeriod;

    public RentalProperty() {
        super();
    }
}
