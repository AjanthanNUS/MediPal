package sg.nus.iss.mtech.ptsix.medipal.common.enums;

import java.util.ArrayList;
import java.util.List;

public enum ICEContactTypeEnums {
    EMERGENCY_NUMBER(0, "Emergency number"),
    NEXT_OF_KIN(1, "Next Of Kin"),
    DOCTORS(2, "Doctor");

    private int enumCode;
    private String enumValue;

    ICEContactTypeEnums(int enumCode, String enumValue) {
        this.enumCode = enumCode;
        this.enumValue = enumValue;
    }

    public int getEnumCode() {
        return enumCode;
    }

    public String getEnumValue() {
        return enumValue;
    }

    public static List<ICEContactTypeEnums> getAllICEContactTypeEnums() {
        List<ICEContactTypeEnums> iceContactTypeEnumsList = new ArrayList<>();
        iceContactTypeEnumsList.add(EMERGENCY_NUMBER);
        iceContactTypeEnumsList.add(NEXT_OF_KIN);
        iceContactTypeEnumsList.add(DOCTORS);
        return iceContactTypeEnumsList;
    }

    public static String[] getAllContacts() {
        String iceContactTypeEnums[] = {EMERGENCY_NUMBER.getEnumValue(), NEXT_OF_KIN.getEnumValue()
                , DOCTORS.getEnumValue()};
        return iceContactTypeEnums;
    }

    public static ICEContactTypeEnums getICEContactTypeEnumsByCode(int code) {
        for (ICEContactTypeEnums iceContactTypeEnums : getAllICEContactTypeEnums()) {
            if (iceContactTypeEnums.getEnumCode() == code) {
                return iceContactTypeEnums;
            }
        }
        return null;
    }

}
