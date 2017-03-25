package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;

public class AddConsumptionDatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        EditText consumeDateView = (EditText) getActivity().findViewById(R.id.et_consumed_date);
        String selectedTime = consumeDateView.getText().toString();
        Date consumeDate = new Date();
        try {
            consumeDate = CommonUtil.ddmmmyyyy2date(selectedTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        final Calendar c = CommonUtil.dateToCalendar(consumeDate);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        EditText consumeDateView = (EditText) getActivity().findViewById(R.id.et_consumed_date);
        Calendar consumeDate = Calendar.getInstance();
        consumeDate.set(Calendar.YEAR, year);
        consumeDate.set(Calendar.MONTH, month);
        consumeDate.set(Calendar.DAY_OF_MONTH, day);

        String conDate = CommonUtil.date2ddMMMYYYY(consumeDate.getTime());

        consumeDateView.setText(conDate);
    }
}
