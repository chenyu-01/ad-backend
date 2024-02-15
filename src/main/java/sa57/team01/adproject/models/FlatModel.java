package sa57.team01.adproject.models;

public enum FlatModel {
    IMPROVED,
    NEW_GENERATION,
    DBSS,
    STANDARD,
    APARTMENT,
    SIMPLIFIED,
    MODEL_A,
    PREMIUM_APARTMENT,
    ADJOINED_FLAT,
    MODEL_A_MAISONETTE,
    MAISONETTE,
    TYPE_S1,
    TYPE_S2,
    MODEL_A2,
    TERRACE,
    IMPROVED_MAISONETTE,
    PREMIUM_MAISONETTE,
    MULTI_GENERATION,
    PREMIUM_APARTMENT_LOFT,
    TWO_ROOM,
    THREE_GEN;

    public static FlatModel getRandomFlatModel() {
        return values()[(int) (Math.random() * values().length)];
    }

    @Override
    public String toString() {
        switch (this) {
            case TWO_ROOM:
                return "2-room";
            case THREE_GEN:
                return "3Gen";
            default:
                // Convert enum names back to the original string values for all other types
                return super.toString().replace('_', ' ').toLowerCase();
        }
    }
}
