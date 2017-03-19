package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class OnBootReceiver extends BroadcastReceiver {
    private static final String ON_BOOT_RECEIVER = "[ON BOOT RECEIVER]";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(ON_BOOT_RECEIVER, "On boot Broadcast receiver received...");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            setDailyAlarm(context);
        }
    }

    private void setDailyAlarm(Context context) {
        Log.i(ON_BOOT_RECEIVER, "Daily checking Alarm set started...");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent receiverIntent = new Intent(context, DailyReminderBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, receiverIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Log.d("TAG1", "Setup the alarm");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        long oneDayInterval = 24 * 60 * 60 * 1000;

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), oneDayInterval, pendingIntent);
        Log.i(ON_BOOT_RECEIVER, "Daily checking Alarm set successful!");
    }
}
