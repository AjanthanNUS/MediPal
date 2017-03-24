package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.services.MeasurementService;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Measurement;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.BloodPressureAdapter;

public class BloodPressureMeasurementListFragment extends Fragment implements SearchView.OnQueryTextListener{

    private List<Measurement> measurementList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BloodPressureAdapter mAdapter;

    private MeasurementService measurementService;
    public static Date fromDate;
    public static Date toDate;
    public static int reportType;

    public BloodPressureMeasurementListFragment() {
        measurementService = new MeasurementService(getContext());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_bloodpressure_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_blood_pressure);

        fromDate = CommonUtil.fromDate;
        toDate = CommonUtil.toDate;

        refreshBloodPressures();
        mAdapter = new BloodPressureAdapter(getActivity(), measurementList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public void refreshBloodPressures() {
        measurementList = measurementService.getBloodPressures(fromDate, toDate);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser) {
            refreshBloodPressures();
            mAdapter.updateDataSet(measurementList);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}

