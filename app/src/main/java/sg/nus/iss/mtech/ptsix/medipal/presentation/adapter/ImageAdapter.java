package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import sg.nus.iss.mtech.ptsix.medipal.R;

/**
 * Created by Praba on 23/3/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        TextView textView;
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_view_item, null);
            textView = (TextView) grid.findViewById(R.id.grid_text);
            imageView = (ImageView)grid.findViewById(R.id.grid_image);
            textView.setText(imageText[position]);
            imageView.setImageResource(mThumbIds[position]);


        } else {
            grid = (View) convertView;
        }

        return grid;
    }

    String[] imageText = {
            "Medicine",
            "Consumption",
            "Appointment",
            "Measurement",
            "Category",
            "Report",
            "Personal Bio",
            "Health Bio",
            "ICE Contact",
            "Settings",
            "App Tour"

    } ;

    private Integer[] mThumbIds = {
            R.drawable.medicine, R.drawable.intake, R.drawable.appointment, R.drawable.measurement,
            R.drawable.category, R.drawable.report, R.drawable.personal_bio, R.drawable.health_bio,
            R.drawable.ice_contact, R.drawable.user_settings, R.drawable.app_tour
    };
}