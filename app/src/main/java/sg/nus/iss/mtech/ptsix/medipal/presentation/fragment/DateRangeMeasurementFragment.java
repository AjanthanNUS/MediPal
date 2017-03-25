package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.services.MeasurementService;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ReportContainerActivity;

public class DateRangeMeasurementFragment extends Fragment {
    private static final String TAG = DateRangeMeasurementFragment.class.getSimpleName();
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constant.DATE_FORMAT, Locale.getDefault());
    Calendar currentCal = Calendar.getInstance();


    private Date fromDate = new Date();
    private Date toDate = new Date();

    private Button btnSearch;
    private EditText etFromDate;
    private EditText etToDate;

    private MeasurementService measurementService;

    public DateRangeMeasurementFragment() {
        measurementService = new MeasurementService(getContext());
        fromDate = CommonUtil.fromDate;
        toDate = CommonUtil.toDate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.date_range_measurement, container, false);
        btnSearch = (Button) rootView.findViewById(R.id.btn_Search);
        etFromDate = (EditText) rootView.findViewById(R.id.et_from_date);
        etToDate = (EditText) rootView.findViewById(R.id.et_to_date);

        fromDate = CommonUtil.fromDate;
        toDate = CommonUtil.toDate;

        btnSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getArguments();
                if (bundle != null) {
                    //Calling Report Activity
                }
            }
        });
        Log.w(TAG, "onCreate View of Today Measurement Fragment");

        this.etFromDate.setText(dateFormatter.format(fromDate .getTime()));
        this.etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                fromDate = calendar.getTime();
                                etFromDate.setText(dateFormatter.format(calendar.getTime()));
                            }
                        };
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(getActivity(), onDateSetListener,
                                currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
                                currentCal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        this.etFromDate.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DatePickerDialog.OnDateSetListener onDateSetListener =
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(year, monthOfYear, dayOfMonth);
                                    fromDate = calendar.getTime();
                                    etFromDate.setText(dateFormatter.format(calendar.getTime()));
                                }
                            };
                    DatePickerDialog datePickerDialog =
                            new DatePickerDialog(getActivity(), onDateSetListener,
                                    currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
                                    currentCal.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                }
            }
        });


        this.etToDate.setText(dateFormatter.format(toDate .getTime()));
        this.etToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                toDate = calendar.getTime();
                                etToDate.setText(dateFormatter.format(calendar.getTime()));
                            }
                        };
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(getActivity(), onDateSetListener,
                                currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
                                currentCal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        this.etToDate.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DatePickerDialog.OnDateSetListener onDateSetListener =
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(year, monthOfYear, dayOfMonth);
                                    toDate = calendar.getTime();
                                    etToDate.setText(dateFormatter.format(calendar.getTime()));

                                }
                            };
                    DatePickerDialog datePickerDialog =
                            new DatePickerDialog(getActivity(), onDateSetListener,
                                    currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
                                    currentCal.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                }
            }
        });

        this.btnSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            Bundle bundle = new Bundle();

            bundle.putLong("fromDate", fromDate.getTime());
            bundle.putLong("toDate", toDate.getTime());
            bundle.putInt("reportType", CommonUtil.reportType);

            if (CommonUtil.reportType != 0) {
                if (bundle != null) {
                    Log.w(TAG, "Not Null");
                    Intent myIntent = new Intent(getActivity(), ReportContainerActivity.class);
                    myIntent.putExtras(bundle);
                    startActivity(myIntent);
                } else {
                    Log.w(TAG, "Null");
                }
            } else {
                new AlertDialog.Builder(getContext())
                        .setTitle("Report")
                        .setMessage("Please select the report type.")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })
                        .show();

                }
            }

        });


        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser) {

        }
    }

}

