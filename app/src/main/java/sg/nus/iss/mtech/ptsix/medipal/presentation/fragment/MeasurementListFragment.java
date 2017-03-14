package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.MeasurementDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Measurement;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MeasurementActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.MeasurementAdapter;
public class MeasurementListFragment extends Fragment {

    private List<Measurement> measurementList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MeasurementAdapter mAdapter;
    private MeasurementDao measurementDao;
    private FloatingActionButton addActionButton;

    public MeasurementListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.measurementDao = new MeasurementDao(this.getContext());
        this.getCategoriesList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_measurement_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mAdapter = new MeasurementAdapter(measurementList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        addActionButton = (FloatingActionButton) rootView.findViewById(R.id.fragment_measurement_list_add);
        addActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MeasurementActivity) getActivity()).switchTab(1, -1);
            }
        });
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        categoriesList = this.categoriesDao.getCategories();
////        if (isVisibleToUser) {
////            getCategoriesList();
////        }
    }

    private void getCategoriesList() {
        measurementList = this.measurementDao.getMeasurements();
    }

    private Boolean checkValidCategory() {


        return false;
    }
}

