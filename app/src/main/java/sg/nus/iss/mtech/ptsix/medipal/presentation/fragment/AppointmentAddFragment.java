package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.asynctask.AppointmentAsyncTask;
import sg.nus.iss.mtech.ptsix.medipal.business.asynctask.AppointmentGetAsyncTask;
import sg.nus.iss.mtech.ptsix.medipal.business.asynctask.AppointmentUpdateAsyncTask;
import sg.nus.iss.mtech.ptsix.medipal.common.exception.AppointmentExistException;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppointmentActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppointmentDetailActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.util.Constant;


public class AppointmentAddFragment extends Fragment {

    public static final String TAG = AppointmentAddFragment.class.getSimpleName();

    private EditText etLocation, etDescription, etDate, etTime = null;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());
    private SimpleDateFormat dbDateFormatter = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
    private Button btnAdd, btnCancel = null;
    Calendar currentCal = Calendar.getInstance();
    Calendar selectedDate = Calendar.getInstance();
    private Appointment mAppointment;

    public AppointmentAddFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_appointment_add, container, false);
        rootView =initializeView(rootView);

        return rootView;

    }

    private View initializeView(View rootView) {

        etLocation = (EditText) rootView.findViewById(R.id.et_appointment_location);
        etDescription = (EditText) rootView.findViewById(R.id.et_appointment_description);
        etDate = (EditText) rootView.findViewById(R.id.et_appointment_date);
        etTime = (EditText) rootView.findViewById(R.id.et_appointment_time);
        btnAdd = (Button) rootView.findViewById(R.id.btn_appointment_add);
        btnCancel = (Button) rootView.findViewById(R.id.btn_appointment_cancel);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                selectedDate = calendar;
                                etDate.setText(dateFormatter.format(calendar.getTime()));
                            }
                        };
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(getActivity(), onDateSetListener,
                                currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
                                currentCal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        etTime.setText(timeFormatter.format(currentCal.getTime()));
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(currentCal.getTime());
        timeCalendar.set(Calendar.HOUR, currentCal.get(Calendar.HOUR));
        timeCalendar.set(Calendar.MINUTE, currentCal.get(Calendar.MINUTE));
        timeCalendar.set(Calendar.AM_PM, currentCal.get(Calendar.AM_PM));
        timeCalendar.add(Calendar.HOUR, 1);
        etTime.setText(timeFormatter.format(timeCalendar.getTime()));

        View.OnClickListener timeClickListener = new View.OnClickListener() {
            @Override public void onClick(final View v) {
                final EditText editText = (EditText) v;
                TimePickerDialog.OnTimeSetListener timeSetListener =
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                        calendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                                editText.setText(timeFormatter.format(calendar.getTime()));
                            }
                        };
                Calendar timeCalendar = Calendar.getInstance();
                try {
                    timeCalendar.setTime(timeFormatter.parse(editText.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(getActivity(), R.string.generic_error, Toast.LENGTH_SHORT)
                            .show();
                }
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(getActivity(), timeSetListener,
                                timeCalendar.get(Calendar.HOUR_OF_DAY), timeCalendar.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        };
        etTime.setOnClickListener(timeClickListener);

        btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValid()) {
                    return;
                }

                Appointment appointment = prepareAppointment();

                try {
                    if (getArguments().getInt("id") == Constant.APPOINTMENT_ADD_INVALID_ID)  {
                        AppointmentAsyncTask appointmentAsyncTask = new AppointmentAsyncTask(getContext());
                        appointmentAsyncTask.execute(appointment);

                        Object returnObj = appointmentAsyncTask.get();

                        if (returnObj instanceof AppointmentExistException) {
                            Toast.makeText(getContext(), "Appointment already existed at that time.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Appointment created successfully.", Toast.LENGTH_SHORT).show();
                            ((AppointmentActivity) getActivity()).switchTab(Constant.APPOINTMENT_TAB_LIST_INDEX, Constant.APPOINTMENT_ADD_INVALID_ID);
                        }

                    } else {
                        AppointmentUpdateAsyncTask updateAsyncTask = new AppointmentUpdateAsyncTask(getContext());
                        updateAsyncTask.execute(appointment);
                        updateAsyncTask.get();
                        Toast.makeText(getContext(), "Appointment updated successfully", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), "Something wrong!", Toast.LENGTH_SHORT).show();
                }



            }
        });

        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAll();

                ((AppointmentActivity) getActivity()).switchTab(Constant.APPOINTMENT_TAB_LIST_INDEX, Constant.APPOINTMENT_ADD_INVALID_ID);
            }
        });

        return rootView;
    }

    private void resetAll() {
        mAppointment = new Appointment();
        bindUI();

    }

    private boolean isValid() {
        boolean isValid = true;

        if (TextUtils.isEmpty(etDate.getText().toString().trim())) {
            etDate.setError("Please fill in appointment date.");
            isValid = false;
        }

        try {
            dateFormatter.parse(etDate.getText().toString());
        } catch (Exception e) {
            etDate.setError("Invalid date format.");
            isValid = false;
        }

        if (TextUtils.isEmpty(etTime.getText().toString().trim())) {
            etTime.setError("Please fill in appointment time.");
            isValid = false;
        }

        try {
            timeFormatter.parse(etTime.getText().toString());
        } catch (Exception e) {
            etDate.setError("Invalid date format.");
            isValid = false;
        }

        if (TextUtils.isEmpty(etDescription.getText().toString().trim())) {
            etDescription.setError("Please fill in appointment description.");
            isValid = false;
        }

        if (TextUtils.isEmpty(etLocation.getText().toString().trim())) {
            etDescription.setError("Please fill in appointment location.");
            isValid = false;
        }

        return isValid;
    }

    private Appointment prepareAppointment() {
        if (mAppointment == null || mAppointment.getId() == 0) {
            mAppointment = new Appointment();
        }

        mAppointment.setDescription(etDescription.getText().toString());
        mAppointment.setLocation(etLocation.getText().toString());

        try {
            String dbDateTime = etDate.getText().toString().trim() + " " + etTime.getText().toString().trim();
            mAppointment.setAppointmentDate(dbDateFormatter.parse(dbDateTime));
        } catch(Exception e) {
            Log.w(AppointmentDetailActivity.class.getName(), e);
        }
        return mAppointment;
    }

    public void bindUI() {
        etLocation.setText(mAppointment.getLocation());
        etDescription.setText(mAppointment.getDescription());
        etDate.setText(dateFormatter.format(mAppointment.getAppointmentDate()));
        etTime.setText(timeFormatter.format(mAppointment.getAppointmentDate()));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        int id = getArguments().getInt("id");
        if (getView() != null) {
            if (isVisibleToUser) {
                if (id > Constant.APPOINTMENT_ADD_INVALID_ID) {
                    AppointmentGetAsyncTask appointmentGetAsyncTask = new AppointmentGetAsyncTask(getContext());
                    appointmentGetAsyncTask.execute(id);

                    try {
                        mAppointment = appointmentGetAsyncTask.get();
                        Log.w(TAG, mAppointment.getDescription());
                        bindUI();
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                }
            }
        }
        Log.w(TAG, "Set User Visible Hint");

    }
}
