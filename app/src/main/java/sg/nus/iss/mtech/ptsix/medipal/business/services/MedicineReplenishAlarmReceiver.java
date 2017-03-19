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

        Calendar calendar = Calendar.getInstance();
        int requestID = Integer.parseInt(NotificationID.REPLENISH + "" + Integer.toString(calendar.get(Calendar.MINUTE)) + Integer.toString(calendar.get(Calendar.SECOND)));
        calendar.setTime(dateTime);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestID, intent, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.w(TAG, "MEDICINE REMINDER SET");
    }
}