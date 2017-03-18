package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.enums.ICEContactTypeEnums;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.IceDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.ICE;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ICEContactActivity;

/**
 * Created by JOHN on 3/12/2017.
 */

public class ICEContactAddFragment extends Fragment {

    private EditText name, iceContactNo, description;
    private Spinner iceContactTYPE, iceSequenceNo;
    private Button btnSave, btnCancel;
    private IceDao iceDao;

    private ICEContactTypeEnums selectedEnum;

    public ICEContactAddFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iceDao = new IceDao(this.getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int totalCount;
        View rootView = inflater.inflate(R.layout.fragment_ice_add, container, false);

        name = (EditText) rootView.findViewById(R.id.ice_name);
        iceContactNo = (EditText) rootView.findViewById(R.id.ice_contact_no);
        description = (EditText) rootView.findViewById(R.id.ice_description);

        iceContactTYPE = (Spinner) rootView.findViewById(R.id.ice_contact_type);
        iceSequenceNo = (Spinner) rootView.findViewById(R.id.ice_sequence);

        IceDao dao = new IceDao(this.getContext());
        List<ICE> iceList = dao.getICEs();
        totalCount = iceList.size();
        Log.i("", "Sixe : " + totalCount);

        String [] contactTypeValues = ICEContactTypeEnums.getAllContacts();
        ArrayAdapter<ICEContactTypeEnums> contactTypeAdapter = new ArrayAdapter<ICEContactTypeEnums>(this.getActivity(),
                android.R.layout.simple_spinner_item, ICEContactTypeEnums.getAllICEContactTypeEnums());
        contactTypeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        iceContactTYPE.setAdapter(contactTypeAdapter);


        Log.i("iceContactTYPE STR : ", ((ICEContactTypeEnums)iceContactTYPE.getSelectedItem()).getEnumValue());
        Log.i("iceContactTYPE INT : ", String.valueOf(((ICEContactTypeEnums)iceContactTYPE.getSelectedItem()).getEnumCode()));

        List<String> list = new ArrayList<>();
        for(int i = 1; i < totalCount+1; i++) {
            list.add(String.valueOf(i));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        iceSequenceNo.setAdapter(dataAdapter);

        btnSave = (Button) rootView.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments().getInt("id") == -1) {
                    // Add new
                    if (isCommonValid()) {
                        iceDao.save(createICEContactFromInput());
                        Toast.makeText(getActivity(), "Saving new category completed", Toast.LENGTH_SHORT).show();
                        resetFields();
                        ((ICEContactActivity) getActivity()).switchTab(0, -1);
                    }
                } else {
                    // Update
                    if (isCommonValid() && isUpdateValid()) {
                        iceDao.save(createICEContactFromInput());
                        Toast.makeText(getActivity(), "Updating category completed", Toast.LENGTH_SHORT).show();
                        resetFields();
                        ((ICEContactActivity) getActivity()).switchTab(0, -1);
                    }
                }
            }
        });

        btnCancel = (Button) rootView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
                ((ICEContactActivity) getActivity()).switchTab(0, -1);
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
                    ICE ice = this.iceDao.getICE(id);
                    name.setText(ice.getName());
                    iceContactNo.setText(ice.getContactNo());
                    selectSpinnerItemByValue(iceContactTYPE, ice.getIceContactType());
                    description.setText(ice.getDescription());
                    selectSpinnerItemByValue(iceSequenceNo, ice.getSequence());
                } else {
                    this.resetFields();
                }
            } else {
                this.resetFields();
            }
        }
    }

    private void selectSpinnerItemByValue(Spinner spnr, long value) {
        ArrayAdapter<ICEContactTypeEnums> adapter = (ArrayAdapter<ICEContactTypeEnums>)spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(adapter.getItemId(position) == value) {
                spnr.setSelection(position);
                return;
            }
        }

}

    private ICE createICEContactFromInput() {
        ICE ice = new ICE();
        ice.setName(name.getText().toString());
        ice.setContactNo(iceContactNo.getText().toString());
        Log.i("iceContactTYPE STR : ", ((ICEContactTypeEnums)iceContactTYPE.getSelectedItem()).getEnumValue());
        Log.i("iceContactTYPE INT : ", String.valueOf(((ICEContactTypeEnums)iceContactTYPE.getSelectedItem()).getEnumCode()));
        ice.setIceContactType(((ICEContactTypeEnums)iceContactTYPE.getSelectedItem()).getEnumCode());
        ice.setDescription(description.getText().toString());
        ice.setSequence(iceSequenceNo.getSelectedItemPosition());
        return ice;
    }

    private void resetFields() {
        this.getArguments().putInt("id", -1);
        this.name.setText("");
        this.iceContactNo.setText("");
        //this.iceContactTYPE.setPromptId(0);
        this.description.setText("");
        //this.iceSequenceNo.setPromptId(1);
    }

  /*  private void getSpinner() {

        Spinner mySpinner = iceContactTYPE;
        mySpinner.setAdapter(new ArrayAdapter<ICEContactTypeEnums>(this, android.R., ICEContactTypeEnums.values()));
    }*/

    private boolean isCommonValid() {
        boolean isValid = true;

        // ICE name required check
        if (TextUtils.isEmpty(name.getText())) {
            name.setError("Please fill in a category name.");
            isValid = false;
        }

        // ICE name number of character check
        if (iceContactNo.getText().toString().trim().length() > 50) {
            iceContactNo.setError("Please fill in a name below 50 characters.");
            isValid = false;
        }

        return isValid;
    }
    private boolean isExistingICE(ICE ice) {
        if(ice.getName().equals(name.getText())) {
            return true;
        }
        return false;
    }

   /* private boolean isNewValid() {
        boolean isValid = true;

        if (iceDao.getICE(categoryCode.getText().toString().trim()).size() > 0) {
            categoryCode.setError(getResources().getString(R.string.category_add_error_category_code_duplicated));
            isValid = false;
        }

        return isValid;
    }
    */
      private boolean isUpdateValid() { return true;}

   /* private boolean isUpdateValid() {
        boolean isValid = true;

        List<Categories> categoriesList = categoriesDao.getCategoriesByCode(categoryCode.getText().toString().trim());
        if (categoriesList.size() > 0) {
            for (int i = 0; i < categoriesList.size(); i++) {
                Categories categories = categoriesList.get(i);
                if (getArguments().getInt("id") != categories.getId()) {
                    categoryCode.setError(getResources().getString(R.string.category_add_error_category_code_duplicated));
                    isValid = false;
                }
            }
        }

        return isValid;
    }*/
}
