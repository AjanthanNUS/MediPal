package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.enums.HealthBioConditionTypeEnums;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.HealthBioDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.HealthBio;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.HealthBioActivity;

/**
 * Created by JOHN on 3/12/2017.
 */

public class HealthBioAddFragment extends android.support.v4.app.Fragment {

    private EditText condition, startDate;
    private Spinner conditionType;
    private Button btnSave, btnCancel;
    private HealthBioDao healthBioDao;

    public HealthBioAddFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        healthBioDao = new HealthBioDao(this.getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_health_bio_add, container, false);

        condition = (EditText) rootView.findViewById(R.id.health_condition);
        conditionType = (Spinner) rootView.findViewById(R.id.health_condition_type);
        startDate = (EditText) rootView.findViewById(R.id.health_start_date);

        healthBioDao = new HealthBioDao(this.getContext());
        List<HealthBio> healthBios = healthBioDao.getHealthBios();

        String [] contactTypeValues = HealthBioConditionTypeEnums.getAllHealthConditionTYPES();
        ArrayAdapter<String> contactTypeAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, contactTypeValues);
        contactTypeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        conditionType.setAdapter(contactTypeAdapter);

        btnSave = (Button) rootView.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments().getInt("id") == -1) {
                    // Add new
                    if (isCommonValid()) {
                        healthBioDao.save(createHealthBioFromInput());
                        Toast.makeText(getActivity(), "Saving new category completed", Toast.LENGTH_SHORT).show();
                        resetFields();
                        ((HealthBioActivity) getActivity()).switchTab(0, -1);
                    }
                } else {
                    // Update
                    if (isCommonValid() && isUpdateValid()) {
                        healthBioDao.save(createHealthBioFromInput());
                        Toast.makeText(getActivity(), "Updating category completed", Toast.LENGTH_SHORT).show();
                        resetFields();
                        ((HealthBioActivity) getActivity()).switchTab(0, -1);
                    }
                }
            }
        });

        btnCancel = (Button) rootView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancel the Category
                resetFields();
                ((HealthBioActivity) getActivity()).switchTab(0, -1);
            }
        });

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        int id = getArguments().getInt("id");
        if (getView() != null) {
            if (isVisibleToUser) {
                if (id >= 1) {
                    HealthBio healthBio = this.healthBioDao.getHealthBio(id);
                    condition.setText(healthBio.getEventCondition());
                    //selectSpinnerItemByValue(conditionType, healthBio.getEventConditionType());
                    startDate.setText(healthBio.getEventStartDate().toString());
                } else {
                    this.resetFields();
                }
            } else {
                this.resetFields();
            }
        }
    }

    private void selectSpinnerItemByValue(Spinner spnr, long value) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>)spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(adapter.getItemId(position) == value) {
                spnr.setSelection(position);
                return;
            }
        }
    }

    private HealthBio createHealthBioFromInput() {
        HealthBio healthBio = new HealthBio();
        healthBio.setEventCondition(condition.getText().toString());
        healthBio.setEventConditionType(conditionType.getSelectedItem().toString());
        SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy");
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
