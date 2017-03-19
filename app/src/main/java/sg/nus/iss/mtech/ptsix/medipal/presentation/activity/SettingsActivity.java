package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;

public class SettingsActivity extends AppCompatActivity {

    private Switch tutorialSwitch;
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
    }
}
