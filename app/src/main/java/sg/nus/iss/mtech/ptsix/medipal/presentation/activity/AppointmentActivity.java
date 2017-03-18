package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ViewPagerAdapter;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.AppointmentAddFragment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.AppointmentListFragment;

public class AppointmentActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AppointmentListFragment appointmentListFragment;
    private AppointmentAddFragment appointmentAddFragment;

    private int[] tabIcons = {
            R.drawable.ic_view_list_white,
            R.drawable.ic_edit_white
    };

    private static final String APPOINTMENT_ADD_TAB_NAME = "Add/Edit";
    private static final String APPOINTMENT_LIST_TAB_NAME = "Listing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        appointmentListFragment = new AppointmentListFragment();
        appointmentAddFragment = new AppointmentAddFragment();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.view_pager_appointment);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs_appointment);
        tabLayout.setupWithViewPager(viewPager);


        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(ViewPager viewPager) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", -1);
        appointmentAddFragment.setArguments(bundle);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(appointmentListFragment, APPOINTMENT_LIST_TAB_NAME);
        adapter.addFragment(appointmentAddFragment, APPOINTMENT_ADD_TAB_NAME);
        viewPager.setAdapter(adapter);
    }

    public void switchTab(int index, int appointmentId) {
        appointmentAddFragment.getArguments().putInt("id", appointmentId);
        tabLayout.getTabAt(index).select();
    }


}


