package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.MeasurementDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Measurement;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MeasurementActivity;
/**
 * Created by WONG_CH on 12-Mar-17.
 */
public class MeasurementAddFragment extends Fragment {

    private RadioGroup categoriesRemind;
    private EditText MeaSystolic, MeaDiastolic,MeaPulse,MeaTemperature,MeaWeight,MeaMeasureOn,MeaComment;
    private Boolean categoryRemind = true;
    private Button btnSave, btnCancel;
    private MeasurementDao measurementDao;
    private Date date = new Date();

    DatePickerDialog datePickerDialog;
    java.util.Calendar dateCalendar;
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    public MeasurementAddFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        measurementDao = new MeasurementDao(this.getContext());
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this.getContext(), new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateCalendar = Calendar.getInstance();
                dateCalendar.set(year, monthOfYear, dayOfMonth);
                date = dateCalendar.getTime();
                MeaMeasureOn.setText(formatter.format(dateCalendar.getTime()));
            }
        },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_measurement_add, container, false);

        MeaSystolic = (EditText) rootView.findViewById(R.id.eSystolic);
        MeaDiastolic = (EditText) rootView.findViewById(R.id.eDiastolic);
        MeaPulse = (EditText) rootView.findViewById(R.id.ePulse);
        MeaTemperature = (EditText) rootView.findViewById(R.id.eTemperature);
        MeaWeight = (EditText) rootView.findViewById(R.id.eWeight);
        MeaComment =  (EditText) rootView.findViewById(R.id.eComment);
        MeaMeasureOn = (EditText) rootView.findViewById(R.id.eMeasuredOn);

        MeaMeasureOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        btnSave = (Button) rootView.findViewById(R.id.btn_ok);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments().getInt("id") == -1) {
                    // Add new
                    if (true) {
                        measurementDao.save(createMeasurementFromInput());
                        Toast.makeText(getActivity(), "Saving new category completed", Toast.LENGTH_SHORT).show();
                        resetFields();
                        ((MeasurementActivity) getActivity()).switchTab(0, -1);
                    }
                } else {
                    // Update
                    if (true) {
                        measurementDao.save(createMeasurementFromInput());
                        Toast.makeText(getActivity(), "Updating category completed", Toast.LENGTH_SHORT).show();
                        resetFields();
                        ((MeasurementActivity) getActivity()).switchTab(0, -1);
                    }
                }
            }
        });

        btnCancel = (Button) rootView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancel the Category
                resetFields();
                ((MeasurementActivity) getActivity()).switchTab(0, -1);
            }
        });

        return rootView;
    }
    private Measurement createMeasurementFromInput() {
        Measurement measurement = new Measurement();

        measurement.setEventMeasureOn(date);
        measurement.setEventWeight(Integer.parseInt(MeaWeight.getText().toString()));
        measurement.setEventTemperature(Float.parseFloat(MeaTemperature.getText().toString()));
        measurement.setEventPulse(Integer.parseInt(MeaPulse.getText().toString()));
        measurement.setEventDiastolic(Integer.parseInt(MeaDiastolic.getText().toString()));
        measurement.setEventSystolic(Integer.parseInt(MeaSystolic.getText().toString()));
        measurement.setComment(MeaComment.getText().toString());
        return measurement;
    }

    private void resetFields() {
        MeaWeight.setText("");
        MeaTemperature.setText("");
        MeaPulse.setText("");
        MeaDiastolic.setText("");
        MeaSystolic.setText("");
        MeaComment.setText("");
        MeaMeasureOn.setText("");
    }

}
