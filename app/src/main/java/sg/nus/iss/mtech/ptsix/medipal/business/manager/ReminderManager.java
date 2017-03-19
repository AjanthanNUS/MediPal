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

/**
 * Created by Ajanthan on 3/17/2017.
 */

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

        reminderVOs.addAll(remindersDao.getReminderVOs());

        return reminderVOs;
    }

    public ReminderVO castToReminderVo(Reminders reminder) {
        ReminderVO reminderVO = new ReminderVO();
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
        return reminderVO;
    }
}
