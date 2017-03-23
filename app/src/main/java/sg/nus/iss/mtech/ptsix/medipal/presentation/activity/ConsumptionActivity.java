package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.manager.ConsumptionManager;
import sg.nus.iss.mtech.ptsix.medipal.business.services.CategoriesService;
import sg.nus.iss.mtech.ptsix.medipal.business.services.MedicineService;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ConsumptionVO;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ConsumptionViewAdapter;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.ConsumptionListFragment;

public class ConsumptionActivity extends AppCompatActivity implements ConsumptionListFragment.OnListFragmentInteractionListener {
    private final String TAG = "[CONSUMPTION ACTIVITY]";
    private final String MEDICINE_TAG = "MED";
    private final String CATEGORY_TAG = "CAT";
    private final String YEAR_TAG = "YEAR";
    private final String MONTH_TAG = "MON";
    private final String WEEK_TAG = "WEEK";
    private final String DAY_TAG = "DAY";
    private ConsumptionListFragment consumptionListFragment;
    private ArrayList<ConsumptionVO> allConsumptionList;


    private CategoriesService categoriesService;

    private MedicineService medicineService;

    private AutoCompleteTextView categoryFilterView;
    private AutoCompleteTextView medicineFilterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);


        allConsumptionList = new ArrayList<>();
        ConsumptionManager consumptionManager = new ConsumptionManager(this);
        allConsumptionList.addAll(consumptionManager.getAllConsumptionVOList());


        consumptionListFragment = ConsumptionListFragment.newInstance();
        ArrayList<ConsumptionVO> initConList = new ArrayList<>();
        initConList.addAll(allConsumptionList);
        consumptionListFragment.setConsumptionList(initConList);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.consumptionFragmentHolder, consumptionListFragment);
        fragmentTransaction.commit();

        setUpCategoryFilterAutocomplete();
        setUpMedicineFilterAutocomplete();
    }

    private void setUpCategoryFilterAutocomplete() {
        List<String> categoryCodeList;
        List<Categories> categories;
        categoriesService = new CategoriesService(this);
        categories = new ArrayList<>();
        categories.addAll(categoriesService.getCategories());
        categoryCodeList = new ArrayList<>();
        for (Categories category : categories) {
            categoryCodeList.add(category.getCode());
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categoryCodeList);
        categoryFilterView = (AutoCompleteTextView) findViewById(R.id.filter_category);
        categoryFilterView.setAdapter(categoryAdapter);
        categoryFilterView.setThreshold(1);
    }

    private void setUpMedicineFilterAutocomplete() {
        List<String> medicineTitleList;
        List<Medicine> medicines;
        medicineService = new MedicineService(this);
        medicines = new ArrayList<>();
        medicines.addAll(medicineService.getMedicine());
        medicineTitleList = new ArrayList<>();
        for (Medicine medicine : medicines) {
            medicineTitleList.add(medicine.getMedicine());
        }
        ArrayAdapter<String> medicineAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, medicineTitleList);
        medicineFilterView = (AutoCompleteTextView) findViewById(R.id.filter_medicine);
        medicineFilterView.setAdapter(medicineAdapter);
        medicineFilterView.setThreshold(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.consumption_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_filter:
                showHistoryFilterPopUp();
        }
        return true;
    }


    private void showHistoryFilterPopUp() {
        final LinearLayout filterLayout = (LinearLayout) findViewById(R.id.filter_bar);
        filterLayout.setActivated(true);
        filterLayout.setVisibility(View.VISIBLE);

        Button goButton = (Button) filterLayout.findViewById(R.id.filter_go_btn);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterLayout.setVisibility(View.GONE);

                AutoCompleteTextView medicineET = (AutoCompleteTextView) filterLayout.findViewById(R.id.filter_medicine);
                AutoCompleteTextView categoryET = (AutoCompleteTextView) filterLayout.findViewById(R.id.filter_category);
                EditText fromYearsET = (EditText) filterLayout.findViewById(R.id.filter_year);
                EditText fromMonthsET = (EditText) filterLayout.findViewById(R.id.filter_month);
                EditText fromWeeksET = (EditText) filterLayout.findViewById(R.id.filter_week);
                EditText fromDaysET = (EditText) filterLayout.findViewById(R.id.filter_day);

                String searchMedicine = medicineET.getText().toString();
                String searchCategory = categoryET.getText().toString();
                String searchYear = fromYearsET.getText().toString();
                String searchMonth = fromMonthsET.getText().toString();
                String searchWeek = fromWeeksET.getText().toString();
                String searchDays = fromDaysET.getText().toString();
                Bundle searchBundle = new Bundle();
                searchBundle.putString(MEDICINE_TAG, searchMedicine);
                searchBundle.putString(CATEGORY_TAG, searchCategory);
                searchBundle.putString(YEAR_TAG, searchYear);
                searchBundle.putString(MONTH_TAG, searchMonth);
                searchBundle.putString(WEEK_TAG, searchWeek);
                searchBundle.putString(DAY_TAG, searchDays);
                filterConsumptionHistory(searchBundle);
            }
        });

        Button resetButton = (Button) filterLayout.findViewById(R.id.filter_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterLayout.setVisibility(View.GONE);
                EditText medicineET = (EditText) filterLayout.findViewById(R.id.filter_medicine);
                EditText categoryET = (EditText) filterLayout.findViewById(R.id.filter_category);
                EditText fromYearsET = (EditText) filterLayout.findViewById(R.id.filter_year);
                EditText fromMonthsET = (EditText) filterLayout.findViewById(R.id.filter_month);
                EditText fromWeeksET = (EditText) filterLayout.findViewById(R.id.filter_week);
                EditText fromDaysET = (EditText) filterLayout.findViewById(R.id.filter_day);
                medicineET.getText().clear();
                categoryET.getText().clear();
                fromYearsET.getText().clear();
                fromMonthsET.getText().clear();
                fromWeeksET.getText().clear();
                fromDaysET.getText().clear();
                Bundle resetBundle = new Bundle();
                resetBundle.putString(MEDICINE_TAG, "");
                resetBundle.putString(CATEGORY_TAG, "");
                resetBundle.putString(YEAR_TAG, "");
                resetBundle.putString(MONTH_TAG, "");
                resetBundle.putString(WEEK_TAG, "");
                resetBundle.putString(DAY_TAG, "");
                filterConsumptionHistory(resetBundle);
            }
        });
    }

    private void filterConsumptionHistory(Bundle searchBundle) {

        String searchMedicine = searchBundle.getString(MEDICINE_TAG);
        String searchCategory = searchBundle.getString(CATEGORY_TAG);
        String searchYear = searchBundle.getString(YEAR_TAG);
        String searchMonth = searchBundle.getString(MONTH_TAG);
        String searchWeek = searchBundle.getString(WEEK_TAG);
        String searchDays = searchBundle.getString(DAY_TAG);

        ConsumptionViewAdapter viewAdapter = consumptionListFragment.getConsumptionViewAdapter();

        List<ConsumptionVO> allConsumptionList = new ArrayList<>();
        allConsumptionList.addAll(this.allConsumptionList);

        if (CommonUtil.isNullOrEmpty(searchMedicine) && CommonUtil.isNullOrEmpty(searchCategory) &&
                CommonUtil.isNullOrEmpty(searchYear) && CommonUtil.isNullOrEmpty(searchMonth) &&
                CommonUtil.isNullOrEmpty(searchWeek) && CommonUtil.isNullOrEmpty(searchDays)) {

            allConsumptionList.clear();
            allConsumptionList.addAll(this.allConsumptionList);

            viewAdapter.getmConsumptionList().clear();
            viewAdapter.getmConsumptionList().addAll(allConsumptionList);
            viewAdapter.notifyDataSetChanged();
        } else {
            List<ConsumptionVO> filterList = new ArrayList<>();

            for (ConsumptionVO vo : allConsumptionList) {
                if (CommonUtil.isNullOrEmpty(searchMedicine) || (vo.getMedicine() != null && vo.getMedicine().getMedicine().contains(searchMedicine))) {
                    if (CommonUtil.isNullOrEmpty(searchCategory) || (vo.getCategories() != null && vo.getCategories().getCategory().contains(searchCategory))) {
                        if (isValidForFilter(vo, searchBundle))
                            filterList.add(vo);
                    }
                }
            }
            viewAdapter.getmConsumptionList().clear();
            viewAdapter.getmConsumptionList().addAll(filterList);
            viewAdapter.notifyDataSetChanged();
        }
    }

    private boolean isValidForFilter(ConsumptionVO c, Bundle searchBundle) {
        boolean valid = true;
        String searchYear = searchBundle.getString(YEAR_TAG);
        String searchMonth = searchBundle.getString(MONTH_TAG);
        String searchWeek = searchBundle.getString(WEEK_TAG);
        String searchDays = searchBundle.getString(DAY_TAG);
        long consumedAgo = Calendar.getInstance().getTimeInMillis() - c.getConsumedOn().getTime();


        if (!CommonUtil.isNullOrEmpty(searchDays) && consumedAgo > CommonUtil.getMilliSeconds(0, 0, Integer.valueOf(searchDays))) {
            valid = false;
        } else {
            searchDays = "0";
        }
        if (valid && !CommonUtil.isNullOrEmpty(searchWeek) && consumedAgo > CommonUtil.getMilliSeconds(0, 0, Integer.valueOf(searchDays) + Integer.valueOf(searchWeek) * 7)) {
            valid = false;
        } else {
            searchWeek = "0";
        }

        if (valid && !CommonUtil.isNullOrEmpty(searchMonth) && consumedAgo > CommonUtil.getMilliSeconds(0, Integer.valueOf(searchMonth), Integer.valueOf(searchDays) + Integer.valueOf(searchWeek) * 7)) {
            valid = false;
        } else {
            searchMonth = "0";
        }
        if (valid && !CommonUtil.isNullOrEmpty(searchYear) && consumedAgo > CommonUtil.getMilliSeconds(Integer.valueOf(searchYear), Integer.valueOf(searchMonth), Integer.valueOf(searchDays) + Integer.valueOf(searchWeek) * 7)) {
            valid = false;
        }
        return valid;
    }

//    public void initiateSpinnerCategory() {
//
//        spinnerCategory = (Spinner) findViewById(R.id.spinner_category);
//        List<String> categoryCodeList = new ArrayList<>();
//        categoryCodeList.add(getResources().getString(R.string.select_category));
//        for (Categories category : categories) {
//            categoryCodeList.add(category.getCode());
//        }
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item, categoryCodeList);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCategory.setAdapter(dataAdapter);
//
//        addListenerOnSpinnerItemSelection();
//    }
//
//    public void addListenerOnSpinnerItemSelection() {
//        spinnerCategory = (Spinner) findViewById(R.id.spinner_category);
//        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//
//                Toast.makeText(parent.getContext(),
//                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
//                        Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//}


    @Override
    public void onListFragmentInteraction(ConsumptionVO consumption) {
    }
}
