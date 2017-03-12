package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.manager.ConsumptionManager;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ConsumptionVO;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ConsumptionViewAdapter;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.ConsumptionListFragment;

public class ConsumptionActivity extends AppCompatActivity implements ConsumptionListFragment.OnListFragmentInteractionListener {
    private final int DATE_DIALOG_ID = 999;
    private PopupWindow mPopupWindow;
    private FrameLayout consumptionFragmentHolder;
    private ConsumptionListFragment consumptionListFragment;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);

        consumptionListFragment = ConsumptionListFragment.newInstance(1);

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
                final String searchMedicine = medicineET.getText().toString();
                final String searchCategory = categoryET.getText().toString();
                filterConsumptionHistory(searchMedicine, searchCategory);
            }
        });

        Button resetButton = (Button) filterLayout.findViewById(R.id.filter_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterLayout.setVisibility(View.GONE);
                EditText medicineET = (EditText) filterLayout.findViewById(R.id.filter_medicine);
                EditText categoryET = (EditText) filterLayout.findViewById(R.id.filter_category);
                medicineET.getText().clear();
                categoryET.getText().clear();
                filterConsumptionHistory(null, null);
            }
        });
    }

    private void filterConsumptionHistory(String searchMedicine, String searchCategory) {
        ConsumptionViewAdapter viewAdapter = consumptionListFragment.getConsumptionViewAdapter();
        List<ConsumptionVO> consumptionList = viewAdapter.getmConsumptionList();
        if (searchMedicine == null && searchCategory == null) {
            consumptionList = new ArrayList<>();
            ConsumptionManager consumptionManager = new ConsumptionManager();
            consumptionList.addAll(consumptionManager.getAllConsumptionList());
            viewAdapter.getmConsumptionList().clear();
            viewAdapter.getmConsumptionList().addAll(consumptionList);
            viewAdapter.notifyDataSetChanged();
        } else {
            List<ConsumptionVO> filterList = new ArrayList<>();

            for (ConsumptionVO vo : consumptionList) {
                if (CommonUtil.isNullOrEmpty(searchMedicine) || (vo.getMedicine() != null && vo.getMedicine().getEventMedicine().contains(searchMedicine))) {
                    if (CommonUtil.isNullOrEmpty(searchCategory) || (vo.getCategories() != null && vo.getCategories().getEventCategory().contains(searchCategory))) {
                        filterList.add(vo);
                    }
                }
            }
            viewAdapter.getmConsumptionList().clear();
            viewAdapter.getmConsumptionList().addAll(filterList);
            viewAdapter.notifyDataSetChanged();
        }
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
