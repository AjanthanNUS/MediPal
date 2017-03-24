package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.business.manager.ReminderManager;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ReminderVO;

public class ConsumptionDailyService extends IntentService {
    private static final String TAG = "[CONSUME DAILY SERVICE]";
    private static final String sharedPreference = "REMINDER_SHARED_PREFERENCE";
    private static final String LAST_ACCESSED_DATE = "LAST_ACCESSED_DATE";

    private ReminderManager reminderManager;

    public ConsumptionDailyService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedpreferences = getSharedPreferences(sharedPreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        String lastAccess = sharedpreferences.getString(LAST_ACCESSED_DATE, "");
        String currentDate = CommonUtil.date2ddMMMYYYY(new Date());

        Log.i(TAG, "SharedPreferences lastAccessedDate : " + lastAccess);
        if (!lastAccess.equals(currentDate)) {
            editor.putString(LAST_ACCESSED_DATE, currentDate);
            editor.apply();
            Log.i(TAG, "SharedPreferences currentDate apply : " + currentDate);
            reminderManager = new ReminderManager(this);
            Log.i(TAG, "ConsumptionDailyService On Handle Intent started...");
            List<ReminderVO> reminders = reminderManager.getAllReminders();
            for (ReminderVO reminder : reminders) {
                if (isRemind(reminder)) {
                    setupReminderAlarm(reminder);
                }
            }
            Log.i(TAG, "ConsumptionDailyService On Handle Intent finished...");
        } else {
            Log.i(TAG, "ConsumptionDailyService Already executed for the day...");
        }
    }

    private boolean isRemind(ReminderVO reminder) {
        boolean remind = false;
        Medicine medicine = reminder.getMedicine();
        Categories category = reminder.getCategory();
        if (category != null && category.getRemind() != Constant.CAT_REMIND_NO) {
            if (medicine != null && medicine.getRemind() == Constant.CAT_REMIND_YES) {
                if (reminder.getFrequency() > 0) {
                    remind = true;
                }
            }
        }
        return remind;
    }


    private void setupReminderAlarm(ReminderVO reminder) {

        Log.i(TAG, "Setup Reminder Alarm Started...");

        Date start = reminder.getStartTime();
        if (start == null) {
            start = new Date();
        }
        Calendar startTime = CommonUtil.dateToCalendar(start);

        int frequency = reminder.getFrequency();
        int intervalHr = reminder.getInterval();
        //long intervalMillis = intervalHr * 60 * 60 * 1000;

        for (int i = 0; i < frequency; i++) {
            Log.i(TAG, "Alarm created : " + (i + 1));
            String remindTime = CommonUtil.formatCalender(startTime);

            int requestCode = Integer.parseInt(reminder.getId() + "" + i);

            Intent notificationIntent = new Intent(getBaseContext(), ConsumptionBroadcastReceiver.class);
            notificationIntent.putExtra(Constant.REMINDER_BUNDLE, reminder);
            notificationIntent.putExtra(Constant.REQUEST_CODE, requestCode);
            notificationIntent.putExtra(Constant.REMIND_TIME , remindTime);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


            alarmManager.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), pendingIntent);
            Log.i(TAG, "Alarm created for : " + CommonUtil.formatCalender(startTime) + " | Medicine : " + reminder.getMedicine().getMedicine());
//            //TODO Testing code
//            // Getting current time and add the seconds in it
//            Calendar cal = Calendar.getInstance();
//            cal.add(Calendar.SECOND, 10 * (i + 1));
//            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
////            TODO Testing code end


            startTime.add(Calendar.HOUR, intervalHr);
        }

        Log.i(TAG, "Setup Reminder Alarm Finished.");
    }
}
