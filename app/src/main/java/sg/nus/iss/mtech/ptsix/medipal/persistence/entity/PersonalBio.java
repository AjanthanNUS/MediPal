package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

/**
 * Created by WONG_CH on 05-Mar-17.
 */

public class PersonalBio implements Parcelable {

    private int id;
    private String eventName;
    private Date eventDOB;
    private String eventIDno;
    private String eventAddress;
    private String eventPostalcode;
    private int eventHeight;
    private String eventBloodtype;


    public PersonalBio() {
        super();
    }

    private PersonalBio(Parcel in) {
        super();
        this.id = in.readInt();
        this.eventName = in.readString();
        this.eventDOB = new Date(in.readLong());
        this.eventIDno = in.readString();
        this.eventAddress = in.readString();
        this.eventPostalcode = in.readString();
        this.eventHeight = in.readInt();
        this.eventBloodtype = in.readString();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String name) {
        this.eventName = name;
    }

    public Date getEventDOB() {
        return eventDOB;
    }
    public void setEventDOB(Date date) {
        this.eventDOB = date;
    }

    public String getEventIDNo() {
        return eventIDno;
    }
    public void setEventIDNo(String id) {
        this.eventIDno = id;
    }

    public String getEventAddress() {
        return eventAddress;
    }
    public void setEventAddress(String addr) {
        this.eventAddress = addr;
    }

    public String getEventPostalCode() {
        return eventPostalcode;
    }
    public void setEventPostalCode(String code) {
        this.eventPostalcode = code;
    }

    public int getEventHeight() {
        return eventHeight;
    }
    public void setEventHeight(int height) {
        this.eventHeight = height;
    }

    public String getEventBloodType() {
        return eventBloodtype;
    }
    public void setEventBloodType(String type) {
        this.eventBloodtype = type;
    }
    @Override
    public String toString() {
        return "PersonalBio [id=" + id +
                ", name=" + eventName +
                ", date=" + eventDOB +
                ", id=" + eventIDno +
                ", addr=" + eventAddress +
                ", code=" + eventPostalcode +
                ", height=" + eventHeight +
                ", type=" + eventBloodtype +
                "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getEventName());
        dest.writeLong(getEventDOB().getDate());
        dest.writeString(getEventIDNo());
        dest.writeString(getEventAddress());
        dest.writeString(getEventPostalCode());
        dest.writeInt(getEventHeight());
        dest.writeString(getEventBloodType());
    }

    public static final Parcelable.Creator<PersonalBio> CREATOR = new Parcelable.Creator<PersonalBio>() {
        public PersonalBio createFromParcel(Parcel in) {
            return new PersonalBio(in);
        }

        public PersonalBio[] newArray(int size) {
            return new PersonalBio[size];
        }
    };

}