package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.MainActivity;
import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.DataModel;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppTourActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.PersonalActivity;

/**
 * Created by Praba on 21/3/2017.
 */

public class AppTourAdapter extends PagerAdapter {

    private Context context;
    private List<DataModel> itemList;
    private LayoutInflater inflater;
    private ImageView imageView;
    private TextView textView;

    public AppTourAdapter(Context context, List<DataModel> itemList) {
        this.context = context;
        this.itemList = itemList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        View itemView = inflater.inflate(R.layout.viewpager_item, container, false);
        ((ViewPager) container).addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position + 1 == itemList.size()) {
                    Intent intent = null;
                    if (isExistingUser()) {
                        intent = new Intent(context, MainActivity.class);
                    } else  {
                        intent = new Intent(context, PersonalActivity.class);
                    }
                    context.startActivity(intent);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        imageView = (ImageView) itemView.findViewById(R.id.imageItem);
        textView = (TextView) itemView.findViewById(R.id.textViewItem);

        DataModel dataModel = itemList.get(position);

        imageView.setImageResource(dataModel.imageId);
        textView.setText(dataModel.title);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(itemView);

        return itemView;
    }


    private boolean isExistingUser() {

        boolean userCreated = false;

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName() + Constant.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        userCreated = sharedPreferences.getBoolean(Constant.USER_CREATED_SETTINGS_LABEL, false);

        return userCreated;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}
