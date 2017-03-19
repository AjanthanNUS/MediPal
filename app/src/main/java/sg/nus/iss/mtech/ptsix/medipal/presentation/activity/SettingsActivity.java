package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;

import java.util.Date;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;

public class SettingsActivity extends AppCompatActivity {

    private Switch tutorialSwitch;
    private Date thresholdTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        tutorialSwitch = (Switch) findViewById(R.id.tutorialSwitch);
        getUserPreferences();
    }

    private void getUserPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName() + Constant.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);

        boolean tutorialRepeatFlag = sharedPreferences.getBoolean(Constant.TUTORIAL_REPEAT_SETTINGS_LABEL, false);
        tutorialSwitch.setChecked(tutorialRepeatFlag);

        String dateString = sharedPreferences.getString(Constant.THRESHOLD_TIME_SETTINGS_LABEL, null);
        thresholdTime = CommonUtil.convertStringToDate(dateString, Constant.TIME_FORMAT);
    }
}
