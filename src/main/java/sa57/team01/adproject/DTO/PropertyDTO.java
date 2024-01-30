package sa57.team01.adproject.DTO;

import lombok.Getter;
import lombok.Setter;
import sa57.team01.adproject.models.Property;
import sa57.team01.adproject.models.RentalProperty;

import java.io.Serializable;
import java.util.List;

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
    private Long appointmentId;

    public PropertyDTO(Property other) {
        this.id = other.getPropertyid();
        this.town = other.getTown();
        this.propertyStatus = other.getPropertyStatus().toString();
        this.flatType = other.getFlatType();
        this.storeyRange = other.getStoreyRange();
        this.streetName = other.getStreetName();
        this.floorArea = other.getFloorArea();
        this.ownerId = other.getOwner().getCustomerId();
        this.appointmentId = other.getAppointment().getAppointmentId();
    }
}
