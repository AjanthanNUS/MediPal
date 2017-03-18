package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.HealthBioDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.HealthBio;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.HealthBioActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.HealthBioAdaptor;

/**
 * Created by JOHN on 3/12/2017.
 */

public class HealthBioListFragment extends android.support.v4.app.Fragment {

    private List<HealthBio> healthBioList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HealthBioAdaptor mAdapter;
    private HealthBioDao healthBioDao;

    public HealthBioListFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.healthBioDao = new HealthBioDao(this.getContext());
        this.getHealthBioList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_health_bio_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.health_bio_recycler_view);

        mAdapter = new HealthBioAdaptor(healthBioList, getActivity());
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

    private void getHealthBioList() {
        healthBioList = this.healthBioDao.getHealthBios();
    }

}
