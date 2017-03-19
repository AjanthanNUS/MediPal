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
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Consumption;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppointmentDetailActivity;


/**
 * Created by win on 12/3/17.
 */

public class ConsumptionReminder extends IntentService {
    public static final String TAG = ConsumptionReminder.class.getSimpleName();

    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    public static final int NOTIFICATION_ID = 1;
    public ConsumptionReminder() {
        super("Consumption Reminder");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.w(TAG, "On Handle Intent");

        if (intent != null && intent.hasExtra(getResources().getResourceName(R.string.consumption_parceable))) {
            Consumption consumption = intent.getParcelableExtra(getResources().getResourceName(R.string.consumption_parceable));

            sendNotification(consumption);
            AppointmentAlarmReceiver.completeWakefulIntent(intent);
            Log.w(TAG, "Call Consumption Alarm Receiver");
        }
    }

    private void sendNotification(Consumption consumption) {
        Log.w(TAG, "SEND CONSUMPTION NOTI");
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        int requestID = Integer.parseInt(NotificationID.CONSUMPTION + consumption.getId());

        // TODO Replace your consumption activities here
        PendingIntent contentIntent = PendingIntent.getActivity(this, requestID, new Intent(this, AppointmentDetailActivity.class), 0);

        //TODO Replace your message preparation here

//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle(getResources().getText(R.string.appointment_reminder_title))
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(appointment.getDescription()))
//                        .setContentText(appointment.getLocation());
//
//        mBuilder.setContentIntent(contentIntent);
//        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        Log.w(TAG, "END CONSUMPTION NOTI");

    }
}
