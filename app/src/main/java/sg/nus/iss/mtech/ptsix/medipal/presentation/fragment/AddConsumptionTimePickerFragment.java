package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;

/**
 * Created by Ajanthan on 3/17/2017.
 */

public class AddConsumptionTimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        EditText startTimeView = (EditText) getActivity().findViewById(R.id.et_consumed_time);
        String selectedTime = startTimeView.getText().toString();
        Date selectedDate = CommonUtil.convertStringToDate(selectedTime, Constant.TIME_FORMAT);

      Calendar  selectedCal = CommonUtil.dateToCalendar( selectedDate);

        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(), this, selectedCal.get(Calendar.HOUR), selectedCal.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        EditText startTimeView = (EditText) getActivity().findViewById(R.id.et_consumed_time);
        Calendar consumeTime = Calendar.getInstance();
        consumeTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        consumeTime.set(Calendar.MINUTE, minute);

        String formattedTime = CommonUtil.getFormattedTime(consumeTime.getTime());

        //Display the user changed time on TextView
        startTimeView.setText(String.valueOf(formattedTime));
    }
}
