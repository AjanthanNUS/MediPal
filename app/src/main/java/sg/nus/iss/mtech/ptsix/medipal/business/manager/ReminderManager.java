package sg.nus.iss.mtech.ptsix.medipal.business.manager;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.CategoriesDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.MedicineDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.RemindersDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminders;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ReminderVO;

public class ReminderManager {

    private RemindersDao remindersDao;
    private MedicineDao medicineDao;
    private CategoriesDao categoriesDao;

    public ReminderManager(Context context) {
        remindersDao = new RemindersDao(context);
        medicineDao = new MedicineDao(context);
        categoriesDao = new CategoriesDao(context);
    }

    public List<ReminderVO> getAllReminders() {
        List<ReminderVO> reminderVOs = new ArrayList<>();
        try {
            remindersDao.open();
            reminderVOs.addAll(remindersDao.getReminderVOs());
        } finally {
            remindersDao.close();
        }
        return reminderVOs;
    }

    public ReminderVO castToReminderVo(Reminders reminder) {
        ReminderVO reminderVO = new ReminderVO();

        try {
            remindersDao.open();
            medicineDao.open();
            categoriesDao.open();
            if (reminder == null) {
                return null;
            }
            if (reminder instanceof ReminderVO) {
                reminderVO = (ReminderVO) reminder;
            } else {
                reminderVO.setId(reminder.getId());
                reminderVO.setFrequency(reminder.getFrequency());
                reminderVO.setStartTime(reminder.getStartTime());
                reminderVO.setInterval(reminder.getInterval());

                List<Medicine> medicines = medicineDao.getMedicines();
                for (Medicine medicine : medicines) {
                    if (reminder.getId() == medicine.getReminderId()) {
                        reminderVO.setMedicine(medicine);
                        Categories category = categoriesDao.getCategories(medicine.getCatId());

                        reminderVO.setCategory(category);

                        break;
                    }

                }
            }
        } finally {
            remindersDao.close();
            medicineDao.close();
            categoriesDao.close();
        }
        return reminderVO;
    }
}
