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
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.services.AppointmentService;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppointmentActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.AppointmentRecyclerViewAdapter;
import sg.nus.iss.mtech.ptsix.medipal.presentation.util.Constant;

/**
 * Created by win on 16/3/17.
 */

public class AppointmentListFragment extends Fragment {
    private List<Appointment> appointmentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AppointmentRecyclerViewAdapter mAdapter;
    private AppointmentService mAppointmentService;
    private FloatingActionButton addActionButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppointmentService = new AppointmentService(this.getContext());
        this.getAppointmentList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_appointment_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_app);

        mAdapter = new AppointmentRecyclerViewAdapter(appointmentList, getActivity());
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManger);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        addActionButton = (FloatingActionButton) rootView.findViewById(R.id.fragment_appointment_list_add);
        addActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppointmentActivity) getActivity()).switchTab(Constant.APPOINTMENT_TAB_ADD_INDEX, Constant.APPOINTMENT_ADD_INVALID_ID);
            }
        });
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser) {
            getAppointmentList();
            mAdapter.updateDataSet(appointmentList);
        }
    }

    private void getAppointmentList() {
        appointmentList = this.mAppointmentService.getAppointments();
    }
}
