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
    private int contractMonthPeriod;

    public RentalProperty() {
        super();
    }

    @Override
    public void randomize() {
        super.randomize();
        this.setContractMonthPeriod((int) (Math.random() * 12) + 1);
        this.setForSale(false);
        this.setPropertyStatus(PropertyStatus.forRent);
    }
}
