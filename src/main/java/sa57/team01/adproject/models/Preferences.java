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
}
