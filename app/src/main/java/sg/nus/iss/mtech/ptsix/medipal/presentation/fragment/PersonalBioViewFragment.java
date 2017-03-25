package sg.nus.iss.mtech.ptsix.medipal.presentation.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.PersonalBioDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.PersonalBio;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalBioViewFragment extends Fragment {

    private PersonalBioEditFragment personalBioEditFragment = null;
    private FragmentManager manager = null;
    private TextView id = null;
    private TextView userNameTextView = null;
    private TextView userDOBTextView = null;
    private TextView userIDNoTextView = null;
    private TextView addressTextView = null;
    private TextView postalcodeTextView = null;
    private TextView heightTextView = null;
    private TextView bloodTypeTextView = null;
    private PersonalBio personalBio = null;
    private PersonalBioDao personalBioDao = null;
    private static final String TITLE = "Personal Bio";
    private static final String USERID = "UserId";
    private static final int USERID_DEFAULT = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_bio_view, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabUpdatePersonalBio);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPersonalBioEdit();
//                Snackbar.make(view, "No create activity found...", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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
      //  String address =   String.valueOf(calculateUserAge(userDOB));
        String address =   personalBio.getAddress();
        String postalCode = personalBio.getPostalcode();
        int height = personalBio.getHeight();
        String bloodType = personalBio.getBloodType();

        userNameTextView.setText(personalBio.getUserName());
        if (userDOB != null) {
            userDOBTextView.setText(CommonUtil.date2ddMMMYYYY(userDOB));
        }
        //Calculate Age
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

    private static int  calculateUserAge(Date dateOfBirth) {
        int age = 0;
        if (dateOfBirth != null) {

        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(dateOfBirth);
        if (birthDate.after(today)) {
            throw new IllegalArgumentException("You don't exist yet");
        }
        int todayYear = today.get(Calendar.YEAR);
        int birthDateYear = birthDate.get(Calendar.YEAR);
        int todayDayOfYear = today.get(Calendar.DAY_OF_YEAR);
        int birthDateDayOfYear = birthDate.get(Calendar.DAY_OF_YEAR);
        int todayMonth = today.get(Calendar.MONTH);
        int birthDateMonth = birthDate.get(Calendar.MONTH);
        int todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH);
        int birthDateDayOfMonth = birthDate.get(Calendar.DAY_OF_MONTH);
        age = todayYear - birthDateYear;

        // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
        if ((birthDateDayOfYear - todayDayOfYear > 3) || (birthDateMonth > todayMonth)){
            age--;

            // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
        } else if ((birthDateMonth == todayMonth) && (birthDateDayOfMonth > todayDayOfMonth)){
            age--;
        }
        }

        return age;
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
