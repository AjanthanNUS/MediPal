package sg.nus.iss.mtech.ptsix.medipal.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOHN on 3/12/2017.
 */

public enum HealthBioConditionTypeEnums {

    ALLERGY("A", "Allergy"),
    CONDITION("B", "Condition");

    private String conditionTypeCode;
    private String conditionTypeName;

    HealthBioConditionTypeEnums(String categoryCode, String categoryName) {
        this.conditionTypeCode = categoryCode;
        this.conditionTypeName = categoryName;
    }

    public String getConditionTypeCode() {
        return conditionTypeCode;
    }

    public String getConditionTypeName() {
        return conditionTypeName;
    }

    public static List<HealthBioConditionTypeEnums> getAllHealthBioCategory() {
        List<HealthBioConditionTypeEnums> healthBioCategories = new ArrayList<>();
        healthBioCategories.add(ALLERGY);
        healthBioCategories.add(CONDITION);
        return healthBioCategories;
    }

    public static String[] getAllHealthConditionTYPES() {
        String[] healthCategories = {ALLERGY.getConditionTypeCode(),CONDITION.getConditionTypeCode()};
        return healthCategories;
    }

}
