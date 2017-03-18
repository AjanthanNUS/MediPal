package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.PersonalBioDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.PersonalBio;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalBioEditFragment extends Fragment {

    private EditText uesrName = null;
    private EditText dob = null;
    private TextView userIDNo = null;
    private EditText address = null;
    private EditText postalCode = null;
    private EditText height = null;
    private EditText bloodType = null;
    private Button saveButton = null;
    private boolean validationStatus = true;
    private boolean isEdit = false;
    private PersonalBio personalBio = null;
    private PersonalBioDao personalBioDao = null;
    public static final String TITLE = "Personal Bio";
    private static final String USERID = "UserId";
    private static final String EMPTY_VALUE = "";
    private static final int USERID_DEFAULT = 1;
    private static final String USERIDNo_BASE = "MPT600";
    private static final String SUCCESS_MESSAGE = "Saved Successfully";
    private static final String FAILED_MESSAGE = "Unable to save, please try again...";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

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

        initializeComponents(rootView);
        getPersonalBioInfo();
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
        bloodType = (EditText) rootView.findViewById(R.id.bloodTypeText);
        saveButton = (Button) rootView.findViewById(R.id.saveButton);
    }

    public void getPersonalBioInfo() {
        // add try catch
        personalBioDao = new PersonalBioDao(this.getContext());
        personalBio = personalBioDao.getPersonalBio();
    }

    private void loadPersonalBioEditView() {
        if (personalBio != null) {
            uesrName.setText(personalBio.getUserName());
            userIDNo.setText(personalBio.getUserIDNo());
            dob.setText(personalBio.getUserDOB().toString());
            address.setText(personalBio.getAddress());
            postalCode.setText(personalBio.getPostalcode());
            height.setText(personalBio.getHeight());
            bloodType.setText(personalBio.getBloodType());
        } else {
            uesrName.setText(EMPTY_VALUE);
            userIDNo.setText(generateUserIDNo());
            dob.setText(EMPTY_VALUE);
            address.setText(EMPTY_VALUE);
            postalCode.setText(EMPTY_VALUE);
            height.setText(EMPTY_VALUE);
            bloodType.setText(EMPTY_VALUE);
        }
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
        if (TextUtils.isEmpty(uesrName.getText().toString().trim())) {
            uesrName.setError("Please enter your name.");
            validationStatus = false;
        }
        if (TextUtils.isEmpty(bloodType.getText().toString().trim())) {
            bloodType.setError("Please enter your blood type.");
            validationStatus = false;
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
            personalBioDao.savePersonalBio(personalBio);
            Toast.makeText(this.getContext(), SUCCESS_MESSAGE, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getContext(), FAILED_MESSAGE, Toast.LENGTH_SHORT).show();
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
            personalBio.setUserDOB(formatter.parse(dob.getText().toString()));
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
        if (bloodType.getText() != null) {
            personalBio.setBloodType(bloodType.getText().toString());
        }
    }

//    public void getPersonalBioInfo(int userId) {
//        if (userId > 0) {
//            personalBioDao = new PersonalBioDao(this.getContext());
//            personalBio = personalBioDao.getPersonalBio();
//        }
//        if (personalBio == null) {
//            personalBio = new PersonalBio();
//        }
//    }
}
