package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.HealthBioDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.HealthBio;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ViewPagerAdapter;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.HealthBioAddFragment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.HealthBioListFragment;

/**
 * Created by JOHN on 3/12/2017.
 */

public class HealthBioActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HealthBioAddFragment healthBioAddFragment;
    private HealthBioListFragment healthBioListFragment;
    private HealthBioDao healthBioDao;


    private int[] tabIcons = {
            R.drawable.ic_view_list_white,
            R.drawable.ic_edit_white
    };

    private static final String HEALTH_BIO_ADD_TAB_NAME = "Add/Edit";
    private static final String HEALTH_BIO_LIST_TAB_NAME = "Listing";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_bio);

        healthBioDao = new HealthBioDao(this);

        healthBioAddFragment = new HealthBioAddFragment();
        healthBioListFragment = new HealthBioListFragment();

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.health_bio_view_pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.health_bio_tabs);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//               Log.d("aaaaaaaaa", "aaaaaaaaaaaaa");
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                // called when tab unselected
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                // called when a tab is reselected
//            }
//        });
        setupTabIcons();

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(ViewPager viewPager) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", -1);
        healthBioAddFragment.setArguments(bundle);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(healthBioListFragment, HEALTH_BIO_LIST_TAB_NAME);
        adapter.addFragment(healthBioAddFragment, HEALTH_BIO_ADD_TAB_NAME);
        viewPager.setAdapter(adapter);
    }

    public void switchTab(int index, int healthBioId) {
        healthBioAddFragment.getArguments().putInt("id", healthBioId);
        tabLayout.getTabAt(index).select();
    }

}
