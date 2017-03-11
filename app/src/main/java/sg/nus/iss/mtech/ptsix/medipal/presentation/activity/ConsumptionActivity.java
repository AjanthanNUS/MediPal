package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Consumption;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.ConsumptionListFragment;

/**
 * @author Ajanthan
 */
public class ConsumptionActivity extends AppCompatActivity implements ConsumptionListFragment.OnListFragmentInteractionListener {
    private PopupWindow mPopupWindow;
    private FrameLayout consumptionFragmentHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.consumptionFragmentHolder, ConsumptionListFragment.newInstance(1));
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.consumption_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

        }

        return true;
    }

    @Override
    public void onListFragmentInteraction(Consumption consumption) {
        consumptionFragmentHolder = (FrameLayout) findViewById(R.id.consumptionFragmentHolder);
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        } else {
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
            View customView = inflater.inflate(R.layout.consumption_detail_popup, null);

            TextView medicine = (TextView) customView.findViewById(R.id.popup_medicine);
            TextView consumedOn = (TextView) customView.findViewById(R.id.popup_consumed_on);
            TextView quantity = (TextView) customView.findViewById(R.id.popup_quantity);
            TextView description = (TextView) customView.findViewById(R.id.popup_description);

            medicine.setText(consumption.getMedicine().getMedicine());
            consumedOn.setText(CommonUtil.formatCalender(consumption.getConsumedOn()));
            quantity.setText(consumption.getQuantity().toString());
            description.setText(consumption.getMedicine().getMedicine() + "Sample description");

            mPopupWindow = new PopupWindow(
                    customView,
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
            );

            if (Build.VERSION.SDK_INT >= 21) {
                mPopupWindow.setElevation(5.0f);
            }

//            ImageButton closeButton = (ImageButton) customView.findViewById(R.id.popup_close);
//
//            closeButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mPopupWindow.dismiss();
//                }
//            });

            mPopupWindow.showAtLocation(consumptionFragmentHolder, Gravity.CENTER, 0, 0);
        }


    }
}
