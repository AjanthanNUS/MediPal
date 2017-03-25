package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.MainActivity;
import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.DataModel;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.AppTourAdapter;

public class AppTourActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<DataModel> imageItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_tour);

        List<DataModel> imageItemList = getDataList();

        viewPager = (ViewPager) findViewById(R.id.view_app_tour_pager);
        AppTourAdapter appTourAdapter = new AppTourAdapter(this, imageItemList);
        viewPager.setAdapter(appTourAdapter);

    }

    public List<DataModel> getDataList() {
        List<DataModel> itemList = new ArrayList<>();

        int[] imageIds = new int[]{
                R.drawable.screen_app, R.drawable.screen_personal, R.drawable.screen_health, R.drawable.screen_measurement, R.drawable.screen_category, R.drawable.screen_medicine, R.drawable.screen_appointment, R.drawable.screen_consumption_list, R.drawable.screen_consumption_add, R.drawable.screen_ice, R.drawable.screen_settings, R.drawable.screen_report
        };

        String[] titles = new String[]{
                "Home", "Personal Bio", "Health Bio", "Measurement", "Category", "Medicine", "Appointment", "Consumption", "Consumption Add", "ICE", "Settings", "Report"
        };

        for (int i = 0; i < imageIds.length; i++) {
            itemList.add(new DataModel(imageIds[i], titles[i]));
        }

        return itemList;
    }
}
