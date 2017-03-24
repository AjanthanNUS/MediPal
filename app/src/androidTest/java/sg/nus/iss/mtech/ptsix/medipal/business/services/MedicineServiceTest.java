package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.MedicineDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;

@RunWith(AndroidJUnit4.class)
public class MedicineServiceTest {

    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();

        MedicineDao medicineDao = new MedicineDao(context);
        medicineDao.open();
        medicineDao.truncateAllMedicine();
        medicineDao.close();
    }

    @Test
    public void testMakeMedicine() throws Exception {
        MedicineService medicineService = new MedicineService(context);

        Date date = new Date();

        Medicine medicine = new Medicine();
        medicine.setMedicine("Medicine Name 1");
        medicine.setDescription("Medicine 1 Description");
        medicine.setCatId(1);
        medicine.setRemind(1);
        medicine.setReminderId(1);
        medicine.setQuantity(10);
        medicine.setDosage(1);
        medicine.setConsumeQuantity(1);
        medicine.setThreshold(1);
        medicine.setDateIssued(date);
        medicine.setExpireFactor(1);

        Long medicine1ReturnValue = medicineService.makeMedicine(medicine);

        Medicine checkMedicine1 = medicineService.getMedicine(1);

//        assertEquals(Long.valueOf(1), medicine1ReturnValue);
//        assertEquals("First Test Category.", checkMedicine1.getMedicine());
//        assertEquals("This is FTC category", checkMedicine1.getDescription());
//        assertEquals("FTC", checkCategory1.getCode());
//        assertEquals(0, checkCategory1.getRemind());
//        assertEquals("Second Test Category.", checkCategory2.getCategory());
//        assertEquals("This is STC category", checkCategory2.getDescription());
//        assertEquals("STC", checkCategory2.getCode());

    }
}
