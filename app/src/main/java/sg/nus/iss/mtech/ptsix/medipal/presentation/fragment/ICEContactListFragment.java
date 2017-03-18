package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.IceDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.ICE;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ICEContactActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ICEContactAdapter;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ViewPagerAdapter;

/**
 * Created by JOHN on 3/12/2017.
 */

public class ICEContactListFragment extends Fragment {

    private List<ICE> iceList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ICEContactAdapter mAdapter;
    private IceDao iceDao;


    public ICEContactListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.iceDao = new IceDao(this.getContext());
        Log.i("Before ICE LIST", "ABX");
        this.getICEContactList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ice_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        Log.i("Fragment ICE LIST", String.valueOf(iceList.size()));
        mAdapter = new ICEContactAdapter(iceList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void getICEContactList() {
        iceList = this.iceDao.getICEs();
        Log.i(String.valueOf(iceList.size())," ICELIST");
    }

}
