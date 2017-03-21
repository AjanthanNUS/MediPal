package sg.nus.iss.mtech.ptsix.medipal.business.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import sg.nus.iss.mtech.ptsix.medipal.business.services.AppointmentService;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;

public class AppointmentGetAsyncTask extends AsyncTask<Integer, Void, Appointment> {

    private AppointmentService appointmentService;

    public AppointmentGetAsyncTask(Context context) {
        appointmentService = new AppointmentService(context);
    }

    @Override
    protected Appointment doInBackground(Integer... params) {
        return appointmentService.getAppointment(params[0]);
    }

    @Override
    protected void onPostExecute(Appointment appointment) {
        super.onPostExecute(appointment);
    }
}
