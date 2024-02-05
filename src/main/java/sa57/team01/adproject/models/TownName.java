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
    KALLANG,
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
        // generate a random number between 0 and length of the enum
        int randomIndex = (int) (Math.random() * TownName.values().length);
        return TownName.values()[randomIndex];
    }


}
