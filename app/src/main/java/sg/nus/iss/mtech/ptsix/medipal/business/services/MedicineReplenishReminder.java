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
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppointmentDetailActivity;


public class MedicineReplenishReminder extends IntentService {
    public static final String TAG = ConsumptionReminder.class.getSimpleName();

    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    public static final int NOTIFICATION_ID = 1;
    public MedicineReplenishReminder() {
        super("MedicineReplenishReminder");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.w(TAG, "On Handle Intent");

        if (intent != null && intent.hasExtra(getResources().getResourceName(R.string.replenish_parceable))) {
            Medicine medicine = intent.getParcelableExtra(getResources().getResourceName(R.string.replenish_parceable));

            sendNotification(medicine);
            MedicineReplenishAlarmReceiver.completeWakefulIntent(intent);
            Log.w(TAG, "Call Medicine Replenish Alarm Receiver");
        }
    }

    private void sendNotification(Medicine medicine) {
        Log.w(TAG, "SEND REPLENISH NOTI");
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        int requestID = Integer.parseInt(NotificationID.CONSUMPTION + "" +medicine.getId());

        // TODO Replace your medicine replenish activities here
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
        Log.w(TAG, "END REPLENISH NOTI");

    }
}
