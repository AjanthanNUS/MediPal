package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.Date;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.NotificationID;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MedicineActivity;


public class MedicineReplenishReminder extends IntentService {
    public static final String TAG = ConsumptionDailyService.class.getSimpleName();

    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    public static final int NOTIFICATION_ID = 1;
    private MedicineService medicineService;

    public MedicineReplenishReminder() {
        super("MedicineReplenishReminder");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        medicineService = new MedicineService(this.getApplicationContext());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.w(TAG, "On Handle Intent");

        List<String> medicineNameList = medicineService.getNamesOfMedicineBelowThreshold();
        if (medicineNameList.size() > 0) {
            sendNotification(medicineNameList);
        }
        MedicineReplenishAlarmReceiver.completeWakefulIntent(intent);

        Log.w(TAG, "Call Medicine Replenish Alarm Receiver");
    }

    private void sendNotification(List<String> medicineNameList) {
        Log.w(TAG, "SEND REPLENISH NOTIFICATION");
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Date ddd = new Date();
        int requestID = Integer.parseInt(NotificationID.REPLENISH + "" + ddd.getMinutes() + ddd.getSeconds());

        Intent medicineIntent = new Intent(this, MedicineActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(this, requestID, medicineIntent, 0);
        for (String ttt : medicineNameList) {
            Log.d(ttt, ttt);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_app_icon);
        mBuilder.setContentTitle(getResources().getText(R.string.medicine_replenish_reminder_title));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText("This is a replenish reminder."));
        mBuilder.setContentText(ddd.getMinutes() + ddd.getSeconds() + "");

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

        Log.w(TAG, "END REPLENISH NOTIFICATION");
    }
}
