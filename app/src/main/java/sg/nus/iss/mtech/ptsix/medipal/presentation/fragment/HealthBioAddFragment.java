package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.services.HealthBioService;
import sg.nus.iss.mtech.ptsix.medipal.common.enums.HealthBioConditionTypeEnums;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.HealthBio;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.HealthBioActivity;

/**
 * Created by JOHN on 3/12/2017.
 */

public class HealthBioAddFragment extends android.support.v4.app.Fragment {

    private EditText condition, startDate;
    private Spinner conditionType;
    private Button btnSave, btnCancel, btnDelete;
    private HealthBioService healthBioService;
    private List<HealthBio> healthBios;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constant.DATE_FORMAT, Locale.getDefault());

    public HealthBioAddFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        healthBioService = new HealthBioService(this.getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_health_bio_add, container, false);

        condition = (EditText) rootView.findViewById(R.id.health_condition);
        conditionType = (Spinner) rootView.findViewById(R.id.health_condition_type);
        startDate = (EditText) rootView.findViewById(R.id.health_start_date);

        healthBios = healthBioService.getAllHealthBio();

        List<HealthBioConditionTypeEnums> contactTypes = HealthBioConditionTypeEnums.getAllHealthBioCategory();
        ArrayAdapter<HealthBioConditionTypeEnums> contactTypeAapter = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, contactTypes);
        contactTypeAapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        conditionType.setAdapter(contactTypeAapter);

        btnSave = (Button) rootView.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isNewId = isNewValidByID();

                if (isNewId) {
                    healthBioService.saveHealthBioInfo(getHealthBioFromInput());
                    Toast.makeText(getActivity(), "Saved New healthBio", Toast.LENGTH_SHORT).show();
                    resetFields();
                    ((HealthBioActivity) getActivity()).switchTab(0, -1);
                } else {
                    healthBioService.updateHealthBioInfo(getHealthBioFromInput());
                    Toast.makeText(getActivity(), "Updating category completed", Toast.LENGTH_SHORT).show();
                    resetFields();
                    ((HealthBioActivity) getActivity()).switchTab(0, -1);
                }
            }
        });

        btnCancel = (Button) rootView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
                ((HealthBioActivity) getActivity()).switchTab(0, -1);
            }
        });

        btnDelete = (Button) rootView.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                healthBioService.deleteHealthBio(getHealthBioFromInput());
                Toast.makeText(getActivity(), "Deleted Successfully!!!", Toast.LENGTH_SHORT).show();
                resetFields();
                ((HealthBioActivity) getActivity()).switchTab(0, -1);
            }
        });

        return rootView;
    }

    private boolean isNewValidByID() {
        if (healthBioService.getHealthBioInfo(getArguments().getInt("id")) == null) {
            return true;
        }
        return false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        int id = getArguments().getInt("id");
        Log.i("ID : ", String.valueOf(id));
        if (getView() != null) {
            if (isVisibleToUser) {
                if (id >= 1) {
                    HealthBio healthBio = this.healthBioService.getHealthBioInfo(id);
                    condition.setText(healthBio.getEventCondition());

                    Log.i("Enum Code : ", String.valueOf(HealthBioConditionTypeEnums.getHealthBioConditionTypeEnums(
                            healthBio.getEventConditionType()).getConditionTypeCode()));

                    Log.i("Enum TYPE : ", String.valueOf(HealthBioConditionTypeEnums.getHealthBioConditionTypeEnums(
                            healthBio.getEventConditionType())));

                    selectSpinnerItemByValue(conditionType,
                            HealthBioConditionTypeEnums.getHealthBioConditionTypeEnums(
                                    healthBio.getEventConditionType()).getConditionTypeCode());

                    Log.i("DATE ", healthBio.getEventStartDate().toString());
                    String dateStr = dateFormatter.format(healthBio.getEventStartDate());
                    Log.i("After Parse DATE ", dateStr);
                    startDate.setText(dateStr);
                } else {
                    this.resetFields();
                }
            } else {
                this.resetFields();
            }
        }
    }

    private void selectSpinnerItemByValue(Spinner spnr, long value) {
        ArrayAdapter<HealthBioConditionTypeEnums> adapter = (ArrayAdapter<HealthBioConditionTypeEnums>)spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(adapter.getItemId(position) == value) {
                spnr.setSelection(position);
                return;
            }
        }
    }

    private HealthBio getHealthBioFromInput() {
        HealthBio healthBio = new HealthBio();
        healthBio.setId(getArguments().getInt("id"));
        healthBio.setEventCondition(condition.getText().toString());
        Log.i("Spinner ", conditionType.getSelectedItem().toString());
        Log.i("ENUM Spinner ", ((HealthBioConditionTypeEnums)conditionType.getSelectedItem()).getConditionTypeName());
        healthBio.setEventConditionType(((HealthBioConditionTypeEnums)conditionType.getSelectedItem()).getConditionTypeName());
        Log.i("Date ", startDate.getText().toString());
        Date date = null;
        try {
           date = dateFormatter.parse(startDate.getText().toString());
        } catch(ParseException ex) {
            ex.printStackTrace();
        }
        healthBio.setEventStartDate(date);
        return healthBio;
    }

    private void resetFields() {
        this.getArguments().putInt("id", -1);
        this.condition.setText("");
        this.conditionType.setSelection(1);
        this.startDate.setText("");
    }


    private boolean isCommonValid() {
        boolean isValid = true;

        if (TextUtils.isEmpty(condition.getText().toString().trim())) {
            condition.setError("Please fill in a category name.");
            isValid = false;
        }

        if (TextUtils.isEmpty(startDate.getText().toString().trim())) {
            startDate.setError("Please fill in a 3 character code.");
            isValid = false;
        }

        return isValid;
    }

    private boolean isUpdateValid() {
        return true;
    }

}
