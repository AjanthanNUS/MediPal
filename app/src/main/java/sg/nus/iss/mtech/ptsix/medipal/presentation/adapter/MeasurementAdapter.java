package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Measurement;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MeasurementActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.viewholder.MeasurementViewHolder;
/**
 * Created by WONG_CH on 12-Mar-17.
 */

public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementViewHolder> {

    private List<Measurement> measurementList;
    private int mExpandedPosition = -1;
    private Context mContext;

    public MeasurementAdapter(List<Measurement> categoriesList, Context context) {
        this.measurementList = categoriesList;
        this.mContext = context;
    }

    @Override
    public MeasurementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_measurement_list_row, parent, false);
        return new MeasurementViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MeasurementViewHolder holder, final int position) {
        final Measurement measurement = measurementList.get(position);

        holder.MeaID.setText("ID " + measurement.getId() + "");
        holder.MeaSystolic.setText("Systolic "+String.valueOf(measurement.getEventSystolic())+" mm Hg");
        holder.MeaDiastolic.setText("Diastolic "+String.valueOf(measurement.getEventDiastolic())+" mm Hg");
        holder.MeaPulse.setText("Pulse "+String.valueOf(measurement.getEventPulse())+" BPM");
        holder.MeaTemperature.setText("Temp "+String.valueOf(measurement.getEventTemperature())+" C");
        holder.MeaWeight.setText("Weight "+String.valueOf(measurement.getEventWeight())+ " Kg");
        holder.MeaMeasureOn.setText(String.valueOf(measurement.getEventMeasureOn()));
        holder.MeaComment.setText(String.valueOf(measurement.getComment()));
        // Handle edit button
        /*
        holder.btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mContext instanceof CategoriesActivity){
                    ((CategoriesActivity)mContext).switchTab(1, category.getId());
                }
            }
        });
        */

        // Expandable part
        /*
        final boolean isExpanded = position == mExpandedPosition;
        holder.description.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? - 1: position;
                notifyDataSetChanged();
            }
        });
        */

    }

    @Override
    public int getItemCount() {
        return measurementList.size();
    }
}

