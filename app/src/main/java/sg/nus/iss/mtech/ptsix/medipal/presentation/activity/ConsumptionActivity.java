package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Consumption;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.ConsumptionListFragment;

/**
 * @author Ajanthan
 */
public class ConsumptionActivity extends AppCompatActivity implements ConsumptionListFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.consumptionFragmentHolder, ConsumptionListFragment.newInstance(1));
        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(Consumption consumption) {
        Toast.makeText(this, "List fragment interaction", Toast.LENGTH_SHORT);
    }
}
