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
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.ICE;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ICEContactActivity;

public class ICEContactAddFragment extends Fragment {

    private EditText name, iceContactNo, description;
    private Spinner iceContactTYPE, iceSequenceNo;
    private Button btnSave, btnCancel, btnDelete;
    private ICEContactService iceContactService;
    private List<ICE> iceList;

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
                boolean isNewId = isNewValidByID();
                if(isCommandValid() && isNewId) {
                    iceContactService.saveICEContact(toSaveICEContactFromInput());
                    Toast.makeText(getActivity(), getResources().getString(R.string.ice_save_contact_alert), Toast.LENGTH_SHORT).show();
                    resetFields();
                    ((ICEContactActivity) getActivity()).switchTab(0, -1);
                } else {
                    if(isCommandValid()) {
                        populateICESequences();
                        iceList = iceContactService.getAllICEContact();
                        Toast.makeText(getActivity(), getResources().getString(R.string.ice_update_contact_alert), Toast.LENGTH_SHORT).show();
                        resetFields();
                        ((ICEContactActivity) getActivity()).switchTab(Constant.TAB_LIST_INDEX, Constant.INVALID_INDEX_ID);
                    }

                }
            }
        });

        btnCancel = (Button) rootView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
                ((ICEContactActivity) getActivity()).switchTab(Constant.TAB_LIST_INDEX, Constant.INVALID_INDEX_ID);
            }
        });

        btnDelete = (Button) rootView.findViewById(R.id.btn_ice_delete);

        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                iceContactService.deleteCEContactsByID(getICEContactFromInput().getId());
                deleteSelectedSequence();
                Toast.makeText(getActivity(), getResources().getString(R.string.ice_delete_contact_alert), Toast.LENGTH_SHORT).show();
                resetFields();
                ((ICEContactActivity) getActivity()).switchTab(Constant.TAB_LIST_INDEX, Constant.INVALID_INDEX_ID);
            }
        });

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
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

    /**
     * select Spinner Item By Value
     * @param spnr
     * @param value
     */
    private void selectSpinnerItemByValue(Spinner spnr, long value) {
        ArrayAdapter<ICEContactTypeEnums> adapter = (ArrayAdapter<ICEContactTypeEnums>)spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(adapter.getItemId(position) == value) {
                spnr.setSelection(position);
                return;
            }
        }
    }

    /**
     * get ICE Contact From Input
     * @return ICE
     */
    private ICE getICEContactFromInput() {
        ICE ice = new ICE();
        ice.setId(getArguments().getInt("id"));
        ice.setName(name.getText().toString());
        ice.setContactNo(iceContactNo.getText().toString());
        ice.setIceContactType(((ICEContactTypeEnums)iceContactTYPE.getSelectedItem()).getEnumCode());
        ice.setDescription(description.getText().toString());
        ice.setSequence(Integer.parseInt((String)iceSequenceNo.getSelectedItem()));
        return ice;
    }

    /**
     * to Save ICE Contact From UI
     * @return ICE
     */
    private ICE toSaveICEContactFromInput() {
        ICE ice = new ICE();
        ice.setId(getArguments().getInt("id"));
        ice.setName(name.getText().toString());
        ice.setContactNo(iceContactNo.getText().toString());
        ice.setIceContactType(((ICEContactTypeEnums)iceContactTYPE.getSelectedItem()).getEnumCode());
        ice.setDescription(description.getText().toString());
        ice.setSequence(iceList.size());
        return ice;
    }

    /**
     * reset Fields
     */
    private void resetFields() {
        int id = this.getArguments().getInt("id");
        this.getArguments().putInt("id", Constant.INVALID_INDEX_ID);
        this.name.setText("");
        this.iceContactNo.setText("");
        //this.iceContactTYPE.setPromptId(0);
        this.description.setText("");
        //this.iceSequenceNo.setPromptId(1);
        this.btnDelete.setVisibility(View.GONE);

    }

    /**
     * is isCommand Valid
     * @return boolean
     */
    private boolean isCommandValid() {
        boolean isValid = true;

        String nameStr = this.name.getText().toString();
        String iceContactNoStr = this.iceContactNo.getText().toString();
        // ICE name required check
        if (TextUtils.isEmpty(name.getText())) {
            name.setError("* ICE Name name.");
            isValid = false;
        }
        // ICE name number of character check
        int iceContactNoInt = 0;
        try {
            iceContactNoInt = Integer.parseInt(iceContactNoStr);
        } catch (NumberFormatException e) {
            iceContactNo.setError(getResources().getString(R.string.ice_contact_no_numeric));
            isValid = false;
        }
        if (iceContactNoInt < 10000000 || iceContactNoInt > 99999999) {
            iceContactNo.setError(getResources().getString(R.string.ice_contact_no_length));
            isValid = false;
        }
        return isValid;
    }

    /**
     * check valid ID or not
     * @return boolean
     */
    private boolean isNewValidByID() {
        if (iceContactService.getICEContactByID(getArguments().getInt("id")) == null) {
            return true;
        }
        return false;
    }


    /*
       * 1) Moving Top order position element (X) to Down side of the list Y position:
           Details :
               a) No change if there is X-i
               b) No change if there is Y+i
               c) X posistion must became Y posistion
               d) from X+1 element to Y element posistion decereased by one.
               Example : 2 => 1 , 3=> 2 etc, n => n-1 & 1 => n

         2) Moving X position into top order (Posistion Y)
           Details :
               a) No change in order from 1st posistion to Y Postion.
               b) No change in after the X position
               c) X posistion became Y position
               d) Y to X position increased by 1
               Example : x=> Y, y => Y+1, Y+1 => Y+2 ...Until X Position
       * */

    /**
     * Above is the detailed scenarios of below function
     * populate ICE Sequences numbers
     */
    private void populateICESequences() {
        ICE newICEContact = getICEContactFromInput();
        Log.i("populateICESequences : " , newICEContact.getName());
        int newSelectedValue  = iceSequenceNo.getSelectedItemPosition();
        int currentSelectedValue = -1;
        List<ICE> updatedICEContactList = new ArrayList<>();

        for(ICE iceContact : iceList) {
            if(newICEContact.getId() == iceContact.getId() && newSelectedValue != iceContact.getSequence()) {
                currentSelectedValue = iceContact.getSequence();
                iceContact.setSequence(newSelectedValue);
                Log.i(iceContact.getName(), "New SEQ NO " + String.valueOf(newSelectedValue));

                updatedICEContactList.add(iceContact);
                break;
            }
        }

        if(currentSelectedValue == -1) {
            return;
        }

        if(currentSelectedValue > newSelectedValue) {
            int i = newSelectedValue;
            for(int j = i; j <= currentSelectedValue; j++) {

                ICE ice = iceList.get(j);
                if(newICEContact.getId() != ice.getId()) {
                    i++;
                    ice.setSequence(i);
                    Log.i(ice.getName(), "New SEQ NO " + String.valueOf(i));
                    updatedICEContactList.add(ice);
                }
            }
        }

        if( newSelectedValue > currentSelectedValue) {
            int i = newSelectedValue;
            for(int j = i; j > currentSelectedValue; j--) {
                ICE ice = iceList.get(j);
                if(newICEContact.getId() != ice.getId()) {
                    i--;
                    ice.setSequence(i);
                    Log.i(ice.getName(), "New SEQ NO " + String.valueOf(i));
                    updatedICEContactList.add(ice);
                }
            }
        }
        iceContactService.updateICEContactByID(updatedICEContactList);
    }

    /**
     * adjust sequence number when user delete record from ICE Contact List
     */
    private void deleteSelectedSequence() {
        ICE newICEContact = getICEContactFromInput();
        Log.i("populateICESequences : " , newICEContact.getName());
        int selectedValue  = iceSequenceNo.getSelectedItemPosition();
        List<ICE> updatedICEContactList = new ArrayList<>();

        int i = selectedValue;
        for(int j = i + 1; j < iceList.size(); j++) {
            ICE ice = iceList.get(j);
            if(newICEContact.getId() != ice.getId()) {
                ice.setSequence(i);
                i++;
                Log.i(ice.getName(), "New SEQ NO " + String.valueOf(i));
                updatedICEContactList.add(ice);
            }
        }
        iceContactService.updateICEContactByID(updatedICEContactList);
    }

}
