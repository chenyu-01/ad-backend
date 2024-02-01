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
    private int flatType;
    private String storeyRange;
    private String streetName;
    private int floorArea;
    private double rentalPrice;
    private int contractMonthPeriod;
    private Long ownerId;
    private Long appointmentId = -1L;

    public PropertyDTO(Property other) {
        this.id = other.getPropertyid();
        this.town = other.getTown();
        this.propertyStatus = other.getPropertyStatus().toString();
        this.flatType = other.getFlatType();
        this.storeyRange = other.getStoreyRange();
        this.streetName = other.getStreetName();
        this.floorArea = other.getFloorArea();
        this.ownerId = other.getOwner().getCustomerId();
        if(other.getAppointment() != null) {
            this.appointmentId = other.getAppointment().getAppointmentId();
        }
    }
}
