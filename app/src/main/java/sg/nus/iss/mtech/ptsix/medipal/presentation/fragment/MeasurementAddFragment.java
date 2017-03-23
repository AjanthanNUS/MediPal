package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.asynctask.MeasurementDeleteAsyncTask;
import sg.nus.iss.mtech.ptsix.medipal.business.asynctask.MeasurementSaveAsyncTask;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.MeasurementDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Measurement;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MeasurementActivity;

public class MeasurementAddFragment extends Fragment {

    private EditText MeaSystolic, MeaDiastolic, MeaPulse, MeaTemperature, MeaWeight, MeaMeasureOn, MeaComment;
    private Button btnSave, btnCancel;

    private MeasurementSaveAsyncTask measurementSaveAsyncTask;
    private Date date = new Date();


    DatePickerDialog datePickerDialog;
    java.util.Calendar dateCalendar;

    public MeasurementAddFragment() {
    }

    private SimpleDateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT, Locale.getDefault());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        measurementSaveAsyncTask = new MeasurementSaveAsyncTask(getActivity());
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this.getContext(), new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateCalendar = Calendar.getInstance();
                dateCalendar.set(year, monthOfYear, dayOfMonth);
                date = dateCalendar.getTime();
                MeaMeasureOn.setText(formatter.format(date));
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
        MeaComment = (EditText) rootView.findViewById(R.id.eComment);
        MeaMeasureOn = (EditText) rootView.findViewById(R.id.eMeasuredOn);
        MeaMeasureOn.setText(formatter.format(date.getTime()));
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

                measurementSaveAsyncTask = new MeasurementSaveAsyncTask(getActivity());
                Measurement measurement = createMeasurementFromInput();
                if(CheckDataFields(measurement)) {
                    measurementSaveAsyncTask.execute(measurement);
                    resetFields();
                    ((MeasurementActivity) getActivity()).switchTab(0, -1);
                }
                else
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setMessage( Constant.MEAS_ASK_TO_KEY_IN_DATA);
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
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

    private boolean CheckDataFields(Measurement measurement_in) {

        int i = 0;
        int b = 0;
        boolean result = true;

        if((measurement_in.getEventPulse()==0)
            &&(measurement_in.getEventDiastolic()==0)
            &&(measurement_in.getEventSystolic()==0)
            &&(measurement_in.getEventTemperature()==0)
            &&(measurement_in.getEventWeight()==0))
        {
            result = false;
        }
        return result;

    }

    private Measurement createMeasurementFromInput() {
        Measurement measurement = new Measurement();

        measurement.setEventMeasureOn(date);

        if (MeaWeight.getText().length() != 0) {
            measurement.setEventWeight(Integer.parseInt(MeaWeight.getText().toString()));
        } else {
            measurement.setEventWeight(0);
        }

        if (MeaTemperature.getText().length() != 0) {
            measurement.setEventTemperature(Float.parseFloat(MeaTemperature.getText().toString()));
        } else {
            measurement.setEventTemperature(0);
        }

        if (MeaPulse.getText().length() != 0) {
            measurement.setEventPulse(Integer.parseInt(MeaPulse.getText().toString()));
        } else {
            measurement.setEventPulse(0);
        }

        if (MeaDiastolic.getText().length() != 0) {
            measurement.setEventDiastolic(Integer.parseInt(MeaDiastolic.getText().toString()));
        } else {
            measurement.setEventDiastolic(0);
        }

        if (MeaSystolic.getText().length() != 0) {
            measurement.setEventSystolic(Integer.parseInt(MeaSystolic.getText().toString()));
        } else {
            measurement.setEventSystolic(0);
        }

        if (MeaComment.getText().length() != 0) {
            measurement.setComment(MeaComment.getText().toString());
        } else {
            measurement.setComment("");
        }

        return measurement;
    }

    private void resetFields() {
        MeaWeight.setText("");
        MeaTemperature.setText("");
        MeaPulse.setText("");
        MeaDiastolic.setText("");
        MeaSystolic.setText("");
        MeaComment.setText("");

    }

}
