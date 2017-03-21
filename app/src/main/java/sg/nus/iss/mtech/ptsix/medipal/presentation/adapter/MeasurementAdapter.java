package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.asynctask.MeasurementDeleteAsyncTask;
import sg.nus.iss.mtech.ptsix.medipal.business.asynctask.MeasurementLoadAsyncTask;
import sg.nus.iss.mtech.ptsix.medipal.business.asynctask.MeasurementSaveAsyncTask;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Measurement;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.CategoriesActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MeasurementActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.MeasurementListFragment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.viewholder.MeasurementViewHolder;
/**
 * Created by WONG_CH on 12-Mar-17.
 */

public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementViewHolder> {

    private List<Measurement> measurementList;
    private Context mContext;
    private Button btnDel;
    private View itemView;
    public MeasurementAdapter(List<Measurement> categoriesList, Context context) {
        this.measurementList = categoriesList;
        this.mContext = context;
    }

    @Override
    public MeasurementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_measurement_list_row, parent, false);
        return new MeasurementViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MeasurementViewHolder holder, final int position) {
        final Measurement measurement = measurementList.get(position);

        Date date = measurement.getEventMeasureOn();
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        String date_string=sdf.format(date.getTime());

        holder.MeaID.setText("No " + measurement.getId() + ",");
        holder.MeaSystolic.setText("Systolic "+String.valueOf(measurement.getEventSystolic())+" mm Hg,");
        holder.MeaDiastolic.setText("Diastolic "+String.valueOf(measurement.getEventDiastolic())+" mm Hg,");
        holder.MeaPulse.setText("Pulse "+String.valueOf(measurement.getEventPulse())+" BPM,");
        holder.MeaTemperature.setText("Temp "+String.valueOf(measurement.getEventTemperature())+" C,");
        holder.MeaWeight.setText("Weight "+String.valueOf(measurement.getEventWeight())+ " Kg");
        holder.MeaMeasureOn.setText("Measured on "+date_string);
        holder.MeaComment.setText("-- "+String.valueOf(measurement.getComment()));
        // Handle edit button
        holder.btnDel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mContext instanceof MeasurementActivity){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                    builder1.setMessage("Are you sure to delete ?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    MeasurementDeleteAsyncTask measurementDeleteAsyncTask = new MeasurementDeleteAsyncTask(mContext);
                                    measurementDeleteAsyncTask.execute(measurement);
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return measurementList.size();
    }
}

