package sa57.team01.adproject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sa57.team01.adproject.DTO.PreferencesDTO;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Preferences {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long Propertyid;
    private boolean bedroom1;
    private boolean bedroom2;
    private boolean bedroom3;
    private boolean bedroom4;
    private boolean bedroom5;
    private boolean executive;
    private boolean multiGen;
    private TownName town;
    private int storyRange;
    private int lowPrice;
    private int highPrice;

    @OneToOne(mappedBy = "preferences")
    private Customer customer;

    public Preferences(){
        super();
    }

    public void updatePreferences(PreferencesDTO other){
        this.bedroom1 = other.isBedroom1();
        this.bedroom2 = other.isBedroom2();
        this.bedroom3 = other.isBedroom3();
        this.bedroom4 = other.isBedroom4();
        this.bedroom5 = other.isBedroom5();
        this.executive = other.isExecutive();
        this.multiGen = other.isMultiGen();
        this.town = other.getTown();
        this.storyRange = other.getStoryRange();
        this.lowPrice = other.getLowPrice();
        this.highPrice = other.getHighPrice();
    }

    public void randomize() {
        this.bedroom1 = Math.random() < 0.5;
        this.bedroom2 = Math.random() < 0.5;
        this.bedroom3 = Math.random() < 0.5;
        this.bedroom4 = Math.random() < 0.5;
        this.bedroom5 = Math.random() < 0.5;
        this.executive = Math.random() < 0.5;
        this.multiGen = Math.random() < 0.5;
        this.town = TownName.values()[(int) (Math.random() * TownName.values().length)];
        this.storyRange = (int) (Math.random() * 10);
        this.lowPrice = (int) (Math.random() * 1000000);
        this.highPrice = lowPrice + (int) (Math.random() * 1000000);
    }

}
