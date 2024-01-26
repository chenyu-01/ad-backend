package sa57.team01.adproject.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class SaleProperty extends Property {

    private LocalDate leaseCommenceDate;
    private int remainingLease; // number of months

    private double resalePrice;

    public SaleProperty(){
        super();
    }
}

