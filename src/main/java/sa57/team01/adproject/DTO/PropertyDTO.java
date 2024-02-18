package sa57.team01.adproject.DTO;

import lombok.Getter;
import lombok.Setter;
import sa57.team01.adproject.models.Property;

import java.io.Serializable;

@Getter
@Setter
public class PropertyDTO implements Serializable {
    private Long id;
    private String town;
    private String propertyStatus;
    private String flatType;
    private String flatModel;
    private String storeyRange;
    private String streetName;
    private int floorArea;
    private double price;
    //private int contractMonthPeriod;
    private Long ownerId;
    private String block;
    private Long appointmentId = -1L;
    private String imageUrl;

    public PropertyDTO(Property other) {
        this.id = other.getPropertyid();
        this.town = other.getTown().toString();
        this.propertyStatus = other.getPropertyStatus().toString();
        this.flatType = other.getFlatType().toString();
        this.storeyRange = other.getStoreyRange();
        this.streetName = other.getStreetName();
        this.floorArea = other.getFloorArea();
        this.ownerId = other.getOwner().getCustomerId();
        this.block = other.getBlock();
        this.price = other.getPrice();
        if(other.getAppointment() != null) {
            this.appointmentId = other.getAppointment().getAppointmentId();
        }
        if(other.getImageUrl() != null) {
            this.imageUrl = other.getImageUrl();
        }
        this.flatModel = other.getFlatModel().toString();
    }
}
