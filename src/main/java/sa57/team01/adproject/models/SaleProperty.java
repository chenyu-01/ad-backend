package sa57.team01.adproject.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class SaleProperty extends Property {

    @NotNull
    private LocalDate leaseCommenceDate;
    @NotNull
    private int remainingLease; // number of months
    @NotNull
    private double resalePrice;

    public SaleProperty(){
        super();
    }
}

