package sa57.team01.adproject.DTO;

import lombok.Getter;
import lombok.Setter;
import sa57.team01.adproject.models.SaleProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class SalePropertyDTO extends PropertyDTO implements Serializable{

    private LocalDate leaseCommenceDate;
    private int remainingLease; // number of months
    public SalePropertyDTO(SaleProperty other) {
        super(other);
        this.leaseCommenceDate = other.getLeaseCommenceDate();
        this.remainingLease = other.getRemainingLease();
    }

}
