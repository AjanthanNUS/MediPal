package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.IceDao;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ViewPagerAdapter;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.ICEContactAddFragment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.ICEContactListFragment;

/**
 * Created by JOHN on 3/11/2017.
 */

public class ICEContactActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ICEContactAddFragment iceContactAddFragment;
    private ICEContactListFragment iceContactListFragment;
    private IceDao iceDAO;

    private int[] tabIcons = {
            R.drawable.ic_view_list_white,
            R.drawable.ic_edit_white
    };

    private static final String CATEGORIES_ADD_TAB_NAME = "Add/Edit";
    private static final String CATEGORIES_LIST_TAB_NAME = "Listing";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ice_contacts);

        iceDAO = new IceDao(this);

        iceContactAddFragment = new ICEContactAddFragment();
        iceContactListFragment = new ICEContactListFragment();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.ice_view_pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.ice_tabs);
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
        iceContactAddFragment.setArguments(bundle);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(iceContactListFragment, CATEGORIES_LIST_TAB_NAME);
        adapter.addFragment(iceContactAddFragment, CATEGORIES_ADD_TAB_NAME);
        viewPager.setAdapter(adapter);
    }

    public void switchTab(int index, int iceID) {
        iceContactAddFragment.getArguments().putInt("id", iceID);
        tabLayout.getTabAt(index).select();
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

}
