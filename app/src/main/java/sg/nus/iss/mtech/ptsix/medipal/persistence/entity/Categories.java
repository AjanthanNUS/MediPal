package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by WongCheeVui on 3/6/2017.
 */

/*
ID              interger
Category        string
Code            string
Description     string
Remind          Bool
 */

public class Categories implements Parcelable {

    private int m_id;
    private String mCategory;
    private String mCode;
    private String mDescription;
    private int mRemind;

    public Categories() {
        super();
    }

    private Categories(Parcel in) {
        super();
        this.m_id = in.readInt();
        this.mCategory = in.readString();
        this.mCode = in.readString();
        this.mDescription = in.readString();
        this.mRemind = in.readInt();
    }

    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        this.m_id = id;
    }

    public String getEventCategory() {
        return mCategory;
    }

    public void setEventCategory(String category) {
        this.mCategory = category;
    }

    public String getEventCode() {
        return mCode;
    }

    public void setEventCode(String Code) {
        this.mCode = Code;
    }

    public void setEventDescription(String Description) {
        this.mDescription = Description;
    }

    public String getEventDescription() {
        return mDescription;
    }

    public void setEventRemind(int mRemind) {
        this.mRemind = mRemind;
    }

    public int getEventRemind() {
        return mRemind;
    }

    @Override
    public String toString() {
        return "Categories [id=" + m_id +
                ", Category=" + mCategory +
                ", Code=" + mCode +
                ", Description=" + mDescription +
                ", Remind=" + mRemind +
                "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getEventCategory());
        dest.writeString(getEventCode());
        dest.writeString(getEventDescription());
        dest.writeInt(getEventRemind());

    }

    public static final Parcelable.Creator<Categories> CREATOR = new Parcelable.Creator<Categories>() {
        public Categories createFromParcel(Parcel in) {
            return new Categories(in);
        }

        public Categories[] newArray(int size) {
            return new Categories[size];
        }
    };

}
