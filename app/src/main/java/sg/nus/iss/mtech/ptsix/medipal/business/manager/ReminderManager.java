package sg.nus.iss.mtech.ptsix.medipal.business.manager;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.RemindersDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminder;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ReminderVO;

/**
 * Created by Ajanthan on 3/17/2017.
 */

public class ReminderManager {

    private RemindersDao remindersDao;

    public ReminderManager(Context context) {
        remindersDao = new RemindersDao(context);
    }

    public List<ReminderVO> getAllReminders() {
        List<ReminderVO> reminders = new ArrayList<>();
        Medicine medicine = new Medicine();
        Categories categories = new Categories();
        categories.setId(1);
        categories.setEventCategory("CATtegory one");
        categories.setEventRemind(1);
        categories.setEventDescription("Sample category description");
        categories.setEventCode("CAT");
        medicine.setId(001);
        medicine.setEventCatID(categories.getId());
        medicine.setEventMedicine("Paracitamole");
        medicine.setEventRemindEnabled(1);

        for (int i = 0; i < 10; i++) {
            ReminderVO reminder = new ReminderVO();
            reminder.setId(i);
            reminder.setStartTime(new Date(Calendar.getInstance().getTimeInMillis()));
            reminder.setFrequency(i);
            reminder.setInterval(i * 10000);
            reminder.setMedicine(medicine);
            reminder.setCategories(categories);

            reminders.add(reminder);
        }

        return reminders;
    }

    public ReminderVO castToReminderVo(Reminder reminder) {
        ReminderVO reminderVO = new ReminderVO();
        if (reminder instanceof ReminderVO) {
            reminderVO = (ReminderVO) reminder;
        } else {
            reminderVO.setId(reminder.getId());
            reminderVO.setFrequency(reminder.getFrequency());
            reminderVO.setStartTime(reminder.getStartTime());
            reminderVO.setInterval(reminder.getInterval());

            //TODO Get medicine , category from DB
            reminderVO.setMedicine(null);
            reminderVO.setCategories(null);
        }


        return reminderVO;
    }
}
