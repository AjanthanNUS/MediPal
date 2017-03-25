package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.manager.ConsumptionManager;
import sg.nus.iss.mtech.ptsix.medipal.business.services.MedicineService;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Consumption;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ConsumptionVO;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.AddConsumptionDatePickerFragment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.AddConsumptionTimePickerFragment;

public class AddConsumptionActivity extends AppCompatActivity {
    public static final String TIME_PICKER = "ADD_CONSUMPTION_TIME_PICKER";
    public static final String DATE_PICKER = "ADD_CONSUMPTION_DATE_PICKER";
    private ViewHolder viewHolder;
    private ConsumptionManager consumptionManager;
    private Consumption consumption;
    private MedicineService medicineService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_consumption);


        viewHolder = new ViewHolder();
        consumptionManager = new ConsumptionManager(this);


        consumption = getIntent().getExtras().getParcelable(Constant.CONSUMPTION_BUNDLE);
        String consumedDateStr = getIntent().getStringExtra(Constant.CONSUMED_TIME);

        if (consumption != null) {
            consumption.setConsumedOn(CommonUtil.convertStringToDate(consumedDateStr, Constant.DATE_TIME_FORMAT));
            ConsumptionVO consumptionVO = consumptionManager.castToConsumptionVo(consumption);

            String consumedDate = CommonUtil.date2ddMMMYYYY(consumptionVO.getConsumedOn());
            String consumedTime = CommonUtil.getFormattedTime(consumptionVO.getConsumedOn());

            viewHolder.consumedDateView.setText(consumedDate);
            viewHolder.consumedTimeView.setText(consumedTime);
            viewHolder.medicineNameView.setText(String.valueOf(consumptionVO.getMedicine().getMedicine()));
            viewHolder.consumedQuantityView.setText(String.valueOf(consumption.getQuantity()));
        } else if (!CommonUtil.isNullOrEmpty(consumedDateStr)) {
            Date consDate = CommonUtil.convertStringToDate(consumedDateStr, Constant.DATE_TIME_FORMAT);
            String consumedDate = CommonUtil.date2ddMMMYYYY(consDate);
            String consumedTime = CommonUtil.getFormattedTime(consDate);

            viewHolder.consumedDateView.setText(consumedDate);
            viewHolder.consumedTimeView.setText(consumedTime);
        }

        viewHolder.consumedDateView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    viewHolder.consumedDateView.callOnClick();
                }
            }
        });
        viewHolder.consumedDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new AddConsumptionDatePickerFragment();
                newFragment.show(getFragmentManager(), DATE_PICKER);
            }
        });

        viewHolder.consumedTimeView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    viewHolder.consumedTimeView.callOnClick();
                }
            }
        });
        viewHolder.consumedTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new AddConsumptionTimePickerFragment();
                newFragment.show(getFragmentManager(), TIME_PICKER);
            }
        });

        viewHolder.saveButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  consumption.setConsumedOn();
                if (consumption != null) {
                    consumptionManager.update(consumption);
                } else {
                    manuallyAddConsumption(false);
                }
                Toast.makeText(AddConsumptionActivity.this, getResources().getText(R.string.medi_consume_saved), Toast.LENGTH_LONG).show();
                Intent consumptionListView = new Intent(AddConsumptionActivity.this, ConsumptionActivity.class);
                startActivity(consumptionListView);
                AddConsumptionActivity.this.finish();
            }
        });

        viewHolder.missedButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (consumption == null) {

                    manuallyAddConsumption(true);
                }
                Toast.makeText(AddConsumptionActivity.this, getResources().getText(R.string.medi_consume_missed), Toast.LENGTH_LONG).show();
                Intent consumptionListView = new Intent(AddConsumptionActivity.this, ConsumptionActivity.class);
                startActivity(consumptionListView);
                AddConsumptionActivity.this.finish();
            }
        });

        setUpMedicineFilterAutocomplete();

    }

    private boolean manuallyAddConsumption(boolean missed) {
        boolean isSaved = false;
        medicineService = new MedicineService(this);
        String medName = viewHolder.medicineNameView.getText().toString().trim();
        List<Medicine> medicines = medicineService.getMedicineByName(medName);
        if (medicines.size() > 0) {
            Medicine medicine = medicines.get(0);
            consumption = new Consumption();
            consumption.setMedicineID(medicine.getId());
            if (missed) {
                consumption.setQuantity(0);
            } else {
                consumption.setQuantity(Integer.valueOf(viewHolder.consumedQuantityView.getText().toString()));
            }

            String dateTime = viewHolder.consumedDateView.getText().toString().trim() + " " + viewHolder.consumedTimeView.getText().toString().trim();
            Date consumedOn = CommonUtil.convertStringToDate(dateTime, Constant.DATE_TIME_FORMAT);
            consumption.setConsumedOn(consumedOn);

            consumptionManager.insertConsumption(consumption);
            isSaved = true;
        } else {
            viewHolder.medicineNameView.setError(getResources().getText(R.string.medicine_not_in_inventory));
        }
        return isSaved;
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

        viewHolder.medicineNameView.setAdapter(medicineAdapter);
        viewHolder.medicineNameView.setThreshold(1);
    }


    class ViewHolder {
        public final EditText consumedDateView;
        public final EditText consumedTimeView;
        public final AutoCompleteTextView medicineNameView;
        public final EditText consumedQuantityView;
        public final Button saveButtonView;
        public final Button missedButtonView;

        public ViewHolder() {
            consumedDateView = (EditText) findViewById(R.id.et_consumed_date);
            consumedTimeView = (EditText) findViewById(R.id.et_consumed_time);
            medicineNameView = (AutoCompleteTextView) findViewById(R.id.actv_medicine_name);
            consumedQuantityView = (EditText) findViewById(R.id.et_quantity);
            saveButtonView = (Button) findViewById(R.id.btn_consumption_save);
            missedButtonView = (Button) findViewById(R.id.btn_consumption_missed);
        }
    }
}
