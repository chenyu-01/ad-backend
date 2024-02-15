package sa57.team01.adproject.DTO;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import sa57.team01.adproject.models.Property;
import sa57.team01.adproject.models.PropertyStatus;
import sa57.team01.adproject.models.TownName;

import java.time.LocalDate;
@Getter
@Setter
public class MixPropertyDTO {
    @Nullable
    private Long id;
    private String town;
    private String propertyStatus;
    private String flatType;
    private String storeyRange;
    private String streetName;
    private String floorArea;
    private String price;
    @Nullable
    private String contractMonthPeriod;
    private String block;
    //private Long appointmentId = -1L;
    @Nullable
    private String leaseCommenceDate;
    @Nullable
    private String remainingLease; // number of months
    private String bedrooms;
    private long ownerid;
    private String imageUrl;

    public MixPropertyDTO(){}


}
