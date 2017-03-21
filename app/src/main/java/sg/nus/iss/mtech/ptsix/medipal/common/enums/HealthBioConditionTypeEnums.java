package sg.nus.iss.mtech.ptsix.medipal.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOHN on 3/12/2017.
 */

public enum HealthBioConditionTypeEnums {

    ALLERGY(0, "A"),
    CONDITION(1, "C");

    private int code;
    private String conditionType;

    HealthBioConditionTypeEnums(int code, String conditionType) {
        this.code = code;
        this.conditionType = conditionType;
    }

    public int getConditionTypeCode() {
        return code;
    }

    public String getConditionTypeName() {
        return conditionType;
    }

    public static List<HealthBioConditionTypeEnums> getAllHealthBioCategory() {
        List<HealthBioConditionTypeEnums> healthBioCategories = new ArrayList<>();
        healthBioCategories.add(ALLERGY);
        healthBioCategories.add(CONDITION);
        return healthBioCategories;
    }

    public static String[] getAllHealthConditionTYPES() {
        String[] healthCategories = {ALLERGY.getConditionTypeName(),CONDITION.getConditionTypeName()};
        return healthCategories;
    }

    public static HealthBioConditionTypeEnums getHealthBioConditionTypeEnums(String  conditionType) {
        for(HealthBioConditionTypeEnums healthConditionTypeEnums : getAllHealthBioCategory()) {
            if(healthConditionTypeEnums.getConditionTypeName().equals(conditionType)) {
                return healthConditionTypeEnums;
            }
        }
        return null;
    }

}
