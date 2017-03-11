package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

import java.util.Calendar;

/**
 * Created by Ajanthan on 3/4/2017.
 */

public class Consumption {
    private Integer id;
    private Medicine medicine;
    private Integer quantity;
    private Calendar consumedOn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Calendar getConsumedOn() {
        return consumedOn;
    }

    public void setConsumedOn(Calendar consumedOn) {
        this.consumedOn = consumedOn;
    }
}
