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
    private String flatModel;
    private long ownerid;
    private String imageUrl;
    private String remainingLease;

    public MixPropertyDTO(){}
    public MixPropertyDTO(Long id,String town,String propertyStatus,String flatType,String storeyRange,
                          String streetName,String floorArea,String price,String contractMonthPeriod,
                          String block,String leaseCommenceDate,String flatModel,long ownerid,String imageUrl){
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
        this.flatModel = flatModel;
        this.ownerid = ownerid;
        this.imageUrl = imageUrl;

    }


}
