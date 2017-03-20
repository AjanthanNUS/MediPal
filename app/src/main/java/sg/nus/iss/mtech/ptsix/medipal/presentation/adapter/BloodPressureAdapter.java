package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Meas_BloodPressure;
import sg.nus.iss.mtech.ptsix.medipal.presentation.viewholder.BloodPressureViewHolder;

/**
 * Created by win on 19/3/17.
 */

public class BloodPressureAdapter extends RecyclerView.Adapter<BloodPressureViewHolder> {
    private Context context;
    private List<Meas_BloodPressure> mBloodPressures;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constant.DATE_FORMAT);


    public BloodPressureAdapter(Context context, List<Meas_BloodPressure> bloodPressures) {
        this.context = context;
        this.mBloodPressures = bloodPressures;
    }




    @Override
    public BloodPressureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BloodPressureViewHolder holder, int position) {
        final Meas_BloodPressure bloodPressure = mBloodPressures.get(position);
        holder.tvSystolic.setText(bloodPressure.get_Systolic_reading());
        holder.tvDiastolic.setText(bloodPressure.get_Diastolic_reading());
        //holder.tvDate.setText(dateFormatter.format(bloodPressure.get));


    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
