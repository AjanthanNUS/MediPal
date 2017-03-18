package sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo;

import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminders;

/**
 * Created by Ajanthan on 3/17/2017.
 */

public class ReminderVO extends Reminders {
    private Medicine medicine;
    private Categories categories;

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

}
