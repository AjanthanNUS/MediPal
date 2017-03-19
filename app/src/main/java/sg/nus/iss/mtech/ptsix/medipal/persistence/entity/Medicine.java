package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Medicine implements Parcelable {

    private int id;
    private String medicine;
    private String description;
    private int catId;
    private int reminderId;
    private int remind;
    private int quantity;
    private int dosage;
    private int consumeQuantity;
    private int threshold;
    private Date dateIssued;
    private int expireFactor;

    public Medicine() {
        super();
    }

    private Medicine(Parcel in) {
        super();
        this.id = in.readInt();
        this.medicine = in.readString();
        this.description = in.readString();
        this.catId = in.readInt();
        this.reminderId = in.readInt();
        this.remind = in.readInt();
        this.quantity = in.readInt();
        this.dosage = in.readInt();
        this.consumeQuantity = in.readInt();
        this.threshold = in.readInt();
        this.dateIssued = new Date(in.readLong());
        this.expireFactor = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public int getRemind() {
        return remind;
    }

    public void setRemind(int remind) {
        this.remind = remind;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public int getConsumeQuantity() {
        return consumeQuantity;
    }

    public void setConsumeQuantity(int consumeQuantity) {
        this.consumeQuantity = consumeQuantity;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(Date date) {
        this.dateIssued = date;
    }

    public int getExpireFactor() {
        return expireFactor;
    }

    public void setExpireFactor(int expireFactor) {
        this.expireFactor = expireFactor;
    }

    @Override
    public String toString() {
        return "Medicine [id=" + id +
                ", Medicine=" + medicine +
                ", Description=" + description +
                ", CatID=" + catId +
                ", ReminderID=" + reminderId +
                ", Remind=" + remind +
                ", Quantity=" + quantity +
                ", Dosage=" + dosage +
                ", ComsumeQuantity=" + consumeQuantity +
                ", Threshold=" + threshold +
                ", DateIssued=" + dateIssued +
                ", ExpireFactor=" + expireFactor +
                "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getMedicine());
        dest.writeString(getDescription());
        dest.writeInt(getCatId());
        dest.writeInt(getReminderId());
        dest.writeInt(getRemind());
        dest.writeInt(getQuantity());
        dest.writeInt(getDosage());
        dest.writeInt(getConsumeQuantity());
        dest.writeInt(getThreshold());
        dest.writeLong(getDateIssued().getDate());
        dest.writeInt(getExpireFactor());
    }

    public static final Parcelable.Creator<Medicine> CREATOR = new Parcelable.Creator<Medicine>() {
        public Medicine createFromParcel(Parcel in) {
            return new Medicine(in);
        }

        public Medicine[] newArray(int size) {
            return new Medicine[size];
        }
    };
}
