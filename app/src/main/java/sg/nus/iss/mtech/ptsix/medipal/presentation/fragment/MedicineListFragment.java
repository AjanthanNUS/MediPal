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
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.MedicineDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MedicineActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.MedicinesAdapter;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;

public class MedicineListFragment extends Fragment {

    private List<Medicine> medicinesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MedicinesAdapter mAdapter;
    private MedicineDao medicineDAO;
    private FloatingActionButton addActionButton;

    public MedicineListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.medicineDAO = new MedicineDao(this.getContext());
        this.getMedicinesList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_medicine_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mAdapter = new MedicinesAdapter(medicinesList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        addActionButton = (FloatingActionButton) rootView.findViewById(R.id.fragment_medicine_list_add);
        addActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MedicineActivity)getActivity()).switchTab(Constant.MEDICINE_TAB_ADD_INDEX, Constant.MEDICINE_ADD_INVALID_ID);
            }
        });
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser) {
            getMedicinesList();
            mAdapter.updateDataSet(medicinesList);
        }
    }

    private void getMedicinesList() {
        medicinesList = this.medicineDAO.getMedicines();
    }
}
