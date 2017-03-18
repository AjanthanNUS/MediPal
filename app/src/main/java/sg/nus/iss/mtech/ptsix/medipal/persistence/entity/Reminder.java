package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by WongCheeVui on 3/6/2017.
 */

/*
ID          interger
Frequency   interger
StartTime   date
Interval    interger

 */
public class Reminder implements Parcelable {

    private int m_id;
    private int mFrequency;
    private Date mStartTime;
    private int mInterval;

    public Reminder() {
        super();
    }

    private Reminder(Parcel in) {
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
        return "Reminder [id=" + m_id +
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

    public static final Parcelable.Creator<Reminder> CREATOR = new Parcelable.Creator<Reminder>() {
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

}
