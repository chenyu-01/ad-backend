package sa57.team01.adproject.DTO;

import lombok.Getter;
import lombok.Setter;
import sa57.team01.adproject.models.Preferences;
import sa57.team01.adproject.models.TownName;
@Getter
@Setter
public class PreferencesDTO {
    private boolean ONE_ROOM;
    private boolean TWO_ROOM;
    private boolean THREE_ROOM;
    private boolean FOUR_ROOM;
    private boolean FIVE_ROOM;
    private boolean EXECUTIVE;
    private boolean MULTI_GENERATION;
    private TownName town;
    private int storyRange;
    private int lowPrice;
    private int highPrice;


    public PreferencesDTO(){

    }

    public PreferencesDTO(boolean ONE_ROOM,boolean TWO_ROOM,boolean THREE_ROOM,boolean FOUR_ROOM, boolean FIVE_ROOM, boolean EXECUTIVE, boolean MULTI_GENERATION,
                       TownName town,int storyRange, int lowPrice, int highPrice){
        this.ONE_ROOM = ONE_ROOM;
        this.TWO_ROOM = TWO_ROOM;
        this.THREE_ROOM = THREE_ROOM;
        this.FOUR_ROOM = FOUR_ROOM;
        this.FIVE_ROOM = FIVE_ROOM;
        this.EXECUTIVE = EXECUTIVE;
        this.MULTI_GENERATION = MULTI_GENERATION;
        this.town = town;
        this.storyRange = storyRange;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
    }

    public PreferencesDTO (Preferences other) {
        this.ONE_ROOM = other.isBedroom1();
        this.TWO_ROOM = other.isBedroom2();
        this.THREE_ROOM = other.isBedroom3();
        this.FOUR_ROOM = other.isBedroom4();
        this.FIVE_ROOM = other.isBedroom5();
        this.EXECUTIVE = other.isExecutive();
        this.MULTI_GENERATION = other.isMultiGen();
        this.town = other.getTown();
        this.storyRange = other.getStoryRange();
        this.lowPrice = other.getLowPrice();
        this.highPrice = other.getHighPrice();
    }
}
