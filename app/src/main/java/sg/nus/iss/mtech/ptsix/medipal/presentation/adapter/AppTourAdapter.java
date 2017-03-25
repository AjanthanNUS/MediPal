package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.DataModel;

/**
 * Created by Praba on 21/3/2017.
 */

public class AppTourAdapter extends PagerAdapter {

    private Context context;
    private List<DataModel> itemList;
    private LayoutInflater inflater;
    private ImageView imageView;
    private TextView textView;

    public AppTourAdapter (Context context, List<DataModel> itemList) {
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
        Log.i("APP TOUR", "Image pos -> " + position + "  item => " + (position+1));

        View itemView = inflater.inflate(R.layout.viewpager_item, container, false);

        imageView = (ImageView) itemView.findViewById(R.id.imageItem);
        textView = (TextView) itemView.findViewById(R.id.textViewItem);

        DataModel dataModel = itemList.get(position);

        imageView.setImageResource(dataModel.imageId);
        textView.setText(dataModel.title);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}
