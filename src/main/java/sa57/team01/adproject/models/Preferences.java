package sa57.team01.adproject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sa57.team01.adproject.DTO.PreferencesDTO;

import java.util.Map;

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

    public void updatePreferences(Map<String, Object> other){
        try {
            if(String.valueOf(other.get("one_ROOM")).isEmpty() || other.get("one_ROOM") == null)
                this.bedroom1 = false;
            else
                this.bedroom1 = Boolean.parseBoolean(other.get("one_ROOM").toString());

            if(String.valueOf(other.get("two_ROOM")).isEmpty() || other.get("two_ROOM") == null)
                this.bedroom2 = false;
            else
                this.bedroom2 = Boolean.parseBoolean(other.get("two_ROOM").toString());

            if(String.valueOf(other.get("three_ROOM")).isEmpty() || other.get("three_ROOM") == null)
                this.bedroom3 = false;
            else
                this.bedroom3 = Boolean.parseBoolean(other.get("three_ROOM").toString());

            if(String.valueOf(other.get("four_ROOM")).isEmpty() || other.get("four_ROOM") == null)
                this.bedroom4 = false;
            else
                this.bedroom4 = Boolean.parseBoolean(other.get("four_ROOM").toString());

            if(String.valueOf(other.get("five_ROOM")).isEmpty() || other.get("five_ROOM") == null)
                this.bedroom5 = false;
            else
                this.bedroom5 = Boolean.parseBoolean(other.get("five_ROOM").toString());

            if(String.valueOf(other.get("executive")).isEmpty() || other.get("executive") == null)
                this.executive = false;
            else
                this.executive = Boolean.parseBoolean(other.get("executive").toString());

            if(String.valueOf(other.get("multi_GENERATION")).isEmpty() || other.get("multi_GENERATION") == null)
                this.multiGen = false;
            else
                this.multiGen = Boolean.parseBoolean(other.get("multi_GENERATION").toString());

            if(String.valueOf(other.get("town")).isEmpty() || other.get("town") == null)
                this.town = TownName.ANG_MO_KIO;
            else
                this.town = TownName.valueOf((String) other.get("town"));
            this.storyRange = Integer.parseInt(other.get("storyRange").toString());
            this.lowPrice = Integer.parseInt(other.get("lowPrice").toString());
            this.highPrice = Integer.parseInt(other.get("highPrice").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
