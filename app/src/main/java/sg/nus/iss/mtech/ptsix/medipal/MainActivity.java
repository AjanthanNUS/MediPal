package sg.nus.iss.mtech.ptsix.medipal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.business.manager.ReminderManager;
import sg.nus.iss.mtech.ptsix.medipal.business.services.ConsumptionBroadcastReceiver;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminders;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ReminderVO;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AddConsumptionActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppointmentActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.CategoriesActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ConsumptionActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MeasurementActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MedicineActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.util.Constant;

public class MainActivity extends AppCompatActivity {
    private final String MAIN_ACTIVITY = "[MAIN ACTIVITY]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setAction("sg.nus.iss.mtech.ptsix.medipal.MainActivity");
        sendBroadcast(intent);

       // setupAlarm(10);
    }

    private void setupAlarm(int seconds) {
        Log.i(MAIN_ACTIVITY, "Alarm setup from main Activity...");
        ReminderManager reminderManager = new ReminderManager(this);
        List<ReminderVO> reminders = reminderManager.getAllReminders();

        if (reminders.size() > 0) {
            ReminderVO reminderVO = reminders.get(0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(getBaseContext(), ConsumptionBroadcastReceiver.class);

            intent.putExtra(sg.nus.iss.mtech.ptsix.medipal.common.util.Constant.REMINDER_BUNDLE, reminderVO);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Log.i(MAIN_ACTIVITY, "Setup the alarm");

            // Getting current time and add the seconds in it
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, seconds);
            long interval = 10 * 1000;
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), interval, pendingIntent);
        }
    }


    public void openConsumables(View view) {
        Intent myIntent = new Intent(MainActivity.this, ConsumptionActivity.class);
        startActivity(myIntent);
    }

    public void openMedicine(View view) {
        Intent myIntent = new Intent(MainActivity.this, MedicineActivity.class);
        startActivity(myIntent);
    }

    public void openCategories(View view) {
        Intent myIntent = new Intent(MainActivity.this, CategoriesActivity.class);
        startActivity(myIntent);
    }

    public void openAppointment(View view) {
        Intent myIntent = new Intent(MainActivity.this, AppointmentActivity.class);
        startActivity(myIntent);
    }

    public void openMeasurement(View view) {
        Intent myIntent = new Intent(MainActivity.this, MeasurementActivity.class);
        startActivity(myIntent);
    }

    public void reminderEdit(View view) {
        ReminderManager reminderManager = new ReminderManager(this);
        Reminders reminders = reminderManager.getAllReminders().get(1);

        Intent myIntent = new Intent(MainActivity.this, AddConsumptionActivity.class);
        myIntent.putExtra(Constant.REMINDER, reminders);
        startActivity(myIntent);
    }
}
