package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.content.Context;

import java.util.ArrayList;

import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.MedicineDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;

public class MedicineService {
    private Context context;

    public MedicineService(Context context) {
        this.context = context;
    }

    public long makeMedicine(Medicine medicine) {
        MedicineDao medicineDao = new MedicineDao(context);
        long result = 0;
        medicineDao.open();

        try {
            result = medicineDao.save(medicine);
            medicine.setId((int) result);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            medicineDao.close();
        }

        return result;
    }

    public long updateMedicine(Medicine medicine) {
        MedicineDao medicineDao = new MedicineDao(context);
        long result = 0;

        medicineDao.open();
        try {
            result = medicineDao.update(medicine);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            medicineDao.close();
        }

        return result;
    }

    public long deleteMedicine(Medicine medicine) {
        MedicineDao medicineDao = new MedicineDao(context);
        long result = 0;

        medicineDao.open();
        try {
            result = medicineDao.delete(medicine);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            medicineDao.close();
        }
        return result;
    }

    public Medicine getMedicine(int medicineId) {
        MedicineDao medicineDao = new MedicineDao(context);
        Medicine medicine = null;

        medicineDao.open();
        try {
            medicine = medicineDao.getMedicine(medicineId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            medicineDao.close();
        }

        return medicine;
    }

    public ArrayList<Medicine> getMedicine() {
        MedicineDao medicineDao = new MedicineDao(context);
        ArrayList<Medicine> ret = new ArrayList<>();

        medicineDao.open();
        try {
            ret = medicineDao.getMedicines();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            medicineDao.close();
        }

        return ret;
    }
}
