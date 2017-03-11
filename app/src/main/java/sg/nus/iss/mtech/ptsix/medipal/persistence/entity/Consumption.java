package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by WongCheeVui on 3/6/2017.
 */
/*
ID              interger
MedicineID      interger
Quantity        interger
ConsumedOn      Date

 */
public class Consumption  implements Parcelable {

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

    public int getEventMedicineID() {
        return mMedicineID;
    }

    public void setEventMedicineID(int medicineID) {
        this.mMedicineID = medicineID;
    }

    public int getEventQuantity() {
        return mQuantity;
    }

    public void setEventQuantity(int Quantity) {
        this.mQuantity = Quantity;
    }

    public Date getEventConsumedOn() {
        return mConsumedOn;
    }
    public void setEventConsumedOn(Date date) {
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
        dest.writeInt(getEventMedicineID());
        dest.writeInt(getEventQuantity());
        dest.writeLong(getEventConsumedOn().getDate());
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

