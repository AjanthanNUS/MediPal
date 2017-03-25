package sg.nus.iss.mtech.ptsix.medipal.presentation.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sg.nus.iss.mtech.ptsix.medipal.R;

public class BloodPressureViewHolder extends RecyclerView.ViewHolder {
    public TextView tvSystolic;
    public TextView tvDiastolic;
    public TextView tvDate;
    public TextView tvStatus;


    public BloodPressureViewHolder(View view) {
        super(view);
        this.tvSystolic = (TextView) view.findViewById(R.id.tv_systolic);
        this.tvDiastolic = (TextView) view.findViewById(R.id.tv_distolic);
        this.tvStatus = (TextView) view.findViewById(R.id.tv_bpstatus);
        this.tvDate = (TextView) view.findViewById(R.id.tv_measure_date);
    }
}
