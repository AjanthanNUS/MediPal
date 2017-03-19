package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AppStartBroadcastReceiver extends BroadcastReceiver {
    private String APP_START_RECEIVER = "[APP START RECEIVER]";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(APP_START_RECEIVER, "App Start Broadcast Receiver received.");
        Log.i(APP_START_RECEIVER, "Starting the Service to set Reminders for the day...");

        Intent i = new Intent(context, ConsumptionDailyService.class);
        context.startService(i);

        Log.i(APP_START_RECEIVER, "App Start Broadcast Receiver finished.");
    }
}
