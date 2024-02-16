package sa57.team01.adproject.models;
public enum FlatType {
    ONE_ROOM,
    TWO_ROOM,
    THREE_ROOM,
    FOUR_ROOM,
    FIVE_ROOM,
    EXECUTIVE,
    MULTI_GENERATION;

    public static FlatType getRandomFlatType() {
        return values()[(int) (Math.random() * values().length)];
    }

    @Override
    public String toString() {
        return switch (this) {
            case ONE_ROOM -> "1 ROOM";
            case TWO_ROOM -> "2 ROOM";
            case THREE_ROOM -> "3 ROOM";
            case FOUR_ROOM -> "4 ROOM";
            case FIVE_ROOM -> "5 ROOM";
            case MULTI_GENERATION -> "MULTI-GENERATION";
            case EXECUTIVE -> "EXECUTIVE";
        };
    }

    public static FlatType getByName(String name) {
        for (FlatType flatType : values()) {
            // convert the enum name to lowercase and remove non-alphabetic characters
            String flatTypeName = flatType.toString().toLowerCase().replaceAll("[^a-z]", "");
            String search = name.toLowerCase().replaceAll("[^a-z]", "");
            if (flatTypeName.equals(search)) {
                return flatType;
            }
        }
        return null;
    }
}
