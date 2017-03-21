package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;

public class SettingsActivity extends AppCompatActivity {

    private Switch tutorialSwitch;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        tutorialSwitch = (Switch) findViewById(R.id.tutorialSwitch);
        initializeSharedPreferece();
        setUserPreferences();
        handleAppTourSwitchSettings();
    }

    private void initializeSharedPreferece() {
        sharedPreferences = getSharedPreferences(getPackageName() + Constant.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
    }

    private void setUserPreferences() {
        setAppTourPreferenceSwich();
    }

    private void setAppTourPreferenceSwich() {
        boolean tutorialRepeatFlag = false;

        tutorialRepeatFlag = sharedPreferences.getBoolean(Constant.TUTORIAL_REPEAT_SETTINGS_LABEL, false);
        tutorialSwitch.setChecked(tutorialRepeatFlag);
    }

    private void handleAppTourSwitchSettings() {
        tutorialSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeAppTourPreference(isChecked);
            }
        });
    }

    private void changeAppTourPreference(boolean isChecked) {
        sharedPreferences = getSharedPreferences(getPackageName() + Constant.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constant.TUTORIAL_REPEAT_SETTINGS_LABEL, isChecked);
        editor.apply();

        if (isChecked) {
            Toast.makeText(this, Constant.APP_TOUR_REPEAT_ON_TEXT, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, Constant.APP_TOUR_REPEAT_OFF_TEXT, Toast.LENGTH_SHORT).show();
        }
    }
}
