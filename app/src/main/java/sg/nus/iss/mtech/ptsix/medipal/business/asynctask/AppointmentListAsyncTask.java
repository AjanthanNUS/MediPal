package sg.nus.iss.mtech.ptsix.medipal.business.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import sg.nus.iss.mtech.ptsix.medipal.business.services.AppointmentService;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;

public class AppointmentListAsyncTask extends AsyncTask<Appointment, Void, ArrayList<Appointment>> {
    private AppointmentService appointmentService;

    public AppointmentListAsyncTask(Context context) {
        appointmentService = new AppointmentService(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Appointment> doInBackground(Appointment... params) {
        return appointmentService.getAppointments();
    }
}
