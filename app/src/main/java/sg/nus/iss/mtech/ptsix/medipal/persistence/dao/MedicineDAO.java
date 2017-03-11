package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;

public class MedicineDAO extends DBDAO {

    public MedicineDAO(Context context) {
        super(context);
    }

    public List<Medicine> getMedicines() {

        List<Medicine> medicines = new ArrayList<>();
        Medicine medicine = new Medicine();
        medicine.setMedicine("Medicine A");
        medicine.setDescription("This is description for medicine A.");
        medicine.setCatId(1);
        medicine.setReminderId(1);
        medicine.setRemind(false);
        medicine.setQuantity(32);
        medicine.setDosage(32);
        medicine.setConsumeQuantity(0);
        medicine.setThreshold(3);
        medicine.setDateIssue(new Date());
        medicine.setExpireFactor(3);
        medicines.add(medicine);

        medicine = new Medicine();
        medicine.setMedicine("Medicine B");
        medicine.setDescription("This is description for medicine B.");
        medicine.setCatId(2);
        medicine.setReminderId(2);
        medicine.setRemind(false);
        medicine.setQuantity(32);
        medicine.setDosage(32);
        medicine.setConsumeQuantity(0);
        medicine.setThreshold(3);
        medicine.setDateIssue(new Date());
        medicine.setExpireFactor(20);
        medicines.add(medicine);

        return medicines;
    }
}
