package sa57.team01.adproject.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import sa57.team01.adproject.DTO.MixPropertyDTO;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class SaleProperty extends Property {

    @NotNull
    private LocalDate leaseCommenceDate;
    @NotNull
    private int remainingLease; // number of months


    public SaleProperty(){
        super();
    }

    @Override
    public void randomize() {
        super.randomize();
        this.setLeaseCommenceDate(LocalDate.of(1972, 5, 1).plusMonths((int) (Math.random() * 400) + 1));
        this.setRemainingLease(60 * 12 + (int) (Math.random() * 400) + 1);
        this.setForSale(true);
        this.setPropertyStatus(PropertyStatus.forSale);
    }

    public void updateProperty(MixPropertyDTO propertyDTO) {
        super.updateProperty(propertyDTO);
        this.setLeaseCommenceDate(LocalDate.parse(propertyDTO.getLeaseCommenceDate()));
    }
}

