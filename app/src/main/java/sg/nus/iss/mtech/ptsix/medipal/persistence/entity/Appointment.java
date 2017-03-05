package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

import java.util.Date;

/**
 * Created by win on 4/3/17.
 */

public class Appointment implements Persistable{
    @Override
    public Long getId() {
        return null;
    }

    private long id;
    private String location;
    private String description;
    private Date appointmentDate;

    public void setId(long id) {
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
}
