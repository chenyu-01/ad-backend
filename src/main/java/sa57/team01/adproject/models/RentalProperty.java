package sa57.team01.adproject.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RentalProperty extends Property {
    @NotNull
    private double rentalPrice;
    @NotNull
    private int contractMonthPeriod;

    public RentalProperty() {
        super();
    }
}
