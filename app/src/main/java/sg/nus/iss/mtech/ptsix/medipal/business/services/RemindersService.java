package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.RemindersDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminders;

public class RemindersService {
    private Context context;
    private static final String TAG = "[REMINDER SERVICE]";

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
            triggerConsumptionBroadcastReceiver(reminders);
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
            triggerConsumptionBroadcastReceiver(reminders);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            remindersDao.close();
        }

        return result;
    }

    public long deleteReminders(Reminders reminders) {
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

    private void triggerConsumptionBroadcastReceiver(Reminders reminder) {
        Intent intent = new Intent(context, ConsumptionDailyService.class);
        intent.putExtra(Constant.FROM_REMINDER_SERVICE, true);
        intent.putExtra(Constant.REMINDER_BUNDLE, reminder);
        intent.putExtra(Constant.REMIND_TIME, CommonUtil.convertDateToString(reminder.getStartTime(),Constant.DATE_TIME_FORMAT ));
        context.startService(intent);
    }
}
