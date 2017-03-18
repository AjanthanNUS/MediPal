package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.manager.ReminderManager;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminder;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ReminderVO;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.ReminderTimePickerFragment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.util.UIConstants;

public class ReminderActivity extends AppCompatActivity {
    public static final String TIME_PICKER = "REMINDER_TIME_PICKER";
    private ViewHolder viewHolder;
    private ReminderManager reminderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        viewHolder = new ViewHolder();
        reminderManager = new ReminderManager(this);

        Reminder reminder = getIntent().getExtras().getParcelable(UIConstants.REMINDER);
        if (reminder != null) {

            ReminderVO reminderVO = reminderManager.castToReminderVo(reminder);
            //viewHolder.startTimeView.setText(reminderVO.getStartTime());
            viewHolder.frequencyView.setText(String.valueOf(reminderVO.getFrequency()));
            viewHolder.intervalView.setText(String.valueOf(reminderVO.getInterval()));

            viewHolder.startTimeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new ReminderTimePickerFragment();
                    newFragment.show(getFragmentManager(), TIME_PICKER);
                }
            });
        }
    }

    class ViewHolder {
        public final EditText startTimeView;
        public final EditText frequencyView;
        public final EditText intervalView;
        public final Button saveButtonView;

        public ViewHolder() {
            startTimeView = (EditText) findViewById(R.id.et_start_time);
            frequencyView = (EditText) findViewById(R.id.et_frequency);
            intervalView = (EditText) findViewById(R.id.et_interval);
            saveButtonView = (Button) findViewById(R.id.btn_reminder_save);
        }
    }
}
