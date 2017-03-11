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
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppointmentDetailActivity;

/**
 * Created by win on 11/3/17.
 */

public class AppointmentReminder extends IntentService {
    private static final String TAG = AppointmentReminder.class.getSimpleName();
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    public static final int NOTIFICATION_ID = 1;
    public AppointmentReminder() {
        super("Appointment Reminder");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.w(TAG, "On Handle Intent");

        if (intent != null && intent.hasExtra(getResources().getResourceName(R.string.appointment_parceable))) {
            Appointment appointment = intent.getParcelableExtra(getResources().getResourceName(R.string.appointment_parceable));

            sendNotification(appointment);
            AppointmentAlarmReceiver.completeWakefulIntent(intent);
            Log.w(TAG, "Call Appointment Alarm Receiver");
        }

        sendNotification(new Appointment());
        AppointmentAlarmReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(Appointment appointment) {
        Log.w(TAG, "SEND APPOINTMENT NOTI");
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        int requestID = NotificationID.APPOINTMENT + appointment.getId();

        PendingIntent contentIntent = PendingIntent.getActivity(this, requestID, new Intent(this, AppointmentDetailActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getResources().getText(R.string.appointment_reminder_title))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(appointment.getDescription()))
                .setContentText(appointment.getLocation());

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        Log.w(TAG, "END APPOINTMENT NOTI");

    }
}
