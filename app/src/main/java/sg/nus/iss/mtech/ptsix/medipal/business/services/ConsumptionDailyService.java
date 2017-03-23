package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.business.manager.ReminderManager;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.common.util.NotificationID;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Consumption;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ReminderVO;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppointmentDetailActivity;

public class ConsumptionDailyService extends IntentService {
    public static final String TAG = "[CONSUME DAILY SERVICE]";

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder builder;
    public static final int NOTIFICATION_ID = 1;

    private ReminderManager reminderManager;

    public ConsumptionDailyService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        reminderManager = new ReminderManager(this);
        Log.i(TAG, "ConsumptionDailyService On Handle Intent started...");
        List<ReminderVO> reminders = reminderManager.getAllReminders();
        for (ReminderVO reminder : reminders) {
            if (isRemind(reminder)) {
                setupReminderAlarm(reminder);
            }

        }

//        if (intent != null && intent.hasExtra(getResources().getResourceName(R.string.consumption_parceable))) {
//            Consumption consumption = intent.getParcelableExtra(getResources().getResourceName(R.string.consumption_parceable));
//
//            sendNotification(consumption);
//            AppointmentAlarmReceiver.completeWakefulIntent(intent);
//            Log.w(TAG, "Call Consumption Alarm Receiver");
//        }
        Log.i(TAG, "ConsumptionDailyService On Handle Intent finished...");
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

            int requestCode = Integer.parseInt(reminder.getId() + "" + i);

            Intent notificationIntent = new Intent(getBaseContext(), ConsumptionBroadcastReceiver.class);
            notificationIntent.putExtra(Constant.REMINDER_BUNDLE, reminder);
            notificationIntent.putExtra(Constant.REQUEST_CODE, requestCode);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            // alarmManager.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), pendingIntent);


            //TODO Testing code
            // Getting current time and add the seconds in it
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, 10 * (i + 1));
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
//            TODO Testing code end


            startTime.add(Calendar.HOUR, intervalHr);
        }

        Log.i(TAG, "Setup Reminder Alarm Finished.");
    }

    private void sendNotification(Consumption consumption) {
        Log.w(TAG, "SEND CONSUMPTION NOTI");
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        int requestID = Integer.parseInt(NotificationID.CONSUMPTION + consumption.getId());

        // TODO Replace your consumption activities here
        PendingIntent contentIntent = PendingIntent.getActivity(this, requestID, new Intent(this, AppointmentDetailActivity.class), 0);

        //TODO Replace your message preparation here

//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle(getResources().getText(R.string.appointment_reminder_title))
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(appointment.getDescription()))
//                        .setContentText(appointment.getLocation());
//
//        mBuilder.setContentIntent(contentIntent);
//        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        Log.w(TAG, "END CONSUMPTION NOTI");
    }
}
