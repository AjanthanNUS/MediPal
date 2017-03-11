package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Objects;

/**
 * Created by win on 4/3/17.
 */

public class Appointment implements Persistable, Parcelable {
    protected Appointment(Parcel in) {
        id = in.readInt();
        location = in.readString();
        description = in.readString();
        appointmentDate = new Date(in.readLong());
    }

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };

    @Override
    public int getId() {
        return id;
    }

    private int id;
    private String location;
    private String description;
    private Date appointmentDate;

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Appointment() {

    }

    public Appointment(String location, String description, Date appointmentDate) {
        this.location = location;
        this.description = description;
        this.appointmentDate = appointmentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        Appointment that = (Appointment) o;

        System.out.println("Location " + location);
        System.out.println("Compared Object Location " + that.location);

        System.out.println("Description " + description);
        System.out.println("Compared Object Location " + that.description);

        System.out.println("Location " + appointmentDate);
        System.out.println("Compared Object Location " + that.appointmentDate);

        System.out.println("ID " + id);
        System.out.println("Compared Object ID " + that.id);

        return id == that.id &&
                Objects.equals(location, that.location) &&
                Objects.equals(description, that.description) &&
                Objects.equals(appointmentDate, that.appointmentDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getLocation());
        dest.writeString(getDescription());
        dest.writeLong(getAppointmentDate().getTime());
    }

}
