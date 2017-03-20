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

import java.util.ArrayList;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.asynctask.MeasurementLoadAsyncTask;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Meas_BloodPressure;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.BloodPressureAdapter;

public class BloodPressureMeasurementListFragment extends Fragment {

    private List<Meas_BloodPressure> measurementList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BloodPressureAdapter mAdapter;
    private MeasurementLoadAsyncTask measurementLoadAsyncTask;

    public BloodPressureMeasurementListFragment() {
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

        mAdapter = new BloodPressureAdapter(getActivity(), measurementList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        measurementLoadAsyncTask = new MeasurementLoadAsyncTask(getActivity(),rootView);
        measurementLoadAsyncTask.execute(0);

        return rootView;
    }

}

