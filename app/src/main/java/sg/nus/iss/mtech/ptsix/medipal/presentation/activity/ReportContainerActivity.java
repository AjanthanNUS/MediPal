package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.services.MeasurementService;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Measurement;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.BloodPressureAdapter;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.WeightAdapter;

public class ReportContainerActivity extends AppCompatActivity {
    private List<Measurement> measurementList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BloodPressureAdapter mAdapter;
    private WeightAdapter mWeightAdapter;
    private MeasurementService measurementService;
    private Date fromDate = new Date();
    private Date toDate = new Date();
    private int reportType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_container);
        measurementService = new MeasurementService(getApplicationContext());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            fromDate = new Date(bundle.getLong("fromDate"));
            toDate = new Date(bundle.getLong("toDate"));
            reportType = bundle.getInt("reportType");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_report_container);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if(reportType == 1) {
            mAdapter = new BloodPressureAdapter(this, measurementList);
            refreshBloodPressures();
            recyclerView.setAdapter(mAdapter);
        } else if (reportType == 2) {
            mWeightAdapter = new WeightAdapter(this, measurementList);
            refreshWeight();
            recyclerView.setAdapter(mWeightAdapter);
        }


    }

    public void refreshBloodPressures() {
        measurementList = measurementService.getBloodPressures(fromDate, toDate);
        mAdapter.updateDataSet(measurementList);
    }

    public void refreshWeight() {
        measurementList = measurementService.getWeights(fromDate, toDate);
        mWeightAdapter.updateDataSet(measurementList);
    }


}
