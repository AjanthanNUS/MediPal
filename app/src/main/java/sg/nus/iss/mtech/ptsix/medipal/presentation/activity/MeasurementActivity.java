package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.MeasurementDao;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ViewPagerAdapter;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.MeasurementAddFragment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.MeasurementListFragment;

public class MeasurementActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MeasurementListFragment measurementListFragment;
    private MeasurementAddFragment measurementAddFragment;
    private MeasurementDao measurementDao;

    private int[] tabIcons = {
            R.drawable.ic_view_list_white,
            R.drawable.ic_edit_white
    };

    private static final String MEASUREMENT_ADD_TAB_NAME = "Add";
    private static final String MEASUREMENT_LIST_TAB_NAME = "Listing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);

        measurementListFragment = new MeasurementListFragment();
        measurementAddFragment = new MeasurementAddFragment();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
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
        measurementAddFragment.setArguments(bundle);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(measurementListFragment, MEASUREMENT_LIST_TAB_NAME);
        adapter.addFragment(measurementAddFragment, MEASUREMENT_ADD_TAB_NAME);
        viewPager.setAdapter(adapter);
    }

    public void switchTab(int index, int catId) {
        measurementAddFragment.getArguments().putInt("id", catId);
        tabLayout.getTabAt(index).select();
    }
}
