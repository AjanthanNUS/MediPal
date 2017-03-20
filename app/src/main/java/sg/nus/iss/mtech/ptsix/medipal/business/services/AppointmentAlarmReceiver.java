package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;

import sg.nus.iss.mtech.ptsix.medipal.common.util.NotificationID;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;

/**
 * Created by win on 11/3/17.
 */

public class AppointmentAlarmReceiver extends WakefulBroadcastReceiver {
    public static final String TAG = AppointmentAlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w(TAG, "START APPOINTMENT RECEIVE");
        Intent service = new Intent(context, AppointmentReminder.class);

        Appointment appointment = intent.getParcelableExtra("appointment");
        service.putExtra("appointment", appointment);

        startWakefulService(context, service);
        Log.w(TAG, "END APPOINTMENT RECEIVE");
    }

    public static void setAlarm(Context context, Appointment appointment) {
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


        Intent intent = new Intent(context, AppointmentAlarmReceiver.class);
        intent.putExtra("appointment", appointment);

        int requestID = Integer.parseInt(NotificationID.APPOINTMENT + "" + appointment.getId());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestID, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(appointment.getAppointmentDate());

        Log.w(TAG, calendar.getTime().toString());


        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
        Log.w(TAG, "APPOINTMENT REMINDER SET");
    }
}
