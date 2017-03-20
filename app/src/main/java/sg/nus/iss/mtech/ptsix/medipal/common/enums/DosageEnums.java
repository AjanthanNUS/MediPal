package sg.nus.iss.mtech.ptsix.medipal.common.enums;

public enum DosageEnums {

    PILLS(1, "pills"),
    CC(2, "cc"),
    ML(3, "ml"),
    GR(4, "gr"),
    MG(5, "mg"),
    DROPS(6, "drops"),
    PIECES(7, "pieces"),
    PUFFS(8, "puffs"),
    UNITS(9, "units"),
    TEASPOON(10, "teaspoon"),
    TABLESPOON(11, "tablespoon"),
    PATCH(12, "patch"),
    MCG(13, "mcg"),
    L(14, "l"),
    MEQ(15, "meq"),
    SPRAY(16, "spray");
    
    private String stringValue;
    private int intValue;

    private DosageEnums(int value, String toString) {
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

    public static String getDosageFromIntValue(int intValue) {
        for (DosageEnums f : values()) {
            if (f.intValue == intValue) return f.stringValue;
        }
        throw new IllegalArgumentException();
    }
}
