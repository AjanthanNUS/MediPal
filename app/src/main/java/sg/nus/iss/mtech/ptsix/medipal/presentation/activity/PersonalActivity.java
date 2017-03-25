package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import sg.nus.iss.mtech.ptsix.medipal.MainActivity;
import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.PersonalBioEditFragment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.PersonalBioViewFragment;

public class PersonalActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        PersonalBioViewFragment personalBioViewFragment = new PersonalBioViewFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container_frame, personalBioViewFragment, personalBioViewFragment.getTag()).commit();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = intent = new Intent(PersonalActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
