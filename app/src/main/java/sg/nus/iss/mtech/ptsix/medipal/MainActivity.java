package sg.nus.iss.mtech.ptsix.medipal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppTourActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppointmentActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.CategoriesActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ConsumptionActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MeasurementActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.HealthBioActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ICEContactActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MedicineActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.PersonalActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar = null;
    private  SharedPreferences sharedPreferences;
    private  Intent navigateIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isFirstTimeAccess()) {
            navigateIntent = new Intent(MainActivity.this, AppTourActivity.class);
            startActivity(navigateIntent);
        } else if (getTutorialRepeatStatus()) {
            navigateIntent = new Intent(MainActivity.this, AppTourActivity.class);
            startActivity(navigateIntent);
        }

        setupToolbar();

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

    public void openConsumables(View view) {
        navigateIntent = new Intent(MainActivity.this, ConsumptionActivity.class);
        startActivity(navigateIntent);
    }

    public void openMedicine(View view) {
        navigateIntent = new Intent(MainActivity.this, MedicineActivity.class);
        startActivity(navigateIntent);
    }

    public void openCategories(View view) {
        navigateIntent = new Intent(MainActivity.this, CategoriesActivity.class);
        startActivity(navigateIntent);
    }

    public void openAppointment(View view) {
        navigateIntent = new Intent(MainActivity.this, AppointmentActivity.class);
        startActivity(navigateIntent);
    }

    public void openMeasurement(View view) {
        Intent myIntent = new Intent(MainActivity.this, MeasurementActivity.class);
        startActivity(myIntent);
    }

    public void openICEContact(View view) {
        navigateIntent = new Intent(MainActivity.this, ICEContactActivity.class);
        startActivity(navigateIntent);
    }

    public void openHealthBio(View view) {
        navigateIntent = new Intent(MainActivity.this, HealthBioActivity.class);
        startActivity(navigateIntent);
    }


    public void openPersonalBio(View view) {

        navigateIntent = new Intent(MainActivity.this, PersonalActivity.class);
        startActivity(navigateIntent);
    }

    public void openSettings(View view) {
        navigateIntent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(navigateIntent);
    }

    public void openAppTour(View view) {
        navigateIntent = new Intent(MainActivity.this, AppTourActivity.class);
        startActivity(navigateIntent);
    }
}