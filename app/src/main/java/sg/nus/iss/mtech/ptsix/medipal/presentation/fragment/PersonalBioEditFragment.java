package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.enums.ICEContactTypeEnums;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.PersonalBioDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.PersonalBio;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalBioEditFragment extends Fragment {

    private int yearOfDate, monthOfDate, dayofDate;
    private EditText uesrName, dob, address, postalCode, height;
    private TextView userIDNo = null;
    private Spinner bloodType = null;
    private Button saveButton = null;
    private boolean validationStatus = true;
    private PersonalBio personalBio = null;
    private PersonalBioDao personalBioDao = null;
    ArrayAdapter<String> bloodTypeAdapter = null;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar = null;
    private static final int USERID_DEFAULT = 0;
    private static final String USERIDNo_BASE = "MPT6001";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constant.DATE_FORMAT, Locale.getDefault());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Intent intent = getActivity().getIntent();
//        if (intent != null && intent.getExtras() != null) {
//            int userId = intent.getIntExtra(USERID, 0);
//            getPersonalBioInfo(userId);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_bio_edit, container, false);

        getPersonalBioInfo();
        initializeComponents(rootView);
        loadPersonalBioEditView();
        updatePersonalBioInfo(rootView);

        return rootView;
    }

    private void initializeComponents(View rootView) {
        uesrName = (EditText) rootView.findViewById(R.id.uesrNameText);
        dob = (EditText) rootView.findViewById(R.id.dobText);
        userIDNo = (TextView) rootView.findViewById(R.id.uIDNoViewText);
        address = (EditText) rootView.findViewById(R.id.addressText);
        postalCode = (EditText) rootView.findViewById(R.id.postalCodeText);
        height = (EditText) rootView.findViewById(R.id.heightText);
        bloodType = (Spinner) rootView.findViewById(R.id.bloodTypeSpinner);
        saveButton = (Button) rootView.findViewById(R.id.saveButton);

        InitializeCalendar();
        setDatePickerDialog();
        setUserDOBListener(rootView);
        InitializeBloodType();
    }

    private void InitializeBloodType() {
        bloodTypeAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bloodType_items));
        bloodTypeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        bloodType.setAdapter(bloodTypeAdapter);
    }

    private void InitializeCalendar() {
        calendar = Calendar.getInstance();
        yearOfDate = calendar.get(Calendar.YEAR);
        monthOfDate = calendar.get(Calendar.MONTH);
        dayofDate = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(yearOfDate, monthOfDate, dayofDate);
    }

    private void setDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dpListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                yearOfDate = year;
                monthOfDate = month;
                dayofDate = day;

                calendar.set(yearOfDate, monthOfDate, dayofDate);
                dob.setText(dateFormatter.format(calendar.getTime()));
            }
        };
        datePickerDialog = new DatePickerDialog(getActivity(), dpListener, yearOfDate, monthOfDate, dayofDate);
    }

    private void setUserDOBListener(View rootView) {
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                         @Override
                                         public void onFocusChange(View v, boolean hasFocus) {
                                             dob.setError(null);
                                             if (hasFocus) {
                                                 datePickerDialog.show();
                                             } else {
                                                 datePickerDialog.hide();

                                             }
                                         }
                                     }
        );
    }

    public void getPersonalBioInfo() {
        // add try catch
        personalBioDao = new PersonalBioDao(this.getContext());
        personalBio = personalBioDao.getPersonalBio();
    }

    private void loadPersonalBioEditView() {
        if (personalBio != null) {
            Date userDOBField = personalBio.getUserDOB();
            String userIDNoField = personalBio.getUserIDNo();
            String addressField = personalBio.getAddress();
            String postalCodeField = personalBio.getPostalcode();
            int heightField = personalBio.getHeight();
            String bloodTypeField = personalBio.getBloodType();

            uesrName.setText(personalBio.getUserName());
            userIDNo.setText(personalBio.getUserIDNo());
            if (userDOBField != null) {
                dob.setText(CommonUtil.date2ddMMMYYYY(userDOBField));
            }
            if (addressField != null) {
                address.setText(addressField);
            } else {
                address.setText(Constant.EMPTY_VALUE);
            }
            if (postalCodeField != null) {
                postalCode.setText(postalCodeField);
            } else {
                postalCode.setText(Constant.EMPTY_VALUE);
            }
            if (heightField > 0) {
                height.setText(String.valueOf(heightField));
            }
            if (bloodTypeField != null) {
                bloodType.setSelection(getIndex(bloodType, bloodTypeField));
            }
        } else {
            uesrName.setText(Constant.EMPTY_VALUE);
            userIDNo.setText(generateUserIDNo());
            dob.setText(Constant.EMPTY_VALUE);
            address.setText(Constant.EMPTY_VALUE);
            postalCode.setText(Constant.EMPTY_VALUE);
            height.setText(Constant.EMPTY_VALUE);
            bloodType.setSelection(getIndex(bloodType, Constant.EMPTY_VALUE));
        }
    }

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int position = 0; position < spinner.getCount(); position++) {
            if (spinner.getItemAtPosition(position).toString().equalsIgnoreCase(myString)) {
                index = position;
                break;
            }
        }
        return index;
    }

    private String generateUserIDNo() {

        return USERIDNo_BASE + String.valueOf(USERID_DEFAULT);
    }

    public void updatePersonalBioInfo(final View rootView) {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatePersonalBio();
                updatePersonalBio();
            }
        });
    }

    private void validatePersonalBio() {

        String dobString = this.dob.getText().toString().trim();

        if (TextUtils.isEmpty(uesrName.getText().toString().trim())) {
            uesrName.setError("Please enter your name.");
            validationStatus = false;
        }
        if (dob.getText() == null || TextUtils.isEmpty(dobString)) {
            dob.setError(getResources().getString(R.string.empty_date));
            validationStatus = false;
        } else {
            try {
                if (!CommonUtil.checkDateBeforeToday(dateFormatter.parse(dobString))) {
                    this.dob.setError(getResources().getString(R.string.invalid_date));
                    validationStatus = false;
                }
            } catch (ParseException ex) {
                validationStatus = false;
            }
        }

        if (TextUtils.isEmpty(height.getText().toString().trim())) {
            height.setError("Please enter your height.");
            validationStatus = false;
        }
    }

    private void updatePersonalBio() {
        if (validationStatus) {
            getUpdatedPersonalBioInfo();
            personalBioDao = new PersonalBioDao(this.getContext());
            if (personalBioDao.savePersonalBio(personalBio) > 0) {
                PersonalBioViewFragment personalBioViewFragment = new PersonalBioViewFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.container_frame, personalBioViewFragment, personalBioViewFragment.getTag()).commit();

                Toast.makeText(this.getContext(), Constant.SAVE_SUCCEED_MESSAGE, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.getContext(), Constant.SAVE_FAILED_MESSAGE, Toast.LENGTH_SHORT).show();
            }
        } else {
            validationStatus = true;
        }
    }

    private void getUpdatedPersonalBioInfo() {
        int userId = USERID_DEFAULT;
        if (personalBio != null) {
            userId = personalBio.getId();
        } else {
            personalBio = new PersonalBio();
        }
        personalBio.setId(userId);
        personalBio.setUserIDNo(userIDNo.getText().toString());
        personalBio.setUserName(uesrName.getText().toString());
        try {
            personalBio.setUserDOB(dateFormatter.parse(this.dob.getText().toString().trim()));

        } catch (ParseException e) {
            personalBio.setUserDOB(null);
        }
        if (address.getText() != null) {
            personalBio.setAddress(address.getText().toString());
        }
        if (postalCode.getText() != null) {
            personalBio.setPostalcode(postalCode.getText().toString());
        }
        if (height.getText() != null) {
            personalBio.setHeight(Integer.parseInt(height.getText().toString()));
        }
        if (bloodType.getSelectedItem() != null) {
            personalBio.setBloodType(bloodType.getSelectedItem().toString());
        }
    }

//    public void getPersonalBioInfo(int userId) {
//        if (userId > 0) {
//            personalBioDao = new PersonalBioDao(this.getContext());
//            personalBio = personalBioDao.getPersonalBio();
//        }
//        if (personalBio == null) {
//            personalBio = new personalBio();
//        }
//    }
}
