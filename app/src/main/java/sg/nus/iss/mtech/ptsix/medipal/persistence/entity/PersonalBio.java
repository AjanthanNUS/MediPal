package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class PersonalBio implements Parcelable {

    private int id;
    private String userName;
    private Date userDOB;
    private String userIDNo;
    private String address;
    private String postalcode;
    private int height;
    private String bloodtype;

    public PersonalBio() {
        super();
    }

    private PersonalBio(Parcel in) {
        super();
        this.id = in.readInt();
        this.userName = in.readString();
        this.userDOB = new Date(in.readLong());
        this.userIDNo = in.readString();
        this.address = in.readString();
        this.postalcode = in.readString();
        this.height = in.readInt();
        this.bloodtype = in.readString();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getUserDOB() {
        return userDOB;
    }

    public void setUserDOB(Date userDOB) {
        this.userDOB = userDOB;
    }

    public String getUserIDNo() {
        return userIDNo;
    }

    public void setUserIDNo(String userIDNo) {
        this.userIDNo = userIDNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getBloodType() {
        return bloodtype;
    }

    public void setBloodType(String bloodtype) {
        this.bloodtype = bloodtype;
    }

    @Override
    public String toString() {
        return "personalBio [id=" + id +
                ", name=" + userName +
                ", date=" + userDOB +
                ", userIDNo=" + userIDNo +
                ", address=" + address +
                ", postalcode=" + postalcode +
                ", height=" + height +
                ", bloodtype=" + bloodtype +
                "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getUserName());
        dest.writeString(getUserDOB().toString());
        dest.writeString(getUserIDNo());
        dest.writeString(getAddress());
        dest.writeString(getPostalcode());
        dest.writeInt(getHeight());
        dest.writeString(getBloodType());
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