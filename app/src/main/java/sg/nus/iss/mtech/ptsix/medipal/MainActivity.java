package sg.nus.iss.mtech.ptsix.medipal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.Date;

import sg.nus.iss.mtech.ptsix.medipal.business.manager.ReminderManager;
import sg.nus.iss.mtech.ptsix.medipal.business.services.MedicineReplenishAlarmReceiver;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminders;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AddConsumptionActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppTourActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppointmentActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.CategoriesActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ConsumptionActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.HealthBioActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ICEContactActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MeasurementActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MedicineActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.PersonalActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.SettingsActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ImageAdapter;

public class MainActivity extends AppCompatActivity {

    private final String MAIN_ACTIVITY = "[MAIN ACTIVITY]";
    private Toolbar toolbar = null;
    private SharedPreferences sharedPreferences;
    private GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startConsumptionDailyNotificationService();

        if (isFirstTimeAccess()) {
            Intent myIntent = new Intent(MainActivity.this, AppTourActivity.class);
            startActivity(myIntent);
        } else if (getTutorialRepeatStatus()) {
            Intent myIntent = new Intent(MainActivity.this, AppTourActivity.class);
            startActivity(myIntent);
        }

        setupHomeScreen();

        // setupAlarm(10);
        setupToolbar();
        startMedicineReplenishReminder();
    }

    private void startConsumptionDailyNotificationService() {
        //Check if the DailyNotification service Already Started with OnBootReceiver

        Intent intent = new Intent();
        intent.setAction("sg.nus.iss.mtech.ptsix.medipal.MainActivity");
        sendBroadcast(intent);

    }

    private void setupHomeScreen() {
        gridview = (GridView) findViewById(R.id.gridview);

        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent myIntent = null;
                switch (position) {
                    case 0:
                        myIntent = new Intent(MainActivity.this, MedicineActivity.class);
                        break;
                    case 1:
                        myIntent = new Intent(MainActivity.this, ConsumptionActivity.class);
                        break;
                    case 2:
                        myIntent = new Intent(MainActivity.this, AppointmentActivity.class);
                        break;
                    case 3:
                        myIntent = new Intent(MainActivity.this, MeasurementActivity.class);
                        break;
                    case 4:
                        myIntent = new Intent(MainActivity.this, CategoriesActivity.class);
                        break;
                    case 5:
                        myIntent = new Intent(MainActivity.this, PersonalActivity.class);
                        break;
                    case 6:
                        myIntent = new Intent(MainActivity.this, HealthBioActivity.class);
                        break;
                    case 7:
                        myIntent = new Intent(MainActivity.this, ICEContactActivity.class);
                        break;
                    case 8:
                        myIntent = new Intent(MainActivity.this, SettingsActivity.class);
                        break;
                    case 9:
                        myIntent = new Intent(MainActivity.this, AppTourActivity.class);
                        break;
                    default:
                        break;
                }
                startActivity(myIntent);
            }
        });
    }


    private boolean getTutorialRepeatStatus() {

        boolean repeatFlag = false;

        sharedPreferences = getSharedPreferences(getPackageName() + Constant.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        repeatFlag = sharedPreferences.getBoolean(Constant.TUTORIAL_REPEAT_SETTINGS_LABEL, false);

        return repeatFlag;
    }

    private boolean isFirstTimeAccess() {
        boolean firstTime = false;

        sharedPreferences = getSharedPreferences(getPackageName() + Constant.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        firstTime = sharedPreferences.getBoolean(Constant.TUTORIAL_REPEAT_SETTINGS_LABEL, true);

        if (firstTime) {
            setDefaultPreference();
        }
        return firstTime;
    }

    private void setDefaultPreference() {
        sharedPreferences = getSharedPreferences(getPackageName() + Constant.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constant.TUTORIAL_REPEAT_SETTINGS_LABEL, false);
        editor.putBoolean(Constant.USER_CREATED_SETTINGS_LABEL, false);
        editor.putString(Constant.THRESHOLD_TIME_SETTINGS_LABEL, Constant.THRESHOLD_TIME_SETTINGS_DEFAULT);
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

}