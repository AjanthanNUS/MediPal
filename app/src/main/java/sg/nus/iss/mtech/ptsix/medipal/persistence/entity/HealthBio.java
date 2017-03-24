package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class HealthBio implements Parcelable {

    private int m_id;
    private String mCondition;
    private Date mStartDate;
    private String mConditionType;

    public HealthBio() {
        super();
    }

    private HealthBio(Parcel in) {
        super();
        this.m_id = in.readInt();
        this.mCondition = in.readString();
        this.mStartDate = new Date(in.readLong());
        this.mConditionType = in.readString();
    }

    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        this.m_id = id;
    }

    public String getEventCondition() {
        return mCondition;
    }

    public void setEventCondition(String Condition) {
        this.mCondition = Condition;
    }

    public Date getEventStartDate() {
        return mStartDate;
    }

    public void setEventStartDate(Date date) {
        this.mStartDate = date;
    }

    public String getEventConditionType () {
        return mConditionType;
    }

    public void setEventConditionType (String ConditionType ) {
        this.mConditionType = ConditionType;
    }

    @Override
    public String toString() {
        return "healthBio [id=" + m_id +
                ", Condition=" + mCondition +
                ", StartDate=" + mStartDate +
                ", ConditionType=" + mConditionType +
                "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getEventCondition());
        dest.writeLong(getEventStartDate().getDate());
        dest.writeString(getEventConditionType());

    }

    public static final Parcelable.Creator<HealthBio> CREATOR = new Parcelable.Creator<HealthBio>() {
        public HealthBio createFromParcel(Parcel in) {
            return new HealthBio(in);
        }

        public HealthBio[] newArray(int size) {
            return new HealthBio[size];
        }
    };
}


