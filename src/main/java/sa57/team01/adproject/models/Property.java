package sa57.team01.adproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Property {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long propertyid;
    @Enumerated(EnumType.STRING)
    private TownName town;
    @Enumerated(EnumType.STRING)
    @NotNull
    private PropertyStatus propertyStatus;
    @NotNull
    private int flatType;
    @NotNull
    private String storeyRange;
    @NotNull
    private String streetName;
    @NotNull
    private int floorArea;
    @NotNull
    private String block;
    @NotNull
    private boolean forSale;
    @NotNull
    @ManyToOne
    private Owner owner;

    @NotNull
    private double price;

    @OneToOne(mappedBy = "property")
    private Appointment appointment;

    public Property() {
        super();
    }

    public Property(long propertyid,
                    TownName town,
                    PropertyStatus propertyStatus,
                    int flatType,
                    String storeyRange,
                    String streetName,
                    int floorArea,
                    String block,
                    Owner owner) {

        this.propertyid = propertyid;
        this.town = town;
        this.propertyStatus = propertyStatus;
        this.flatType = flatType;
        this.storeyRange = storeyRange;
        this.streetName = streetName;
        this.floorArea = floorArea;
        this.block = block;
        this.owner = owner;
    }
}
