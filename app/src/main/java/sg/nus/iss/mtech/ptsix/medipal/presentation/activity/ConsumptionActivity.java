package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.manager.ConsumptionManager;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ConsumptionVO;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ConsumptionViewAdapter;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.ConsumptionListFragment;

public class ConsumptionActivity extends AppCompatActivity implements ConsumptionListFragment.OnListFragmentInteractionListener {
    private final int DATE_DIALOG_ID = 999;


    private ConsumptionListFragment consumptionListFragment;


    private final String MEDICINE_TAG = "MED";
    private final String CATEGORY_TAG = "CAT";
    private final String YEAR_TAG = "YEAR";
    private final String MONTH_TAG = "MON";
    private final String WEEK_TAG = "WEEK";
    private final String DAY_TAG = "DAY";
    private ArrayList<ConsumptionVO> fullConsumptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);

        fullConsumptionList = new ArrayList<>();
        ConsumptionManager consumptionManager = new ConsumptionManager();
        fullConsumptionList.addAll(consumptionManager.getAllConsumptionList());


        consumptionListFragment = ConsumptionListFragment.newInstance();
        ArrayList<ConsumptionVO> initConList = new ArrayList<>();
        initConList.addAll(fullConsumptionList);
        consumptionListFragment.setConsumptionList(initConList);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.consumptionFragmentHolder, consumptionListFragment);
        fragmentTransaction.commit();
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

                EditText medicineET = (EditText) filterLayout.findViewById(R.id.filter_medicine);
                EditText categoryET = (EditText) filterLayout.findViewById(R.id.filter_category);
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
        allConsumptionList.addAll(fullConsumptionList);

        if (CommonUtil.isNullOrEmpty(searchMedicine) && CommonUtil.isNullOrEmpty(searchCategory) &&
                CommonUtil.isNullOrEmpty(searchYear) && CommonUtil.isNullOrEmpty(searchMonth) &&
                CommonUtil.isNullOrEmpty(searchWeek) && CommonUtil.isNullOrEmpty(searchDays)) {

            allConsumptionList = new ArrayList<>();
            ConsumptionManager consumptionManager = new ConsumptionManager();
            allConsumptionList.addAll(consumptionManager.getAllConsumptionList());
            viewAdapter.getmConsumptionList().clear();
            viewAdapter.getmConsumptionList().addAll(allConsumptionList);
            viewAdapter.notifyDataSetChanged();
        } else {
            List<ConsumptionVO> filterList = new ArrayList<>();

            for (ConsumptionVO vo : allConsumptionList) {
                if (CommonUtil.isNullOrEmpty(searchMedicine) || (vo.getMedicine() != null && vo.getMedicine().getEventMedicine().contains(searchMedicine))) {
                    if (CommonUtil.isNullOrEmpty(searchCategory) || (vo.getCategories() != null && vo.getCategories().getEventCategory().contains(searchCategory))) {
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
        long consumedAgo = Calendar.getInstance().getTimeInMillis() - c.getEventConsumedOn().getTime();


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

    @Override
    public void onListFragmentInteraction(ConsumptionVO consumption) {
//        consumptionFragmentHolder = (FrameLayout) findViewById(R.id.consumptionFragmentHolder);
//        if (mPopupWindow != null) {
//            mPopupWindow.dismiss();
//            mPopupWindow = null;
//        } else {
//            LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
//            View customView = inflater.inflate(R.layout.consumption_filter_popup, null);
//
//            TextView medicine = (TextView) customView.findViewById(R.id.popup_medicine);
//            TextView consumedOn = (TextView) customView.findViewById(R.id.popup_consumed_on);
//            TextView quantity = (TextView) customView.findViewById(R.id.popup_quantity);
//            TextView description = (TextView) customView.findViewById(R.id.popup_description);
//
//            medicine.setText(consumption.getMedicine().getEventMedicine());
//            consumedOn.setText(CommonUtil.formatDateStandart(consumption.getEventConsumedOn()));
//            quantity.setText(String.valueOf(consumption.getEventQuantity()));
//            description.setText(consumption.getMedicine().getEventDescription() + "Sample description");
//
//            mPopupWindow = new PopupWindow(
//                    customView,
//                    LayoutParams.WRAP_CONTENT,
//                    LayoutParams.WRAP_CONTENT
//            );
//
//            if (Build.VERSION.SDK_INT >= 21) {
//                mPopupWindow.setElevation(5.0f);
//            }
//
////            ImageButton closeButton = (ImageButton) customView.findViewById(R.id.popup_close);
////
////            closeButton.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    mPopupWindow.dismiss();
////                }
////            });
//
//            mPopupWindow.showAtLocation(consumptionFragmentHolder, Gravity.CENTER, 0, 0);
//        }


    }
}
