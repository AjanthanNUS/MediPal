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
import sg.nus.iss.mtech.ptsix.medipal.business.services.ICEContactService;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.ICE;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ICEContactActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ICEContactAdapter;

public class ICEContactListFragment extends Fragment {

    private List<ICE> iceList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ICEContactAdapter mAdapter;
    private ICEContactService service;
    private FloatingActionButton addActionButton;

    public ICEContactListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.service = new ICEContactService(this.getContext());
        this.getICEContactList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ice_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mAdapter = new ICEContactAdapter(iceList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        addActionButton = (FloatingActionButton) rootView.findViewById(R.id.fragment_ice_add);
        addActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ICEContactActivity) getActivity()).switchTab(Constant.TAB_ADD_INDEX, Constant.INVALID_INDEX_ID);
            }
        });

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser) {
            getICEContactList();
            mAdapter.updateDataSet(iceList);
        }

    }

    private void getICEContactList() {
        iceList = this.service.getAllICEContact();
    }

}
