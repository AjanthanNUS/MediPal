package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
import sg.nus.iss.mtech.ptsix.medipal.business.services.AppointmentAlarmReceiver;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;

public class AppointmentDetailActivity extends AppCompatActivity {
    private EditText etLocation, etDescription, etDate, etTime = null;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());
    private SimpleDateFormat dbDateFormatter = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
    private Button btnAdd, btnCancel = null;
    Calendar currentCal = Calendar.getInstance();
    Calendar selectedDate = Calendar.getInstance();
    private Appointment mAppointment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);
        initializeView();

        // The detail Activity called via intent.  Inspect the intent for forecast data.
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("appointment")) {
            mAppointment = intent.getParcelableExtra("appointment");
            bindUI();
        }

    }

    public void bindUI() {
        etLocation.setText(mAppointment.getLocation());
        etDescription.setText(mAppointment.getDescription());
        etDate.setText(dateFormatter.format(mAppointment.getAppointmentDate()));
        etTime.setText(timeFormatter.format(mAppointment.getAppointmentDate()));
    }

    private void initializeView() {
        etLocation = (EditText) findViewById(R.id.etLocation);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etDate = (EditText) findViewById(R.id.etDate);
        etTime = (EditText) findViewById(R.id.etTime);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnCancel = (Button) findViewById(R.id.btnCancel);

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
                        new DatePickerDialog(AppointmentDetailActivity.this, onDateSetListener,
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
                    Toast.makeText(AppointmentDetailActivity.this, R.string.generic_error, Toast.LENGTH_SHORT)
                            .show();
                }
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(AppointmentDetailActivity.this, timeSetListener,
                                timeCalendar.get(Calendar.HOUR_OF_DAY), timeCalendar.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        };
        etTime.setOnClickListener(timeClickListener);

        btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointmentAlarmReceiver alarmReceiver = new AppointmentAlarmReceiver();
                Appointment appointment = prepareAppointment();

                AppointmentAsyncTask appointmentAsyncTask = new AppointmentAsyncTask(getApplicationContext());
                appointmentAsyncTask.execute(appointment);
                Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAll();
            }
        });
    }

    private void resetAll() {
        mAppointment = new Appointment();
        bindUI();

    }

    private boolean isValid() {
        return true;
    }

    private Appointment prepareAppointment() {
        mAppointment = new Appointment();
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
}
