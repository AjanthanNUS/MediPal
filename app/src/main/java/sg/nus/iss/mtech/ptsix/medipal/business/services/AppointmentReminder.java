package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.NotificationID;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppointmentActivity;

public class AppointmentReminder extends IntentService {
    private static final String TAG = AppointmentReminder.class.getSimpleName();
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    public static final int NOTIFICATION_ID = 1;

    public AppointmentReminder() {
        super("appointment Reminders");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.w(TAG, "On Handle Intent");
        Log.w(TAG, "HasExtra " + intent.hasExtra("appointment") + "");


        if (intent != null && intent.hasExtra("appointment")) {
            Appointment appointment = intent.getParcelableExtra("appointment");

            sendNotification(appointment);
            AppointmentAlarmReceiver.completeWakefulIntent(intent);
            Log.w(TAG, "Call appointment Alarm Receiver");
        }
    }

    private void sendNotification(Appointment appointment) {
        Log.w(TAG, "SEND APPOINTMENT NOTI");
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        int requestID = Integer.parseInt(NotificationID.APPOINTMENT + "" + appointment.getId());

        Intent appointmentIntent = new Intent(this, AppointmentActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(this, requestID, appointmentIntent, 0);
        Log.w(TAG, "appointment desc" + appointment.getDescription());
        Log.w(TAG, "appointment location" + appointment.getLocation());

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getResources().getText(R.string.appointment_reminder_title))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(appointment.getDescription()))
                        .setContentText(appointment.getDescription() + " at " + appointment.getLocation());

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        Log.w(TAG, "END APPOINTMENT NOTI");

    }
}
