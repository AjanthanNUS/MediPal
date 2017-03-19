package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DailyReminderBroadcastReceiver extends BroadcastReceiver {
    private static final String DAILY_REMINDER_RECEIVER = "[DAILY REMIND RECEIVER]";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(DAILY_REMINDER_RECEIVER, "Daily Broadcast Receiver received.");
        Log.i(DAILY_REMINDER_RECEIVER, "Starting the Service to set Reminders for the day...");

        Intent i = new Intent(context, ConsumptionDailyService.class);
        context.startService(i);

        Log.i(DAILY_REMINDER_RECEIVER, "Daily Broadcast Receiver finished.");
    }
}
