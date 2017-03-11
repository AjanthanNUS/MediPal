package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

public class MedicineReplenishService extends IntentService {

    private static final String TAG = "ReplenishService";
    public static final String PARAM_IN_MSG = "imsg";

    NotificationCompat.Builder mBuilder;


    public MedicineReplenishService() {
        super(MedicineReplenishService.class.getName());
    }

    public MedicineReplenishService(String name) {
        super(MedicineReplenishService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "Medicine Replenish Service Started!");

        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());


    }
}
