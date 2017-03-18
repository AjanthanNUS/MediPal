package sg.nus.iss.mtech.ptsix.medipal.common.enums;


public enum DosageEnums {

    PILLS("pills", 1),
    CC("cc", 2),
    ML("ml", 3),
    GR("gr", 4),
    MG("mg", 5),
    DROPS("drops", 6),
    PIECES("pieces", 7),
    PUFFS("puffs", 8),
    UNITS("units", 9),
    TEASPOON("teaspoon", 10),
    TABLESPOON("tablespoon", 11),
    PATCH("patch", 12),
    MCG("mcg", 13),
    L("l", 14),
    MEQ("meq", 15),
    SPRAY("spray", 16);


    private String stringValue;
    private int intValue;

    private DosageEnums(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    public int getValue() {
        return intValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
