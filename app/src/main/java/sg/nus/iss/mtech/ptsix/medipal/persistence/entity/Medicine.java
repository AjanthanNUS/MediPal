package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by WongCheeVui on 3/6/2017.
 */


/*
ID              interger
Medicine        string
Description     string
CatID string    interger
ReminderID      interger
Remind          Bool
Quantity        interger
Dosage          interger
ComsumeQuantity interger
Threshold       interger
DateIssued      Date
ExpireFactor    interger

 */
public class Medicine implements Parcelable {

    public static final Parcelable.Creator<Medicine> CREATOR = new Parcelable.Creator<Medicine>() {
        public Medicine createFromParcel(Parcel in) {
            return new Medicine(in);
        }

        public Medicine[] newArray(int size) {
            return new Medicine[size];
        }
    };
    private int m_id;
    private String mMedicine;
    private String mDesc;
    private int mCatID;
    private int mReminderID;
    private int mRemindEnabled;
    private int mQuantity;
    private int mDosage;
    private int mConsumeQuantity;
    private int mThreshold;
    private Date mDateIssued;
    private int mExpireFactor;

    public Medicine() {
        super();
    }

    private Medicine(Parcel in) {
        super();
        this.m_id = in.readInt();
        this.mMedicine = in.readString();
        this.mDesc = in.readString();
        this.mCatID = in.readInt();
        this.mReminderID = in.readInt();
        this.mRemindEnabled = in.readInt();
        this.mQuantity = in.readInt();
        this.mDosage = in.readInt();
        this.mConsumeQuantity = in.readInt();
        this.mThreshold = in.readInt();
        this.mDateIssued = new Date(in.readLong());
        this.mExpireFactor = in.readInt();
    }

    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        this.m_id = id;
    }

    public String getEventMedicine() {
        return mMedicine;
    }

    public void setEventMedicine(String medicine) {
        this.mMedicine = medicine;
    }

    public String getEventDescription() {
        return "Sample description " + Calendar.getInstance().toString();//mDesc;
    }

    public void setEventDescription(String Description) {
        this.mDesc = Description;
    }

    public int getEventCatID() {
        return mCatID;
    }

    public void setEventCatID(int CatID) {
        this.mCatID = CatID;
    }

    public int getEventReminderID() {
        return mReminderID;
    }

    public void setEventReminderID(int ReminderID) {
        this.mReminderID = ReminderID;
    }

    public int getEventRemindEnabled() {
        return mRemindEnabled;
    }

    public void setEventRemindEnabled(int RemindEnabled) {
        this.mRemindEnabled = RemindEnabled;
    }

    public int getEventQuantity() {
        return mQuantity;
    }

    public void setEventQuantity(int Quantity) {
        this.mQuantity = Quantity;
    }

    public int getEventDosage() {
        return mDosage;
    }

    public void setEventDosage(int Dosage) {
        this.mDosage = Dosage;
    }

    public int getEventConsumeQuantity() {
        return mConsumeQuantity;
    }

    public void setEventConsumeQuantity(int ConsumeQuantity) {
        this.mConsumeQuantity = ConsumeQuantity;
    }

    public int getEventThreshold() {
        return mThreshold;
    }

    public void setEventThreshold(int Threshold) {
        this.mThreshold = Threshold;
    }

    public Date getEventDateIssued() {
        return mDateIssued;
    }

    public void setEventDateIssued(Date date) {
        this.mDateIssued = date;
    }

    public int getEventExpireFactor() {
        return mExpireFactor;
    }

    public void setEventExpireFactor(int ExpireFactor) {
        this.mExpireFactor = ExpireFactor;
    }

    @Override
    public String toString() {
        return "Medicine [id=" + m_id +
                ", Medicine=" + mMedicine +
                ", Description=" + mDesc +
                ", CatID=" + mCatID +
                ", ReminderID=" + mReminderID +
                ", Remind=" + mRemindEnabled +
                ", Quantity=" + mQuantity +
                ", Dosage=" + mDosage +
                ", ComsumeQuantity=" + mConsumeQuantity +
                ", Threshold=" + mThreshold +
                ", DateIssued=" + mDateIssued +
                ", ExpireFactor=" + mExpireFactor +
                "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getEventMedicine());
        dest.writeString(getEventDescription());
        dest.writeInt(getEventCatID());
        dest.writeInt(getEventReminderID());
        dest.writeInt(getEventRemindEnabled());
        dest.writeInt(getEventQuantity());
        dest.writeInt(getEventDosage());
        dest.writeInt(getEventConsumeQuantity());
        dest.writeInt(getEventThreshold());
        dest.writeLong(getEventDateIssued().getDate());
        dest.writeInt(getEventExpireFactor());
    }

}
