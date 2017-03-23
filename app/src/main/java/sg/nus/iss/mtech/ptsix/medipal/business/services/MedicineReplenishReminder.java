package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.NotificationID;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MedicineActivity;

public class MedicineReplenishReminder extends IntentService {
    public static final String TAG = ConsumptionDailyService.class.getSimpleName();

    private NotificationManager mNotificationManager;
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

        Log.w(TAG, "Call medicine Replenish Alarm Receiver");
    }

    private void sendNotification(List<String> medicineNameList) {
        Log.w(TAG, "SEND REPLENISH NOTIFICATION");
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Calendar cal = Calendar.getInstance();
        int requestID = Integer.parseInt(NotificationID.REPLENISH + "" + Integer.toString(cal.get(Calendar.MINUTE)) + Integer.toString(cal.get(Calendar.SECOND)));
        Intent medicineIntent = new Intent(this, MedicineActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, requestID, medicineIntent, 0);
        StringBuilder notificationMessage = new StringBuilder();
        notificationMessage.append("Required: ");

        for (String medicineName : medicineNameList) {
            notificationMessage.append(medicineName);
            notificationMessage.append(",");
        }
        String notificationMessageString = notificationMessage.toString();
        notificationMessageString = notificationMessageString.substring(0, notificationMessageString.length() - 1);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_app_icon);
        mBuilder.setContentTitle(getResources().getText(R.string.medicine_replenish_reminder_title));
        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText("This is a replenish reminder."));
        mBuilder.setContentText(notificationMessageString);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

        Log.w(TAG, "END REPLENISH NOTIFICATION");
    }
}
