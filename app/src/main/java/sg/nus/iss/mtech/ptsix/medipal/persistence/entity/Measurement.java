package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by WongCheeVui on 3/6/2017.
 */

/*
ID              interger
Systolic        interger
Diastolic       interger
Pulse           interger
Temperature     Decimal(5,2)
Weight          interger
MeasureOn       DateTime

 */
public class Measurement implements Parcelable {

    private int m_id;
    private int mSystolic;
    private int mDiastolic;
    private int mPulse;
    private float mTemperature;
    private int mWeight;
    private Date mMeasureOn;

    public Measurement() {
        super();
    }

    private Measurement(Parcel in) {
        super();
        this.m_id = in.readInt();
        this.mSystolic = in.readInt();
        this.mDiastolic = in.readInt();
        this.mPulse = in.readInt();
        this.mTemperature = in.readInt();
        this.mWeight = in.readInt();
        this.mMeasureOn = new Date(in.readLong());
    }

    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        this.m_id = id;
    }

    public int getEventSystolic() {
        return mSystolic;
    }

    public void setEventSystolic(int Systolic) {
        this.mSystolic = Systolic;
    }

    public int getEventDiastolic() {
        return mDiastolic;
    }

    public void setEventPulse (int Pulse ) {
        this.mPulse = Pulse ;
    }
    public int getEventPulse () {
        return mPulse;
    }

    public void setEventDiastolic(int Diastolic) {
        this.mDiastolic = Diastolic;
    }
    public float getEventTemperature() {
        return mTemperature;
    }

    public void setEventTemperature(float Temperature) {
        this.mTemperature = Temperature;
    }

    public int getEventWeight() {
        return mWeight;
    }

    public void setEventWeight(int Weight) {
        this.mWeight = Weight;
    }

    public Date getEventMeasureOn() {
        return mMeasureOn;
    }
    public void setEventMeasureOn(Date date) {
        this.mMeasureOn = date;
    }

    @Override
    public String toString() {
        return "Measurement [id=" + m_id +
                ", Systolic=" + mSystolic +
                ", Diastolic=" + mDiastolic +
                ", Pulse=" + mPulse +
                ", Temperature=" + mTemperature +
                ", Weight=" + mWeight +
                ", MeasureOn=" + mMeasureOn +
                "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeInt(getEventSystolic());
        dest.writeInt(getEventDiastolic());
        dest.writeInt(getEventPulse());
        dest.writeFloat(getEventTemperature());
        dest.writeInt(getEventWeight());
        dest.writeLong(getEventMeasureOn().getDate());
    }

    public static final Parcelable.Creator<Measurement> CREATOR = new Parcelable.Creator<Measurement>() {
        public Measurement createFromParcel(Parcel in) {
            return new Measurement(in);
        }

        public Measurement[] newArray(int size) {
            return new Measurement[size];
        }
    };

}

