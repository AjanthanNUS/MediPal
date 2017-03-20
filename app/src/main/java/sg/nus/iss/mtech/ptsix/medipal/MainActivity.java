package sg.nus.iss.mtech.ptsix.medipal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.business.manager.ReminderManager;
import sg.nus.iss.mtech.ptsix.medipal.business.services.ConsumptionBroadcastReceiver;
import sg.nus.iss.mtech.ptsix.medipal.business.services.MedicineReplenishAlarmReceiver;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminders;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ReminderVO;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AddConsumptionActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppointmentActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.CategoriesActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ConsumptionActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.HealthBioActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ICEContactActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MeasurementActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MedicineActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.PersonalActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    private final String MAIN_ACTIVITY = "[MAIN ACTIVITY]";
    private Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setAction("sg.nus.iss.mtech.ptsix.medipal.MainActivity");
        sendBroadcast(intent);

        // setupAlarm(10);
        setupToolbar();
        setDefaultPreference();
        startMedicineReplenishReminder();
    }

    private void setDefaultPreference() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(getPackageName() + Constant.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!sharedPreferences.contains(Constant.THRESHOLD_TIME_SETTINGS_LABEL)) {
            editor.putString(Constant.THRESHOLD_TIME_SETTINGS_LABEL, Constant.THRESHOLD_TIME_SETTINGS_DEFAULT);
        }
        if(!sharedPreferences.contains(Constant.USER_CREATED_SETTINGS_LABEL)) {
            editor.putBoolean(Constant.USER_CREATED_SETTINGS_LABEL, false);
        }
        if(!sharedPreferences.contains(Constant.TUTORIAL_REPEAT_SETTINGS_LABEL)) {
            editor.putBoolean(Constant.TUTORIAL_REPEAT_SETTINGS_LABEL, false);
        }
        editor.apply();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        getSupportActionBar().hide();
        toolbar.setTitle(Constant.APP_TITLE);
        toolbar.setSubtitle(Constant.APP_SUB_TITLE);
        toolbar.setLogo(R.drawable.ic_app_icon);
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

    private void startMedicineReplenishReminder() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(getPackageName() + Constant.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        String dateString = sharedPreferences.getString(Constant.THRESHOLD_TIME_SETTINGS_LABEL, null);
        Date date = CommonUtil.convertStringToDate(dateString, Constant.TIME_FORMAT);
        if (date != null) {
            MedicineReplenishAlarmReceiver.setAlarm(this.getApplicationContext(), date);
        } else {
            MedicineReplenishAlarmReceiver.setAlarm(this.getApplicationContext(), new Date());
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
        myIntent.putExtra(Constant.REMINDER_BUNDLE, reminders);
        startActivity(myIntent);
    }

    public void openICEContact(View view) {
        Intent myIntent = new Intent(MainActivity.this, ICEContactActivity.class);
        startActivity(myIntent);
    }

    public void openHealthBio(View view) {
        Intent myIntent = new Intent(MainActivity.this, HealthBioActivity.class);
        startActivity(myIntent);
    }

    public void openPersonalBio(View view) {
        Intent personalBioIntent = new Intent(MainActivity.this, PersonalActivity.class);
        startActivity(personalBioIntent);
    }

    public void openSettings(View view) {
        Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(settingsIntent);
    }
}