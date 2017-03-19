package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.CategoriesDao;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ViewPagerAdapter;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.CategoriesAddFragment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.CategoriesListFragment;

public class CategoriesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CategoriesListFragment categoriesListFragment;
    private CategoriesAddFragment categoriesAddFragment;
    private CategoriesDao categoriesDao;

    private int[] tabIcons = {
            R.drawable.ic_view_list_white,
            R.drawable.ic_edit_white
    };

    private static final String CATEGORIES_ADD_TAB_NAME = "Add/Edit";
    private static final String CATEGORIES_LIST_TAB_NAME = "Listing";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        this.categoriesDao = new CategoriesDao(this);

        this.categoriesListFragment = new CategoriesListFragment();
        this.categoriesAddFragment = new CategoriesAddFragment();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.viewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        this.tabLayout = (TabLayout) findViewById(R.id.tabs);
        this.tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        this.tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        this.tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(ViewPager viewPager) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", -1);
        this.categoriesAddFragment.setArguments(bundle);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(categoriesListFragment, CATEGORIES_LIST_TAB_NAME);
        adapter.addFragment(categoriesAddFragment, CATEGORIES_ADD_TAB_NAME);
        viewPager.setAdapter(adapter);
    }

    public void switchTab(int index, int categoriesId) {
        categoriesAddFragment.getArguments().putInt("id", categoriesId);
        tabLayout.getTabAt(index).select();
    }
}
