package sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo;

import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminders;

public class ReminderVO extends Reminders {
    private Medicine medicine;
    private Categories category;

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

}
