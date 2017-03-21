package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.common.util.NotificationID;

public class MedicineReplenishAlarmReceiver extends WakefulBroadcastReceiver {
    public static final String TAG = MedicineReplenishAlarmReceiver.class.getSimpleName();
    private static SimpleDateFormat timeFormatter = new SimpleDateFormat(Constant.TIME_FORMAT, Locale.getDefault());

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
        Calendar dateTimeCal = Calendar.getInstance();
        dateTimeCal.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), dateTime.getHours(), dateTime.getMinutes(), 0);
        int requestID = Integer.parseInt(NotificationID.REPLENISH + "" + Integer.toString(calendar.get(Calendar.MINUTE)) + Integer.toString(calendar.get(Calendar.SECOND)));
        if (dateTimeCal.compareTo(calendar) <= 0) {
            calendar.add(Calendar.DATE, 1);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), dateTimeCal.get(Calendar.HOUR_OF_DAY), dateTimeCal.get(Calendar.MINUTE), 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestID, intent, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.w(TAG, "MEDICINE REMINDER SET");
    }
}