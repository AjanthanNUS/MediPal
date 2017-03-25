package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.CategoriesDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.MedicineDao;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ViewPagerAdapter;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.MedicineAddFragment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.MedicineListFragment;


public class MedicineActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MedicineListFragment medicineListFragment;
    private MedicineAddFragment medicineAddFragment;
    private CategoriesDao categoriesDao;
    private MedicineDao medicineDao;

    private int[] tabIcons = {
            R.drawable.ic_view_list_white,
            R.drawable.ic_edit_white
    };

    private static final String MEDICINE_ADD_TAB_NAME = "Add/Edit";
    private static final String MEDICINE_LIST_TAB_NAME = "Listing";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        this.categoriesDao = new CategoriesDao(this);

        this.medicineListFragment = new MedicineListFragment();
        this.medicineAddFragment = new MedicineAddFragment();

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
        this.medicineAddFragment.setArguments(bundle);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(medicineListFragment, MEDICINE_LIST_TAB_NAME);
        adapter.addFragment(medicineAddFragment, MEDICINE_ADD_TAB_NAME);
        viewPager.setAdapter(adapter);
    }

    public void switchTab(int index, int medicineId) {
        medicineAddFragment.getArguments().putInt("id", medicineId);
        tabLayout.getTabAt(index).select();
    }
}