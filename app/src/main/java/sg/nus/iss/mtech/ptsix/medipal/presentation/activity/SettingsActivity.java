package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;

public class SettingsActivity extends AppCompatActivity {

    private SimpleDateFormat timeFormatter = new SimpleDateFormat(Constant.TIME_FORMAT, Locale.getDefault());
    private Switch tutorialSwitch;
    private EditText settingsThresholdTime;
    private Date thresholdTime;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        this.tutorialSwitch = (Switch) findViewById(R.id.tutorial_switch);
        this.settingsThresholdTime = (EditText) findViewById(R.id.settings_threshold_time);

        initializeSharedPreferece();
        setUserPreferences();
        handleAppTourSwitchSettings();
        handleThresholdTimeSettings();

    }

    private void handleThresholdTimeSettings() {
        this.settingsThresholdTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = (EditText) v;
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                        setThresholdTimeSharedPreference(calendar);
                        editText.setText(timeFormatter.format(calendar.getTime()));
                    }
                };
                Calendar timeCalendar = Calendar.getInstance();
                try {
                    timeCalendar.setTime(timeFormatter.parse(editText.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(SettingsActivity.this, R.string.generic_error, Toast.LENGTH_SHORT).show();
                }
                TimePickerDialog timePickerDialog = new TimePickerDialog(SettingsActivity.this, timeSetListener, timeCalendar.get(Calendar.HOUR_OF_DAY), timeCalendar.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        });
    }


    private void initializeSharedPreferece() {
        sharedPreferences = getSharedPreferences(getPackageName() + Constant.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
    }

    private void setUserPreferences() {
        setAppTourPreferenceSwich();
        setThresholdTimePreferences();
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

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constant.TUTORIAL_REPEAT_SETTINGS_LABEL, isChecked);
        editor.apply();

        if (isChecked) {
            Toast.makeText(this, Constant.APP_TOUR_REPEAT_ON_TEXT, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, Constant.APP_TOUR_REPEAT_OFF_TEXT, Toast.LENGTH_SHORT).show();
        }
    }

    private void setThresholdTimePreferences() {
        String dateString = sharedPreferences.getString(Constant.THRESHOLD_TIME_SETTINGS_LABEL, null);
        thresholdTime = CommonUtil.convertStringToDate(dateString, Constant.TIME_FORMAT);

        this.settingsThresholdTime.setText(timeFormatter.format(thresholdTime.getTime()));
    }

    private void setThresholdTimeSharedPreference(Calendar cal) {
        String dateString = CommonUtil.convertDateToString(cal.getTime(), Constant.TIME_FORMAT);

        SharedPreferences sharedPreferences = this.getSharedPreferences(getPackageName() + Constant.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constant.THRESHOLD_TIME_SETTINGS_LABEL, dateString);
        editor.apply();
    }
}
