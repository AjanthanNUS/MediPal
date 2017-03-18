package sg.nus.iss.mtech.ptsix.medipal.business.services;


import android.content.Context;

import java.util.ArrayList;

import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.RemindersDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminders;

public class RemindersService {
    private Context context;

    public RemindersService(Context context) {
        this.context = context;
    }

    public long makeReminders(Reminders reminders) {
        RemindersDao remindersDao = new RemindersDao(context);
        long result = 0;

        remindersDao.open();
        try {
            result = remindersDao.save(reminders);
            reminders.setId((int) result);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            remindersDao.close();
        }

        return result;
    }

    public long updateReminders(Reminders reminders) {
        RemindersDao remindersDao = new RemindersDao(context);
        long result = 0;

        remindersDao.open();
        try {
            result = remindersDao.update(reminders);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            remindersDao.close();
        }

        return result;
    }

    public long deleteCategories(Reminders reminders) {
        RemindersDao remindersDao = new RemindersDao(context);
        long result = 0;

        remindersDao.open();
        try {
            result = remindersDao.delete(reminders);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            remindersDao.close();
        }

        return result;
    }

    public Reminders getReminders(int remindersId) {
        RemindersDao remindersDao = new RemindersDao(context);
        Reminders reminders = null;

        remindersDao.open();
        try {
            reminders = remindersDao.getReminders(remindersId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            remindersDao.close();
        }

        return reminders;
    }

    public ArrayList<Reminders> getReminders() {
        RemindersDao remindersDao = new RemindersDao(context);
        ArrayList<Reminders> ret = new ArrayList<>();

        remindersDao.open();
        try {
            ret = remindersDao.getReminders();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            remindersDao.close();
        }

        return ret;
    }
}
