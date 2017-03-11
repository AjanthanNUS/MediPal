package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by WongCheeVui on 3/6/2017.
 */

/*
ID              interger
Name            string
ContactNo       string
ContactType     interger
Description     string
Sequence        interger

 */
public class ICE implements Parcelable {

    private int m_id;
    private String mName;
    private String mContactNo;
    private int mContactType;
    private String mDescription;
    private int mSequence;

    public ICE() {
        super();
    }

    private ICE(Parcel in) {
        super();
        this.m_id = in.readInt();
        this.mName = in.readString();
        this.mContactNo = in.readString();
        this.mContactType = in.readInt();
        this.mDescription = in.readString();
        this.mSequence = in.readInt();
    }

    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        this.m_id = id;
    }

    public String getEventName() {
        return mName;
    }

    public void setEventName(String Name) {
        this.mName = Name;
    }

    public String getEventContactNo() {
        return mContactNo;
    }

    public void setEventContactNo(String ContactNo) {
        this.mContactNo = ContactNo;
    }

    public int getContactType() {
        return mContactType;
    }

    public void setContactType(int ContactType) {
        this.mContactType = ContactType;
    }

    public String getEventDescription() {
        return mDescription;
    }

    public void setEventDescription(String Description) {
        this.mDescription = Description;
    }

    public int getSequence() {
        return mSequence;
    }

    public void setSequence(int Sequence) {
        this.mSequence = Sequence;
    }

    @Override
    public String toString() {
        return "HealthBio [id=" + m_id +
                ", Name=" + mName +
                ", ContactNo=" + mContactNo +
                ", ContactType=" + mContactType +
                ", Description=" + mDescription +
                ", Sequence=" + mSequence +
                "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getEventName());
        dest.writeString(getEventContactNo());
        dest.writeInt(getContactType());
        dest.writeString(getEventDescription());
        dest.writeInt(getSequence());
    }

    public static final Parcelable.Creator<ICE> CREATOR = new Parcelable.Creator<ICE>() {
        public ICE createFromParcel(Parcel in) {
            return new ICE(in);
        }

        public ICE[] newArray(int size) {
            return new ICE[size];
        }
    };

}
