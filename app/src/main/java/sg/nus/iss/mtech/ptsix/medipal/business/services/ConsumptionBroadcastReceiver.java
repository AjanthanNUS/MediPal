package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.manager.ConsumptionManager;
import sg.nus.iss.mtech.ptsix.medipal.business.manager.ReminderManager;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.common.util.NotificationID;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Consumption;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminders;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ReminderVO;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AddConsumptionActivity;

public class ConsumptionBroadcastReceiver extends WakefulBroadcastReceiver {
    private final String TAG = "[CONSUMPTION RECEIVER]";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "BroadcastReceiver, in onReceive: Start notification");

        ReminderManager reminderManager = new ReminderManager(context);
        Reminders reminder = intent.getParcelableExtra(Constant.REMINDER_BUNDLE);
        ReminderVO reminderVO = reminderManager.castToReminderVo(reminder);

        String remindTime = intent.getStringExtra(Constant.REMIND_TIME);
        Date reminderDate = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(Constant.DATE_TIME_FORMAT, Locale.getDefault());
        try {
            reminderDate = dateFormatter.parse(remindTime);
        } catch (ParseException e) {
            Log.e(TAG, "DATE format issue");
        }

        Consumption consumption = new Consumption();
        consumption.setConsumedOn(reminderDate);
        consumption.setMedicineID(reminderVO.getMedicine().getId());
        consumption.setQuantity(0);

        ConsumptionManager consumptionManager = new ConsumptionManager(context);
        //Set the consumption entry for missed consumption
        consumption = consumptionManager.insertConsumption(consumption);
        //set the consume quantity to show the user
        consumption.setQuantity(reminderVO.getMedicine().getConsumeQuantity());

        String notificationText = reminderVO.getMedicine().getConsumeQuantity() + " "
                + CommonUtil.getDosageList().get(reminderVO.getMedicine().getDosage()) + " of " + reminderVO.getMedicine().getMedicine();

        int requestCode = intent.getIntExtra(Constant.REQUEST_CODE, 0);

        int notificationId = Integer.parseInt(NotificationID.CONSUMPTION) + requestCode;

        // Intent i = new Intent(context, ConsumptionDailyService.class);
        //context.startService(i);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_app_icon)
                        .setContentTitle(context.getResources().getText(R.string.consumption_reminder_title))
                        .setContentText(notificationText)
                        .setAutoCancel(true)
                        /*.setStyle(new NotificationCompat.BigTextStyle().bigText(reminderVO.getMedicine().getDescription()))*/;

        Intent resultIntent = new Intent(context, AddConsumptionActivity.class);
        resultIntent.putExtra(Constant.CONSUMPTION_BUNDLE, consumption);
        resultIntent.putExtra(Constant.NOTIFICATION_ID, notificationId);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(AddConsumptionActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(requestCode, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(notificationId, mBuilder.build());

        Log.i(TAG, "Broadcast receiver finished Notification");
    }
}
