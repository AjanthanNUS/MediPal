package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Measurement;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.PersonalBio;
import sg.nus.iss.mtech.ptsix.medipal.presentation.viewholder.WeightViewHolder;

public class WeightAdapter extends RecyclerView.Adapter<WeightViewHolder> {

    private Context context;
    private List<Measurement> mMeasurements;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constant.DATE_FORMAT);
    private PersonalBio mPersonalBio;


    public WeightAdapter(Context context, List<Measurement> measurements, PersonalBio personalBio) {
        this.context = context;
        this.mMeasurements = measurements;
        this.mPersonalBio = personalBio;

    }

    @Override
    public WeightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_weight_list_row, parent, false);
        return new WeightViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WeightViewHolder holder, final int position) {
        final Measurement measurement = mMeasurements.get(position);
        StringBuilder strWeight = new StringBuilder();
//        strWeight.append(CommonUtil.padRight("Weight", 14));
        strWeight.append(measurement.getEventWeight()).append(" (kg)");
        holder.tvWeight.setText(strWeight.toString());

        if (mPersonalBio != null && mPersonalBio.getHeight() != 0) {
            double bmi = CommonUtil.getBMI(measurement.getEventWeight(), mPersonalBio.getHeight());

            StringBuilder strHeight = new StringBuilder();
            strHeight.append(mPersonalBio.getHeight()).append(" (cm)");

//            strHeight.append(CommonUtil.padRight("Height", 14));
//            strHeight.append(": ").append(mPersonalBio.getHeight()).append(" (cm)");
            holder.tvHeight.setText(strHeight.toString());

            StringBuilder strBMI = new StringBuilder();
            strBMI.append(CommonUtil.format2Decimals(bmi));
//            strBMI.append(CommonUtil.padRight("BMI", 14));
//            strBMI.append(": ").append(CommonUtil.format2Decimals(bmi));
            holder.tvBMI.setText(strBMI.toString());

            StringBuilder strBMIStatus = new StringBuilder();
            strBMIStatus.append(CommonUtil.getBMIStatus(bmi));
//            strBMIStatus.append(CommonUtil.padRight("Status", 14));
//            strBMIStatus.append(": ").append(CommonUtil.getBMIStatus(bmi));
            Log.w("INFO", "BMI Status " + strBMIStatus.toString());
            holder.tvBMIStatus.setText(strBMIStatus.toString());
        }

        holder.tvDate.setText  (dateFormatter.format(measurement.getEventMeasureOn()));


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
