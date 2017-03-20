package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class ICE implements Parcelable {

    private int id;
    private String name;
    private String contactNo;
    private int iceContactType;
    private String description;
    private int sequence;

    public ICE() {
        super();
    }

    private ICE(Parcel in) {
        super();
        this.id = in.readInt();
        this.name = in.readString();
        this.contactNo = in.readString();
        this.iceContactType = in.readInt();
        this.description = in.readString();
        this.sequence = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public int getIceContactType() {
        return iceContactType;
    }

    public void setIceContactType(int iceContactType) {
        this.iceContactType = iceContactType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "HealthBio [id=" + id +
                ", Name=" + name +
                ", ContactNo=" + contactNo +
                ", ContactType=" + iceContactType +
                ", Description=" + description +
                ", Sequence=" + sequence +
                "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getName());
        dest.writeString(getContactNo());
        dest.writeInt(getIceContactType());
        dest.writeString(getDescription());
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
