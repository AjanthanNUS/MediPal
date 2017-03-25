package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.MedicineDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminders;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

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
        RemindersService remindersService = new RemindersService(context);
        Date date = new Date();

        Reminders reminder = new Reminders();
        reminder.setFrequency(1);
        reminder.setInterval(1);
        reminder.setStartTime(date);
        Long reminder1ReturnValue = remindersService.makeReminders(reminder);

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

        assertEquals(Long.valueOf(1), medicine1ReturnValue);
        assertEquals("Medicine Name 1", checkMedicine1.getMedicine());
        assertEquals("Medicine 1 Description", checkMedicine1.getDescription());
        assertEquals(1, checkMedicine1.getCatId());
        assertEquals(1, checkMedicine1.getRemind());
        assertEquals(1, checkMedicine1.getReminderId());
        assertEquals(10, checkMedicine1.getQuantity());
        assertEquals(1, checkMedicine1.getDosage());
    }

    @Test
    public void testGetMedicineByName() throws Exception {
        MedicineService medicineService = new MedicineService(context);
        RemindersService remindersService = new RemindersService(context);

        List<Reminders> remindersList = this.createDummyReminders();

        for (Reminders reminder : remindersList) {
            remindersService.makeReminders(reminder);
        }

        List<Medicine> medicineList = this.createDummyMedicines();

        for (Medicine medicine : medicineList) {
            medicineService.makeMedicine(medicine);
        }

        medicineList = medicineService.getMedicineByName("1");

        assertEquals(1, medicineList.size());
        assertEquals("1", medicineList.get(0).getMedicine());

        medicineList = medicineService.getMedicineByName("11");

        assertEquals(0, medicineList.size());
    }

    @Test
    public void testValidCheckEditNameDuplicate() throws Exception {
        MedicineService medicineService = new MedicineService(context);
        RemindersService remindersService = new RemindersService(context);

        List<Reminders> remindersList = this.createDummyReminders();

        for (Reminders reminder : remindersList) {
            remindersService.makeReminders(reminder);
        }

        List<Medicine> medicineList = this.createDummyMedicines();

        for (Medicine medicine : medicineList) {
            medicineService.makeMedicine(medicine);
        }

        Boolean checkedResult = medicineService.validCheckEditNameDuplicate(1, "1");
        assertTrue(checkedResult);

        checkedResult = medicineService.validCheckEditNameDuplicate(1, "2");
        assertFalse(checkedResult);
    }

    @Test
    public void testGetNamesOfMedicineBelowThreshold() throws Exception {
        MedicineService medicineService = new MedicineService(context);
        RemindersService remindersService = new RemindersService(context);

        List<Reminders> remindersList = this.createDummyReminders();

        for (Reminders reminder : remindersList) {
            remindersService.makeReminders(reminder);
        }

        List<Medicine> medicineList = this.createDummyMedicines();

        for (Medicine medicine : medicineList) {
            if (medicine.getThreshold() == 5) {
                medicine.setThreshold(-1);
            }
            if (medicine.getThreshold() != 2) {
                medicine.setThreshold(medicine.getThreshold() + 2);
            }
            medicineService.makeMedicine(medicine);
        }

        List<String> nameList = medicineService.getNamesOfMedicineBelowThreshold();

        assertEquals(7, nameList.size());
        assertEquals("1", nameList.get(0));
        assertEquals("3", nameList.get(1));
        assertEquals("4", nameList.get(2));
        assertEquals("6", nameList.get(3));
        assertEquals("7", nameList.get(4));
        assertEquals("8", nameList.get(5));
        assertEquals("9", nameList.get(6));
    }

    private List<Reminders> createDummyReminders() {
        List<Reminders> remindersList = new ArrayList<Reminders>();
        Date date = new Date();

        for (int i = 0; i < 10; i++) {
            Reminders reminder = new Reminders();
            reminder.setFrequency(i);
            reminder.setInterval(i);
            reminder.setStartTime(date);
            remindersList.add(reminder);
        }

        return remindersList;
    }

    private List<Medicine> createDummyMedicines() {
        List<Medicine> medicineList = new ArrayList<Medicine>();
        Date date = new Date();

        for (int i = 0; i < 10; i++) {
            Medicine medicine = new Medicine();
            medicine.setMedicine(i + "");
            medicine.setDescription("Medicine 1 Description");
            medicine.setCatId(1);
            medicine.setRemind(1);
            medicine.setReminderId(i);
            medicine.setQuantity(i);
            medicine.setDosage(i);
            medicine.setConsumeQuantity(i);
            medicine.setThreshold(i);
            medicine.setDateIssued(date);
            medicine.setExpireFactor(i);
            medicineList.add(medicine);
        }
        return medicineList;
    }
}
