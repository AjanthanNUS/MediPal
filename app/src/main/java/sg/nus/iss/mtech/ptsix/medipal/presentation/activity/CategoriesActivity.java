package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ViewPagerAdapter;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.CategoriesAddFragment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.CategoriesListFragment;

public class CategoriesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CategoriesListFragment categoriesListFragment;
    private CategoriesAddFragment categoriesAddFragment;

    private int[] tabIcons = {
            android.R.drawable.picture_frame,
            android.R.drawable.ic_menu_add
    };

    private static final String CATEGORIES_ADD_TAB_NAME = "Add/Edit";
    private static final String CATEGORIES_LIST_TAB_NAME = "Listing";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        categoriesListFragment = new CategoriesListFragment();
        categoriesAddFragment = new CategoriesAddFragment();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
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
        categoriesAddFragment.setArguments(bundle);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(categoriesListFragment, CATEGORIES_LIST_TAB_NAME);
        adapter.addFragment(categoriesAddFragment, CATEGORIES_ADD_TAB_NAME);
        viewPager.setAdapter(adapter);
    }

    public void switchTab(int index, int catId) {
        categoriesAddFragment.getArguments().putInt("id", catId);
        tabLayout.getTabAt(index).select();
    }
}
