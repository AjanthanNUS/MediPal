package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

import java.util.Date;

/**
 * Created by JOHN on 3/8/2017.
 */

public class HealthBio {

    private int id;
    private String condition;
    private Date startDate;
    private String conditionType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }
}