package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.services.ICEContactService;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ViewPagerAdapter;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.ICEContactAddFragment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.ICEContactListFragment;

public class ICEContactActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ICEContactAddFragment iceContactAddFragment;
    private ICEContactListFragment iceContactListFragment;
    private ICEContactService iceContactService;

    private int[] tabIcons = {
            R.drawable.ic_view_list_white,
            R.drawable.ic_edit_white
    };

    private static final String ICE_ADD_TAB_NAME = "Add/Edit";
    private static final String ICE_LIST_TAB_NAME = "Listing";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ice_contacts);

        iceContactService = new ICEContactService(this);

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
        adapter.addFragment(iceContactListFragment, ICE_LIST_TAB_NAME);
        adapter.addFragment(iceContactAddFragment, ICE_ADD_TAB_NAME);
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
