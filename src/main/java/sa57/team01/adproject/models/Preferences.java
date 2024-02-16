package sa57.team01.adproject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private boolean lowPriceRange;
    private boolean midPriceRange;
    private boolean highPriceRange;

    @OneToOne(mappedBy = "preferences")
    private Customer customer;

    public Preferences(){
        super();
    }

    public Preferences(boolean bedroom1,boolean bedroom2,boolean bedroom3,boolean bedroom4,
                       TownName town,int storyRange,boolean lowPriceRange,boolean midPriceRange,boolean highPriceRange){
        this.bedroom1 = bedroom1;
        this.bedroom2 = bedroom2;
        this.bedroom3 = bedroom3;
        this.bedroom4 = bedroom4;
        this.town = town;
        this.storyRange = storyRange;
        this.lowPriceRange = lowPriceRange;
        this.midPriceRange = midPriceRange;
        this.highPriceRange = highPriceRange;
    }

    public Preferences randomize() {
        this.bedroom1 = Math.random() > 0.5;
        this.bedroom2 = Math.random() > 0.5;
        this.bedroom3 = Math.random() > 0.5;
        this.bedroom4 = Math.random() > 0.5;
        this.bedroom5 = Math.random() > 0.5;
        this.executive = Math.random() > 0.5;
        this.multiGen = Math.random() > 0.5;
        this.town = TownName.values()[(int) (Math.random() * TownName.values().length)];
        this.storyRange = (int) (Math.random() * 40) + 1;
        this.lowPriceRange = Math.random() > 0.5;
        this.midPriceRange = Math.random() > 0.5;
        this.highPriceRange = Math.random() > 0.5;
        return this;
    }
}
