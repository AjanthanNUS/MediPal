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
import sg.nus.iss.mtech.ptsix.medipal.business.services.ICEContactService;
import sg.nus.iss.mtech.ptsix.medipal.common.enums.ICEContactTypeEnums;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.ICE;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ICEContactActivity;

/**
 * Created by JOHN on 3/12/2017.
 */

public class ICEContactAddFragment extends Fragment {

    private EditText name, iceContactNo, description;
    private Spinner iceContactTYPE, iceSequenceNo;
    private Button btnSave, btnCancel;
    private ICEContactService iceContactService;
    List<ICE> iceList;

    public ICEContactAddFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iceContactService = new ICEContactService(this.getContext());
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

        iceList = iceContactService.getAllICEContact();
        totalCount = iceList.size();
        Log.i("", "Sixe : " + totalCount);

        ArrayAdapter<ICEContactTypeEnums> contactTypeAdapter = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, ICEContactTypeEnums.getAllICEContactTypeEnums());
        contactTypeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        iceContactTYPE.setAdapter(contactTypeAdapter);

        List<String> list = new ArrayList<>();
        for(int i = 1; i < totalCount + 1; i++) {
            list.add(String.valueOf(i));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        iceSequenceNo.setAdapter(dataAdapter);

        btnSave = (Button) rootView.findViewById(R.id.btn_ice_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Save button : ","Clicked");
                boolean isNewId = isNewValidByID();
                Log.i("Is New ID : ", String.valueOf(isNewValidByID()));
                if(isNewId) {
                    iceContactService.saveICEContact(getICEContactFromInput());
                    Toast.makeText(getActivity(), "Saved New ICE Contact!!!", Toast.LENGTH_SHORT).show();
                    resetFields();
                    ((ICEContactActivity) getActivity()).switchTab(0, -1);
                } else {
                    populateICESequences();
                    iceList = iceContactService.getAllICEContact();
                    Toast.makeText(getActivity(), "Update ICE Contact Info!!!", Toast.LENGTH_SHORT).show();
                    resetFields();
                    ((ICEContactActivity) getActivity()).switchTab(0, -1);
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
       // Log.i("Show User Info : ", name.getText().toString());
       // Log.i("Show User Info : ", name.getText().toString());
        super.setUserVisibleHint(isVisibleToUser);
        int id = getArguments().getInt("id");
        Log.i("ID : ", String.valueOf(id));
        if (getView() != null) {
            if (isVisibleToUser) {
                if (id >= 1) {
                    ICE ice = this.iceContactService.getICEContactByID(id);
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

    private ICE getICEContactFromInput() {
        ICE ice = new ICE();
        ice.setId(getArguments().getInt("id"));
        ice.setName(name.getText().toString());
        ice.setContactNo(iceContactNo.getText().toString());
        Log.i("iceContactTYPE STR : ", ((ICEContactTypeEnums)iceContactTYPE.getSelectedItem()).getEnumValue());
        Log.i("iceContactTYPE INT : ", String.valueOf(((ICEContactTypeEnums)iceContactTYPE.getSelectedItem()).getEnumCode()));
        ice.setIceContactType(((ICEContactTypeEnums)iceContactTYPE.getSelectedItem()).getEnumCode());
        ice.setDescription(description.getText().toString());
        ice.setSequence(iceList.size());
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

    private boolean isNewValid() {
        if (iceContactService.getICEContactByName(name.getText().toString().trim()) == null) {
           return true;
        }
        return false;
    }

    private boolean isNewValidByID() {
        if (iceContactService.getICEContactByID(getArguments().getInt("id")) == null) {
            return true;
        }
        return false;
    }

    private void populateICESequences() {
        ICE newICEContact = getICEContactFromInput();
        int listCount = iceList.size();
        Log.i("populateICESequences : " , newICEContact.getName());
        int i = 0;
        int selectedItem  = (int)iceSequenceNo.getSelectedItemPosition();
        int k = selectedItem;
        int l = 0;
        boolean isSequenceNoChanged = false;

        for(ICE iceContact : iceList) {
            Log.i(newICEContact.getName()  , iceContact.getName());
            if(newICEContact.getId() == iceContact.getId() && newICEContact.getSequence() != iceContact.getSequence()) {
                isSequenceNoChanged = true;
                Log.i("1. Name : " + newICEContact.getId(), newICEContact.getName());
                iceContact.setSequence(k);
                i++;
                Log.i("After Exchange" + iceContact.getName(), String.valueOf(iceContact.getSequence()));
                break;
            }
        }
        if(iceList.size() != i) {
            for (ICE iceContact : iceList) {
                if (iceContact.getId() != newICEContact.getId()) {
                    iceContact.setSequence(i);
                    i++;
                }
            }
        }
        iceContactService.updateICEContactByID(iceList);
    }
}
