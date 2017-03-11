package sg.nus.iss.mtech.ptsix.medipal.presentation.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.asynctask.AppointmentListAsyncTask;
import sg.nus.iss.mtech.ptsix.medipal.business.services.AppointmentService;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;

/**
 * Created by win on 6/3/17.
 */

public class AppointmentAdapter extends ArrayAdapter<Appointment> {
    private Context context;
    private List<Appointment> mAppointments;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

    public AppointmentAdapter(Context context, List <Appointment> appointments) {
        super(context, R.layout.fragment_appointment_item, appointments);
        this.context = context;
        refreshAppointments();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        AppointmentAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_appointment_item, parent, false);
            viewHolder = new AppointmentAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AppointmentAdapter.ViewHolder) convertView.getTag();
        }

        Appointment appointment = (Appointment) getItem(position);
        viewHolder.tvDescription.setText(appointment.getDescription());
        viewHolder.tvLocation.setText(appointment.getLocation());
        viewHolder.tvDate.setText(dateFormatter.format(appointment.getAppointmentDate()));


        return convertView;
    }

    public static class ViewHolder {
        public final TextView tvLocation;
        public final TextView tvDescription;
        public final TextView tvDate;

        public ViewHolder(View view) {
            this.tvDescription = (TextView) view.findViewById(R.id.list_item_appointmentdesc);
            this.tvLocation = (TextView) view.findViewById(R.id.list_item_appointmentlocation);
            this.tvDate = (TextView) view.findViewById(R.id.list_item_appointmentdate);
//            this.btnRemove = (Button) view.findViewById(R.id.btnRemove);
        }
    }

    @Nullable
    @Override
    public Appointment getItem(int position) {
        return mAppointments.get(position);
    }




    @Override
    public int getCount() {
        return mAppointments.size();
    }

    public void refreshAppointments() {
           mAppointments = new ArrayList<>();
        mAppointments.clear();
        AppointmentService appointmentService = new AppointmentService(context);
        mAppointments.addAll(appointmentService.getAppointments());


        //mAppointments.addAll(App.club.getFacilities(this.context));
//        mAppointments.addAll(generateDummyAppointments());
        notifyDataSetChanged();
    }

    public List<Appointment> generateDummyAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        AppointmentListAsyncTask appointmentListAsyncTask = new AppointmentListAsyncTask(context);
        appointmentListAsyncTask.execute();


        Date d = new Date();
        Appointment appointment1 = new Appointment("Tampines", "Go for the monthly checkup", d);
        Appointment appointment2 = new Appointment("Bedok", "Go for GP", d);
        Appointment appointment3 = new Appointment("QueensTown", "Queens Town Clincic", d);

        appointments.add(appointment1);
        appointments.add(appointment2);
        appointments.add(appointment3);
        return  appointments;
    }



}
