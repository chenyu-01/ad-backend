package sa57.team01.adproject.DTO;

import lombok.Getter;
import lombok.Setter;
import sa57.team01.adproject.models.RentalProperty;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class RentalPropertyDTO extends PropertyDTO implements Serializable {
    private int contractMonthPeriod;

    public RentalPropertyDTO(RentalProperty other) {
        super(other);
        this.contractMonthPeriod = other.getContractMonthPeriod();
    }

    public static List<RentalPropertyDTO> from(List<RentalProperty> rentalProperties) {
        return rentalProperties.stream().map(RentalPropertyDTO::new).toList();
    }




}
