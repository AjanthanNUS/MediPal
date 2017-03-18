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
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;

/**
 * Created by win on 12/3/17.
 */

public class MedicineReplenishAlarmReceiver extends WakefulBroadcastReceiver {
    public static final String TAG = AppointmentAlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w(TAG, "START MEDICINE REPLENISH RECEIVE");
        Intent service = new Intent(context, MedicineReplenishReminder.class);
        startWakefulService(context, service);
        Log.w(TAG, "END MEDICINE REPLENISH RECEIVE");
    }

    public static void setAlarm(Context context, Appointment appointment) {
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


        Intent intent = new Intent(context, AppointmentAlarmReceiver.class);
        intent.putExtra(context.getResources().getResourceName(R.string.appointment_parceable), appointment);

        int requestID = NotificationID.APPOINTMENT + appointment.getId();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestID, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(appointment.getAppointmentDate());

        Log.w(TAG, calendar.getTime().toString());

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
        Log.w(TAG, "MEDICINE REMINDER SET");
    }
}