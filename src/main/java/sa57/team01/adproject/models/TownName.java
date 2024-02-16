package sa57.team01.adproject.models;

public enum TownName {
        ANG_MO_KIO,
        BEDOK,
        BISHAN, 
        BUKIT_BATOK,
        BUKIT_MERAH,
        BUKIT_PANJANG,
        BUKIT_TIMAH,
        CENTRAL_AREA,
        CHOA_CHU_KANG,
        CLEMENTI, 
        GEYLANG,
        HOUGANG, 
        JURONG_EAST,
        JURONG_WEST,
        KALLANG_WHAMPOA,
        MARINE_PARADE,
        PASIR_RIS,
        PUNGGOL, 
        QUEENSTOWN,
        SEMBAWANG,
        SENGKANG,
        SERANGOON,
        TAMPINES, 
        TOA_PAYOH,
        WOODLANDS, 
        YISHUN;


    public static TownName getRandomTown() {
        return values()[(int) (Math.random() * values().length)];
    }

    @Override
    public String toString() {
        return super.toString().replace('_', ' ');
    }

    public static TownName getByName(String name) {
        for (TownName town : values()) {
            // convert the enum name to lowercase and remove non-alphabetic characters
            String townName = town.toString().toLowerCase().replaceAll("[^a-z]", "");
            String search = name.toLowerCase().replaceAll("[^a-z]", "");
            if (townName.equals(search)) {
                return town;
            }
        }
        return null;
    }


}
