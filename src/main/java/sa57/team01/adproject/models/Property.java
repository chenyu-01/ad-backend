package sa57.team01.adproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import sa57.team01.adproject.DTO.MixPropertyDTO;

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
    @Enumerated(EnumType.STRING)
    private FlatType flatType;
    @NotNull
    private String storeyRange;
    @NotNull
    private String streetName;
    @NotNull
    private int floorArea; // in square meters
    @NotNull
    private String block;
    @NotNull
    private boolean forSale;
    @NotNull
    @ManyToOne
    private Owner owner;

    @NotNull
    private double price;

    @NotNull
    private FlatModel flatModel;

    @OneToOne(mappedBy = "property")
    private Appointment appointment;

    private String imageUrl;

    public Property() {
        super();
    }

    public Property(long propertyid,
                    TownName town,
                    PropertyStatus propertyStatus,
                    FlatType flatType,
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

   public void setRandomImage() {
        int imageIndex = (int) (Math.random() * 40) + 1; // from 1 to 40
        this.imageUrl = "http://localhost:8080/images/" + imageIndex + ".png";
    }
    public void setRandomBlock() {
        int blockIndex = (int) (Math.random() * 999) + 1; // from 1 to 999
        // from A to D or empty
        String blockLetter = Math.random() < 0.1 ? String.valueOf((char) ((int) (Math.random() * 4) + 65)) : "";

        this.block = blockIndex + blockLetter;
    }
    public void setRandomStoreyRange() {
        int storageIndex = (int) (Math.random() * 8) * 3 + 1; // from 1 to 25
        this.storeyRange = storageIndex + " TO " + (storageIndex + 2);
    }
    public void setRandomPrice() {
        this.price = (int) (Math.random() * 1000000) + 100000;
    }
    public void setRandomFlatModel() {
        this.flatModel = FlatModel.getRandomFlatModel();
    }
    public void setRandomForSale() {
        this.forSale = Math.random() < 0.5;
        this.propertyStatus = forSale ? PropertyStatus.forSale : PropertyStatus.forRent;
    }
    public void setRandomFlatType() {
        this.flatType = FlatType.getRandomFlatType();
    }
    public void setRandomTown() {
        this.town = TownName.getRandomTown();
        int aveIndex = (int) (Math.random() * 9) + 1; // from 1 to 9
        this.streetName = town.toString() + " AVE " + aveIndex;
    }
    public void setRandomFloorArea() {
        this.floorArea = (int) (Math.random() * 100) + 50; // from 50 to 150
    }
    public void randomize() {
        setRandomTown();
        setRandomFlatType();
        setRandomFloorArea();
        setRandomForSale();
        setRandomFlatModel();
        setRandomPrice();
        setRandomStoreyRange();
        setRandomBlock();
        setRandomImage();
    }


    public boolean isBooked() {
        return appointment != null;
    }

    public void updateProperty(MixPropertyDTO propertyDTO) {
        this.town = TownName.valueOf(propertyDTO.getTown());
        this.propertyStatus = PropertyStatus.valueOf(propertyDTO.getPropertyStatus());
        this.flatType = FlatType.valueOf(propertyDTO.getFlatType());
        this.storeyRange = propertyDTO.getStoreyRange();
        this.streetName = propertyDTO.getStreetName();
        this.floorArea = Integer.parseInt(propertyDTO.getFloorArea());
        this.block = propertyDTO.getBlock();
        this.forSale = Boolean.parseBoolean(propertyDTO.getContractMonthPeriod());
        this.price = Float.parseFloat(propertyDTO.getPrice());
        this.flatModel = FlatModel.valueOf(propertyDTO.getFlatModel());
    }
}
