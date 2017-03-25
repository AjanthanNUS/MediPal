package sg.nus.iss.mtech.ptsix.medipal.presentation.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sg.nus.iss.mtech.ptsix.medipal.R;

/**
 * Created by win on 19/3/17.
 */

public class WeightViewHolder extends RecyclerView.ViewHolder {
    public TextView tvWeight;
    public TextView tvHeight;
    public TextView tvBMI;
    public TextView tvDate;
    public TextView tvBMIStatus;


    public WeightViewHolder(View view) {
        super(view);
        this.tvWeight = (TextView) view.findViewById(R.id.tv_weight);
        this.tvHeight = (TextView) view.findViewById(R.id.tv_height);
        this.tvBMI = (TextView) view.findViewById(R.id.tv_bmi);
        this.tvDate = (TextView) view.findViewById(R.id.tv_measure_date);
        this.tvBMIStatus = (TextView) view.findViewById(R.id.tv_bmi_status);
    }
}
