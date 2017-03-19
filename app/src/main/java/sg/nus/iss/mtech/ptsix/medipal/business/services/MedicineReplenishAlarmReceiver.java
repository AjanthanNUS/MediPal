package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import sg.nus.iss.mtech.ptsix.medipal.common.util.NotificationID;

public class MedicineReplenishAlarmReceiver extends WakefulBroadcastReceiver {
    public static final String TAG = MedicineReplenishAlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w(TAG, "START MEDICINE REPLENISH RECEIVE");
        Intent service = new Intent(context, MedicineReplenishReminder.class);

        startWakefulService(context, service);
        Log.w(TAG, "END MEDICINE REPLENISH RECEIVE");
    }

    public static void setAlarm(Context context, Date dateTime) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, MedicineReplenishAlarmReceiver.class);

        Date ddd = new Date();
        int requestID = Integer.parseInt(NotificationID.REPLENISH + "" + ddd.getMinutes() + ddd.getSeconds());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestID, intent, 0);

        Calendar calendar = Calendar.getInstance();
        if(dateTime != null) {
            calendar.setTime(dateTime);
        }
        else {
            calendar.set(Calendar.HOUR_OF_DAY, 13);
            calendar.set(Calendar.MINUTE, 10);
        }

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 100, pendingIntent);
        Log.w(TAG, "MEDICINE REMINDER SET");
    }
}