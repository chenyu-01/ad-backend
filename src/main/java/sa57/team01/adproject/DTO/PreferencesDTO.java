package sa57.team01.adproject.DTO;

import lombok.Getter;
import lombok.Setter;
import sa57.team01.adproject.models.Preferences;
import sa57.team01.adproject.models.TownName;
@Getter
@Setter
public class PreferencesDTO {
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



    public PreferencesDTO(){

    }

    public PreferencesDTO(boolean bedroom1,boolean bedroom2,boolean bedroom3,boolean bedroom4, boolean bedroom5, boolean executive, boolean multiGen,
                       TownName town,int storyRange, int lowPrice, int highPrice){
        this.bedroom1 = bedroom1;
        this.bedroom2 = bedroom2;
        this.bedroom3 = bedroom3;
        this.bedroom4 = bedroom4;
        this.bedroom5 = bedroom5;
        this.executive = executive;
        this.multiGen = multiGen;
        this.town = town;
        this.storyRange = storyRange;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
    }

    public PreferencesDTO (Preferences other) {
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
}
