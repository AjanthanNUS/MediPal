package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.manager.ConsumptionManager;
import sg.nus.iss.mtech.ptsix.medipal.business.services.CategoriesService;
import sg.nus.iss.mtech.ptsix.medipal.business.services.MedicineService;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Consumption;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ConsumptionVO;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ConsumptionViewAdapter;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.ConsumptionListFragment;

public class ConsumptionActivity extends AppCompatActivity implements ConsumptionListFragment.OnListFragmentInteractionListener {
    private static final int START_DATE_DIALOG_ID = 1;
    private static final int END_DATE_DIALOG_ID = 2;
    private final String TAG = "[CONSUMPTION ACTIVITY]";
    private final String MEDICINE_TAG = "MED";
    private final String CATEGORY_TAG = "CAT";
    private final String START_DATE = "YEAR";
    private final String END_DATE = "MON";
    private ConsumptionListFragment consumptionListFragment;
    private ArrayList<ConsumptionVO> allConsumptionList;

    private CategoriesService categoriesService;
    private MedicineService medicineService;

    private LinearLayout filterLayout;
    private AutoCompleteTextView categoryFilterView;
    private AutoCompleteTextView medicineFilterView;
    private EditText filterStartDateView;
    private EditText filterEndDateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);

        filterLayout = (LinearLayout) findViewById(R.id.filter_bar);
        categoryFilterView = (AutoCompleteTextView) filterLayout.findViewById(R.id.filter_medicine);
        medicineFilterView = (AutoCompleteTextView) filterLayout.findViewById(R.id.filter_category);
        filterStartDateView = (EditText) filterLayout.findViewById(R.id.filter_start_date);
        filterEndDateView = (EditText) filterLayout.findViewById(R.id.filter_end_date);

        allConsumptionList = new ArrayList<>();
        ConsumptionManager consumptionManager = new ConsumptionManager(this);
        allConsumptionList.addAll(consumptionManager.getAllConsumptionVOList());
        consumptionManager.sortConsumptionList(allConsumptionList);

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
        filterLayout.setActivated(true);
        filterLayout.setVisibility(View.VISIBLE);

        filterStartDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(START_DATE_DIALOG_ID);
            }
        });

        filterEndDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(END_DATE_DIALOG_ID);
            }
        });

        Button goButton = (Button) filterLayout.findViewById(R.id.filter_go_btn);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterLayout.setVisibility(View.GONE);

                String searchMedicine = medicineFilterView.getText().toString();
                String searchCategory = categoryFilterView.getText().toString();
                String filterDateStart = filterStartDateView.getText().toString();
                String filterDateEnd = filterEndDateView.getText().toString();
                Bundle searchBundle = new Bundle();
                searchBundle.putString(MEDICINE_TAG, searchMedicine);
                searchBundle.putString(CATEGORY_TAG, searchCategory);
                searchBundle.putString(START_DATE, filterDateStart);
                searchBundle.putString(END_DATE, filterDateEnd);
                filterConsumptionHistory(searchBundle);
            }
        });

        Button resetButton = (Button) filterLayout.findViewById(R.id.filter_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterLayout.setVisibility(View.GONE);

                medicineFilterView.getText().clear();
                categoryFilterView.getText().clear();
                filterStartDateView.getText().clear();
                filterEndDateView.getText().clear();
                Bundle resetBundle = new Bundle();
                resetBundle.putString(MEDICINE_TAG, "");
                resetBundle.putString(CATEGORY_TAG, "");
                resetBundle.putString(START_DATE, "");
                resetBundle.putString(END_DATE, "");
                filterConsumptionHistory(resetBundle);
            }
        });
    }

    private void filterConsumptionHistory(Bundle searchBundle) {

        String searchMedicine = searchBundle.getString(MEDICINE_TAG);
        String searchCategory = searchBundle.getString(CATEGORY_TAG);
        String filterStartDate = searchBundle.getString(START_DATE);
        String filterEndDate = searchBundle.getString(END_DATE);

        ConsumptionViewAdapter viewAdapter = consumptionListFragment.getConsumptionViewAdapter();

        List<ConsumptionVO> allConsumptionList = new ArrayList<>();
        allConsumptionList.addAll(this.allConsumptionList);

        if (CommonUtil.isNullOrEmpty(searchMedicine) && CommonUtil.isNullOrEmpty(searchCategory) &&
                CommonUtil.isNullOrEmpty(filterStartDate) && CommonUtil.isNullOrEmpty(filterEndDate)) {

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
        boolean valid = false;
        try {
            Date consumedDate = c.getConsumedOn();
            Date filterStartDate;
            Date filterEndDate;

            String startDate = searchBundle.getString(START_DATE);

            if (CommonUtil.isNullOrEmpty(startDate)) {
                Calendar epochCal = Calendar.getInstance();
                epochCal.setTimeInMillis(0);
                filterStartDate = epochCal.getTime();
            } else {
                filterStartDate = CommonUtil.ddmmmyyyy2date(startDate);
            }

            String endDate = searchBundle.getString(END_DATE);
            if (CommonUtil.isNullOrEmpty(endDate)) {
                Calendar nowCal = Calendar.getInstance();
                filterEndDate = nowCal.getTime();
            } else {
                filterEndDate = CommonUtil.ddmmmyyyy2date(endDate);
            }


            if (filterStartDate.before(consumedDate) && filterEndDate.after(consumedDate)) {
                valid = true;
            }
        } catch (ParseException e) {
            Log.e(TAG, "Date format error in parsing string to Date.");
        }
        return valid;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case START_DATE_DIALOG_ID: {
                Consumption consumption = allConsumptionList.get(allConsumptionList.size() - 1);
                Calendar consumedOn = CommonUtil.dateToCalendar(consumption.getConsumedOn());
                return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar startDate = Calendar.getInstance();
                        startDate.set(Calendar.YEAR, year);
                        startDate.set(Calendar.MONTH, month);
                        startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        filterStartDateView.setText(CommonUtil.date2ddMMMYYYY(startDate.getTime()));
                    }
                }, consumedOn.get(Calendar.YEAR), consumedOn.get(Calendar.MONTH), consumedOn.get(Calendar.DAY_OF_MONTH));
            }
            case END_DATE_DIALOG_ID: {

                Consumption consumption = allConsumptionList.get(0);
                Calendar consumedOn = CommonUtil.dateToCalendar(consumption.getConsumedOn());
                return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar startDate = Calendar.getInstance();
                        startDate.set(Calendar.YEAR, year);
                        startDate.set(Calendar.MONTH, month);
                        startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        filterEndDateView.setText(CommonUtil.date2ddMMMYYYY(startDate.getTime()));
                    }
                }, consumedOn.get(Calendar.YEAR), consumedOn.get(Calendar.MONTH), consumedOn.get(Calendar.DAY_OF_MONTH));
            }
        }
        return null;
    }

    @Override
    public void onListFragmentInteraction(ConsumptionVO consumption) {
    }
}
