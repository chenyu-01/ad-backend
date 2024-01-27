package sa57.team01.adproject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance (strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Property {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long propertyid;
    private String town;
    @Enumerated(EnumType.STRING)
    private PropertyStatus propertyStatus;
    private int flatType;
    private String storeyRange;
    private String streetName;
    private int floorArea;

    @ManyToOne
    private Owner owner;

    @OneToOne(mappedBy = "property")
    private Appointment appointment;

    public Property(){
        super();
    }

    public Property (long propertyid, String town, PropertyStatus propertyStatus,int flatType,String storeyRange, String streetName,int floorArea){
      this.propertyid = propertyid;
      this.town = town;
      this.propertyStatus = propertyStatus;
      this.flatType=flatType;
      this.storeyRange=storeyRange;
      this.streetName=streetName;
      this.floorArea=floorArea;
    }
}
