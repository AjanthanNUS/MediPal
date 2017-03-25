package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Measurement;
import sg.nus.iss.mtech.ptsix.medipal.presentation.viewholder.BloodPressureViewHolder;

public class BloodPressureAdapter extends RecyclerView.Adapter<BloodPressureViewHolder> {
    private Context context;
    private List<Measurement> mMeasurements;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constant.DATE_FORMAT);


    public BloodPressureAdapter(Context context, List<Measurement> measurements) {
        this.context = context;
        this.mMeasurements = measurements;
    }

    @Override
    public BloodPressureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_blood_pressure_list_row, parent, false);
        return new BloodPressureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BloodPressureViewHolder holder, final int position) {
        final Measurement measurement = mMeasurements.get(position);
        holder.tvSystolic.setText (measurement.getEventSystolic() + "");
        holder.tvDiastolic.setText(measurement.getEventDiastolic() + "");
        holder.tvDate.setText     (dateFormatter.format(measurement.getEventMeasureOn()));
        holder.tvStatus.setText   (CommonUtil.getBPStatus(measurement.getEventSystolic(), measurement.getEventDiastolic()));

    }

    @Override
    public int getItemCount() {
        return mMeasurements.size();
    }

    public void updateDataSet(List<Measurement> measurementList) {
        this.mMeasurements.clear();
        this.mMeasurements.addAll(measurementList);
        notifyDataSetChanged();

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
