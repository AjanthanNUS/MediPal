package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

import java.util.Calendar;

public class ConsumeReminder {
    private Integer frequency;
    private Calendar startTime;
    private Integer interval;
    private Boolean remind;

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
    }
}
