package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ViewPagerAdapter;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.BloodPressureMeasurementListFragment;

public class MeasurementReportActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BloodPressureMeasurementListFragment bloodPressureMeasurementListFragment;

    private int[] tabIcons = {
            R.drawable.ic_view_list_white,
            R.drawable.ic_measurement
    };

    private static final String BLOOD_PRESSURE_TAB_NAME = "Blood Pressure";
    private static final String CHOLESTEROL_TAB_NAME = "Chelostrol Level";
    private static final String WEIGHT_TAB_NAME = "Weight";
    private static final String GLUECOSE_TAB_NAME = "Glucose Level";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_report);

        bloodPressureMeasurementListFragment = new BloodPressureMeasurementListFragment();
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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(bloodPressureMeasurementListFragment, BLOOD_PRESSURE_TAB_NAME);
        viewPager.setAdapter(adapter);
    }

    public void switchTab(int index) {
        tabLayout.getTabAt(index).select();
    }
}
