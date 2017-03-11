package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppointmentDetailActivity;

/**
 * Created by win on 11/3/17.
 */

public class AppointmentReminder extends IntentService {
    private NotificationManager mNotificationManger;
    NotificationCompat.Builder builder;
    public static final int NOTIFICATION_ID = 1;
    public AppointmentReminder() {
        super("Appointment Reminder");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sendNotification("Appointment 1");
        AppointmentAlarmReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {
        Log.w("INFO", "SEND NOTI");
        mNotificationManger = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, AppointmentDetailActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Appointment Alert")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManger.notify(NOTIFICATION_ID, mBuilder.build());
        Log.w("INFO", "END NOTI");

    }
}
