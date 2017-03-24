package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.asynctask.AppointmentDeleteAsysncTask;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppointmentActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.presentation.viewholder.AppointmentViewHolder;

/**
 * Created by win on 15/3/17.
 */

public class AppointmentRecyclerViewAdapter extends RecyclerView.Adapter<AppointmentViewHolder> {
    private List<Appointment> mAppointments;
    private int mExpandedPosition = -1;
    private Context mContext;
    private SimpleDateFormat dbDateFormatter = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());

    public AppointmentRecyclerViewAdapter(List<Appointment> appointments, Context context) {
        this.mAppointments = appointments;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return mAppointments.size();
    }

    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_appointment_list_row, parent, false);
        return new AppointmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AppointmentViewHolder holder, final int position) {
        final Appointment appointment = mAppointments.get(position);
        holder.tvDescription.setText(appointment.getDescription());
        holder.tvLocation.setText(appointment.getLocation());
        holder.tv_date_time.setText(CommonUtil.getFriendlyDayString(mContext, appointment.getAppointmentDate().getTime()));

        holder.btnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show Alert and call delete
                new AlertDialog.Builder(mContext)
                        .setTitle("appointment Delete")
                        .setMessage("Do you want to delete?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                AppointmentDeleteAsysncTask deleteAsysncTask = new AppointmentDeleteAsysncTask(mContext);
                                deleteAsysncTask.execute(appointment);
                                mAppointments.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(mContext, "appointment deleted successfully.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();

            }
        });


        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof AppointmentActivity) {
                    ((AppointmentActivity) mContext).switchTab(Constant.APPOINTMENT_TAB_ADD_INDEX, appointment.getId());
                }
            }
        });

    }

    public void updateDataSet(List<Appointment> appointmentList) {
        this.mAppointments.clear();
        this.mAppointments.addAll(appointmentList);
        notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

}
