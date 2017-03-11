package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.AppointmentFragment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.AppointmentFragment.OnListFragmentInteractionListener;

public class AppointmentActivity extends AppCompatActivity implements OnListFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_containter);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new AppointmentFragment())
                    .commit();
        }



//        initializeView();
    }

    @Override
    public void onListFragmentInteraction(Appointment item) {

    }
}


