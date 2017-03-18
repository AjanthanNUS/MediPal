package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import sg.nus.iss.mtech.ptsix.medipal.R;

/**
 * Created by Ajanthan on 3/17/2017.
 */

public class ReminderTimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        EditText startTimeView = (EditText) getActivity().findViewById(R.id.et_start_time);
        String selectedTime = startTimeView.getText().toString();
        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        EditText startTimeView = (EditText) getActivity().findViewById(R.id.et_start_time);
        String aMpM = "AM";
        if (hourOfDay > 11) {
            aMpM = "PM";
        }

        //Make the 24 hour time format to 12 hour time format
        int currentHour;
        if (hourOfDay > 11) {
            currentHour = hourOfDay - 12;
        } else {
            currentHour = hourOfDay;
        }

        //Display the user changed time on TextView
        startTimeView.setText(String.valueOf(currentHour)
                + " : " + String.valueOf(minute) + " " + aMpM + "\n");
    }
}
