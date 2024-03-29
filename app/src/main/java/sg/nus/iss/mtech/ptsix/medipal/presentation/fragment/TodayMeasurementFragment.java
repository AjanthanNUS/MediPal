package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

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
import android.view.ViewGroup;
import android.widget.Button;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ReportContainerActivity;

public class TodayMeasurementFragment extends Fragment {
    private static final String TAG = TodayMeasurementFragment.class.getSimpleName();
    private Button btnSearch;

    public TodayMeasurementFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.today_measurement, container, false);
        btnSearch = (Button) rootView.findViewById(R.id.btn_Search);

        btnSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putLong("fromDate", CommonUtil.fromDate.getTime());
                bundle.putLong("toDate", CommonUtil.toDate.getTime());
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

        Log.w(TAG, "onCreate View of Today Measurement Fragment");
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser) {

        }
    }

}

