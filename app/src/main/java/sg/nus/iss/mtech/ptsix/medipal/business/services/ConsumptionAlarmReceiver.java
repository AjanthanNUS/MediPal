package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.NotificationID;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Consumption;


/**
 * Created by win on 12/3/17.
 */

public class ConsumptionAlarmReceiver extends WakefulBroadcastReceiver {
    public static final String TAG = ConsumptionAlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w(TAG, "START CONSUMPTION RECEIVE");
        Intent service = new Intent(context, ConsumptionDailyService.class);
        startWakefulService(context, service);
        Log.w(TAG, "END CONSUMPTION RECEIVE");
    }

    public static void setAlarm(Context context, Consumption consumption) {
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


        Intent intent = new Intent(context, ConsumptionAlarmReceiver.class);
        intent.putExtra(context.getResources().getResourceName(R.string.consumption_parceable), consumption);

        int requestID = Integer.parseInt(NotificationID.CONSUMPTION + "" +consumption.getId());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestID, intent, 0);

        // TODO Need to replace with your logic
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(consumption.getConsumedOn());
        //

        Log.w(TAG, calendar.getTime().toString());

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
        Log.w(TAG, "CONSUMPTION REMINDER SET");
    }
}
