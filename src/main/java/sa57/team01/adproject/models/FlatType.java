package sa57.team01.adproject.models;
public enum FlatType {
    ONE_ROOM,
    TWO_ROOM,
    THREE_ROOM,
    FOUR_ROOM,
    FIVE_ROOM,
    EXECUTIVE,
    MULTI_GENERATION;

    @Override
    public String toString() {
        switch (this) {
            case ONE_ROOM:
                return "1 ROOM";
            case TWO_ROOM:
                return "2 ROOM";
            case THREE_ROOM:
                return "3 ROOM";
            case FOUR_ROOM:
                return "4 ROOM";
            case FIVE_ROOM:
                return "5 ROOM";
            case MULTI_GENERATION:
                return "MULTI-GENERATION";
            default:
                // For the EXECUTIVE and any other types, we can directly use the name as it matches the data
                return super.toString().replace('_', ' ').toUpperCase();
        }
    }

    public static FlatType getRandomFlatType() {
        return values()[(int) (Math.random() * values().length)];
    }
}
