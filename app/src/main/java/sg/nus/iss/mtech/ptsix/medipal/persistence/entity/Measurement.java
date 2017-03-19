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

    private Meas_Temp mMeas_Temp;
    private MeasurementBase mMeas_Weight;
    private MeasurementBase mMeas_Id;
    private MeasurementBase mMeas_Pulse;
    private Meas_BloodPressure mMeas_BloodPressure;
    private Meas_Comment mMeasComment;
    private Meas_MeasuredOn mMeasMeasuredOn;

    public Measurement() {
        super();
        mMeas_Temp = new Meas_Temp();
        mMeas_Weight = new MeasurementBase("KG");
        mMeas_Id = new MeasurementBase();
        mMeas_Pulse = new MeasurementBase("BMP");
        mMeas_BloodPressure = new Meas_BloodPressure();
        mMeasComment = new Meas_Comment();
        mMeasMeasuredOn = new Meas_MeasuredOn();
    }

    private Measurement(Parcel in) {
        super();
        this.mMeas_Id.set_reading(in.readInt());
        this.mMeas_BloodPressure.set_Systolic_reading(in.readInt());
        this.mMeas_BloodPressure.set_Diastolic_reading(in.readInt());
        this.mMeas_Pulse.set_reading(in.readInt());
        this.mMeas_Temp.set_Temp_reading(in.readInt());
        this.mMeas_Weight.set_reading(in.readInt());
        this.mMeasMeasuredOn.set_MeasDate(new Date(in.readLong()));
        this.mMeasComment.set_Comment(in.readString());
    }

    public int getId() {
        return mMeas_Id.get_reading();
    }

    public void setId(int id) {
        this.mMeas_Id.set_reading(id);
    }

    public int getEventSystolic() {
        return this.mMeas_BloodPressure.get_Systolic_reading();
    }

    public void setEventSystolic(int Systolic) {
        this.mMeas_BloodPressure.set_Systolic_reading(Systolic);
    }

    public int getEventDiastolic() {
        return this.mMeas_BloodPressure.get_Diastolic_reading();
    }

    public void setEventDiastolic(int Diastolic) {
        this.mMeas_BloodPressure.set_Diastolic_reading(Diastolic);
    }
    public void setEventPulse (int Pulse ) {
        this.mMeas_Pulse.set_reading(Pulse) ;
    }

    public int getEventPulse () {
        return mMeas_Pulse.get_reading();
    }

    public float getEventTemperature() {
        return this.mMeas_Temp.get_Temp_reading();
    }

    public void setEventTemperature(float Temperature) {
        this.mMeas_Temp.set_Temp_reading(Temperature);
    }

    public int getEventWeight() {
        return mMeas_Weight.get_reading();
    }

    public void setEventWeight(int Weight) {
        this.mMeas_Weight.set_reading(Weight);
    }

    public Date getEventMeasureOn() {
        return mMeasMeasuredOn.get_MeasDate();
    }
    public void setEventMeasureOn(Date date) {
        this.mMeasMeasuredOn.set_MeasDate(date);
    }

    public String getComment() {
        return mMeasComment.get_Comment();
    }

    public void setComment(String comment) {
        this.mMeasComment.set_Comment(comment);
    }
    @Override
    public String toString() {
        return "Measurement [id=" + mMeas_Id.get_reading() +
                ", Systolic=" + mMeas_BloodPressure.get_Systolic_reading() +
                ", Diastolic=" + mMeas_BloodPressure.get_Diastolic_reading() +
                ", Pulse=" + mMeas_Pulse.get_reading() +
                ", Temperature=" + mMeas_Temp.get_Temp_reading() +
                ", Weight=" + mMeas_Weight.get_reading() +
                ", MeasureOn=" + mMeasMeasuredOn.get_MeasDate() +
                ", Comment=" + mMeasComment.get_Comment() +
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
        dest.writeString(getComment());
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

