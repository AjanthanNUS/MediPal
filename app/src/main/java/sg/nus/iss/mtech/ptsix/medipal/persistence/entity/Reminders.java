package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Reminders implements Parcelable {

    private int m_id;
    private int mFrequency;
    private Date mStartTime;
    private int mInterval;

    public Reminders() {
        super();
    }

    private Reminders(Parcel in) {
        super();
        this.m_id = in.readInt();
        this.mFrequency = in.readInt();
        this.mStartTime = new Date(in.readLong());
        this.mInterval = in.readInt();
    }

    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        this.m_id = id;
    }

    public int getFrequency() {
        return mFrequency;
    }

    public void setFrequency(int frequency) {
        this.mFrequency = frequency;
    }

    public Date getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Date date) {
        this.mStartTime = date;
    }

    public int getInterval() {
        return mInterval;
    }

    public void setInterval(int Interval) {
        this.mInterval = Interval;
    }

    @Override
    public String toString() {
        return "Reminders [id=" + m_id +
                ", Frequency=" + mFrequency +
                ", StartTime=" + mStartTime +
                ", Interval=" + mInterval +
                "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeInt(getFrequency());
        dest.writeLong(getStartTime().getDate());
        dest.writeInt(getInterval());
    }

    public static final Parcelable.Creator<Reminders> CREATOR = new Parcelable.Creator<Reminders>() {
        public Reminders createFromParcel(Parcel in) {
            return new Reminders(in);
        }

        public Reminders[] newArray(int size) {
            return new Reminders[size];
        }
    };
}
