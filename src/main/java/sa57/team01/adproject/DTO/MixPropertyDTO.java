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

    public MixPropertyDTO(){}


    public MixPropertyDTO(Long id, String town, String propertyStatus, String flatType,
                          String storeyRange, String streetName, String floorArea, String price,
                          String contractMonthPeriod,  String block, String  leaseCommenceDate,
                          String remainingLease,String bedrooms,long ownerid) {
        this.id = id;
        this.town = town;
        this.propertyStatus = propertyStatus;
        this.flatType = flatType;
        this.storeyRange = storeyRange;
        this.streetName = streetName;
        this.floorArea = floorArea;
        this.price = price;
        this.contractMonthPeriod = contractMonthPeriod;

        this.block = block;
        this.leaseCommenceDate = leaseCommenceDate;
        this.remainingLease = remainingLease;
        this.bedrooms = bedrooms;
        this.ownerid = ownerid;

    }
}
