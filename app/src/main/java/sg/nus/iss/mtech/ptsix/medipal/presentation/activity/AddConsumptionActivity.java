package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.manager.ConsumptionManager;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Consumption;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ConsumptionVO;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.AddConsumptionDatePickerFragment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.AddConsumptionTimePickerFragment;

public class AddConsumptionActivity extends AppCompatActivity {
    public static final String TIME_PICKER = "ADD_CONSUMPTION_TIME_PICKER";
    public static final String DATE_PICKER = "ADD_CONSUMPTION_DATE_PICKER";
    private ViewHolder viewHolder;
    private ConsumptionManager consumptionManager;
    private Consumption consumption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_consumption);

        viewHolder = new ViewHolder();
        consumptionManager = new ConsumptionManager(this);

        consumption = getIntent().getExtras().getParcelable(Constant.CONSUMPTION_BUNDLE);
        consumption.setConsumedOn(CommonUtil.convertStringToDate(getIntent().getStringExtra(Constant.CONSUMED_TIME), Constant.DATE_TIME_FORMAT));

        if (consumption != null) {

            ConsumptionVO consumptionVO = consumptionManager.castToConsumptionVo(consumption);

            String consumedDate = CommonUtil.date2ddMMMYYYY(consumptionVO.getConsumedOn());
            String consumedTime = CommonUtil.getFormattedTime(consumptionVO.getConsumedOn());

            viewHolder.consumedDateView.setText(consumedDate);
            viewHolder.consumedTimeView.setText(consumedTime);
            viewHolder.medicineNameView.setText(String.valueOf(consumptionVO.getMedicine().getMedicine()));
            viewHolder.consumedQuantityView.setText(String.valueOf(consumption.getQuantity()));

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

                    consumptionManager.update(consumption);

                    Toast.makeText(AddConsumptionActivity.this, getResources().getText(R.string.medi_consume_saved), Toast.LENGTH_LONG).show();
                }
            });

            viewHolder.missedButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(AddConsumptionActivity.this, getResources().getText(R.string.medi_consume_missed), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    class ViewHolder {
        public final EditText consumedDateView;
        public final EditText consumedTimeView;
        public final EditText medicineNameView;
        public final EditText consumedQuantityView;
        public final Button saveButtonView;
        public final Button missedButtonView;

        public ViewHolder() {
            consumedDateView = (EditText) findViewById(R.id.et_consumed_date);
            consumedTimeView = (EditText) findViewById(R.id.et_consumed_time);
            medicineNameView = (EditText) findViewById(R.id.et_medicine_name);
            consumedQuantityView = (EditText) findViewById(R.id.et_quantity);
            saveButtonView = (Button) findViewById(R.id.btn_consumption_save);
            missedButtonView = (Button) findViewById(R.id.btn_consumption_missed);
        }
    }
}
