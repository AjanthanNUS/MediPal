package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.DataModel;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.AppTourAdapter;

public class AppTourActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_tour);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        List<DataModel> imageItemList = getDataList();

        AppTourAdapter appTourAdapter = new AppTourAdapter(this, imageItemList);

        viewPager.setAdapter(appTourAdapter);
    }

    public List<DataModel> getDataList() {
        List<DataModel> itemList = new ArrayList<>();

        int[] imageIds = new int[]{
                R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6};

        String[] titles = new String[] {
                "Home screen", "Medicine list", "Medicine Entry", "Personal Bio", "Test 5", "Test 6"
        };

        int count  = imageIds.length;
        for (int i = 0; i < count; i++) {
            itemList.add(new DataModel(imageIds[i], titles[i]));
        }

        return itemList;
    }
}
