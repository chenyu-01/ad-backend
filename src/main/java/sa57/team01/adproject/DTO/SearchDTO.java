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
    private boolean roomThree;
    private boolean roomFour;
    private boolean roomFive;
    private boolean executive;
    private boolean multiGen;

    private String town;
    private long page;
    private String propertyType;

}
