package sg.nus.iss.mtech.ptsix.medipal.presentation.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sg.nus.iss.mtech.ptsix.medipal.R;

/**
 * Created by win on 15/3/17.
 */

public class AppointmentViewHolder extends RecyclerView.ViewHolder {

    public TextView tvLocation;
    public TextView tv_date_time;
    public TextView tvDescription;
    public Button btnDelete;


    public AppointmentViewHolder(View view) {
        super(view);
        this.tvDescription = (TextView) view.findViewById(R.id.tv_app_description);
        this.tvLocation = (TextView) view.findViewById(R.id.tv_app_location);
        this.tv_date_time = (TextView) view.findViewById(R.id.tv_app_date_time);
        this.btnDelete = (Button) view.findViewById(R.id.btn_app_delete);
    }


}
