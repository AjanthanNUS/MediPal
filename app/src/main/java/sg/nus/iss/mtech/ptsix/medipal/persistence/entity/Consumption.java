package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Consumption implements Parcelable {

    private int m_id;
    private int mMedicineID;
    private int mQuantity;
    private Date mConsumedOn;

    public Consumption() {
        super();
    }

    private Consumption(Parcel in) {
        super();
        this.m_id = in.readInt();
        this.mMedicineID = in.readInt();
        this.mQuantity = in.readInt();
        this.mConsumedOn = new Date(in.readLong());
    }

    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        this.m_id = id;
    }

    public int getMedicineID() {
        return mMedicineID;
    }

    public void setMedicineID(int medicineID) {
        this.mMedicineID = medicineID;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int Quantity) {
        this.mQuantity = Quantity;
    }

    public Date getConsumedOn() {
        return mConsumedOn;
    }

    public void setConsumedOn(Date date) {
        this.mConsumedOn = date;
    }

    @Override
    public String toString() {
        return "Consumption [id=" + m_id +
                ", MedicineID=" + mMedicineID +
                ", Quantity=" + mQuantity +
                ", ConsumedOn=" + mConsumedOn +
                "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeInt(getMedicineID());
        dest.writeInt(getQuantity());
        dest.writeLong(getConsumedOn().getDate());
    }

    public static final Parcelable.Creator<Consumption> CREATOR = new Parcelable.Creator<Consumption>() {
        public Consumption createFromParcel(Parcel in) {
            return new Consumption(in);
        }

        public Consumption[] newArray(int size) {
            return new Consumption[size];
        }
    };
}

