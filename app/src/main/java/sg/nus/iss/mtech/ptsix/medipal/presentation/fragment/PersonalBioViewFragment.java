package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.PersonalBioDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.PersonalBio;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.HealthBioActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ICEContactActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalBioViewFragment extends Fragment {

    private PersonalBioEditFragment personalBioEditFragment = null;
    private FragmentManager manager = null;
    private TextView userNameTextView = null;
    private TextView userDOBTextView = null;
    private TextView userIDNoTextView = null;
    private TextView addressTextView = null;
    private TextView postalcodeTextView = null;
    private TextView heightTextView = null;
    private TextView bloodTypeTextView = null;
    private PersonalBio personalBio = null;
    private PersonalBioDao personalBioDao = null;
    private static final String USERID = "UserId";
    private static final int USERID_DEFAULT = 0;
    private Button addIceBtn, addHealthBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_bio_view, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabUpdatePersonalBio);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPersonalBioEdit();
            }
        });

        addIceBtn = (Button) rootView.findViewById(R.id.btn_add_ice);
        addIceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iceIntent = new Intent(getActivity(), ICEContactActivity.class);
                startActivity(iceIntent);
            }
        });

        addHealthBtn = (Button) rootView.findViewById(R.id.btn_add_health);
        addHealthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iceIntent = new Intent(getActivity(), HealthBioActivity.class);
                startActivity(iceIntent);

            }
        });

        initializeComponents(rootView);
        getPersonalBio();
        loadPersonalBio();

        return rootView;
    }

    private void getPersonalBio() {
        try {
            personalBioDao = new PersonalBioDao(this.getContext());
            personalBio = personalBioDao.getPersonalBio();
        } catch (Exception e) {
        }
    }

    private void loadPersonalBio() {
        if (personalBio != null) {
            loadPersonalBioView();
        } else {
            loadPersonalBioEdit();
        }
    }

    private void loadPersonalBioEdit() {
        personalBioEditFragment = new PersonalBioEditFragment();
        personalBioEditFragment.setArguments(getArgs());
        manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.container_frame, personalBioEditFragment, personalBioEditFragment.getTag()).commit();
    }

    private Bundle getArgs() {
        Bundle args = new Bundle();
        if (personalBio != null) {
            args.putInt(USERID, personalBio.getId());
        } else {
            args.putInt(USERID, USERID_DEFAULT);
        }
        return args;
    }

    private void loadPersonalBioView() {
        Date userDOB = personalBio.getUserDOB();
        String userIDNo = personalBio.getUserIDNo();
        String address =   personalBio.getAddress();
        String postalCode = personalBio.getPostalcode();
        int height = personalBio.getHeight();
        String bloodType = personalBio.getBloodType();

        userNameTextView.setText(personalBio.getUserName());
        if (userDOB != null) {
            userDOBTextView.setText(CommonUtil.date2ddMMMYYYY(userDOB));
        }
        if (userIDNo != null) {
            userIDNoTextView.setText(userIDNo);
        }
        if (address != null) {
            addressTextView.setText(address);
        }
        if (postalCode != null) {
            postalcodeTextView.setText(postalCode);
        }
        if (height > 0) {
            heightTextView.setText(String.valueOf(height));
        }
        if (bloodType != null) {
            bloodTypeTextView.setText(bloodType);
        }
    }

    public void initializeComponents(View rootView) {
        userNameTextView = (TextView) rootView.findViewById(R.id.userNameViewText);
        userDOBTextView = (TextView) rootView.findViewById(R.id.userDOBViewText);
        userIDNoTextView = (TextView) rootView.findViewById(R.id.userIDNoViewText);
        addressTextView = (TextView) rootView.findViewById(R.id.addressViewText);
        postalcodeTextView = (TextView) rootView.findViewById(R.id.postalCodeViewText);
        heightTextView = (TextView) rootView.findViewById(R.id.heightViewText);
        bloodTypeTextView = (TextView) rootView.findViewById(R.id.bloodTypeViewText);

    }


}
