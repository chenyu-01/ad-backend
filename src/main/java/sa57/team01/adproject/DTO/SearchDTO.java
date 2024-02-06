package sa57.team01.adproject.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDTO {
    private double lowPrice;
    private double highPrice;

    private boolean roomOne;
    private boolean roomTwo;
    private boolean roomThree; // flatType == 3 && room3 == true
    private boolean roomFour; // flatType == 4 && room4 == true

    private String town;
    private long page;
    private String propertyType;

}
