package sg.nus.iss.mtech.ptsix.medipal.business.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import sg.nus.iss.mtech.ptsix.medipal.business.services.AppointmentService;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;

public class AppointmentUpdateAsyncTask extends AsyncTask<Appointment, Void, Long> {
    private AppointmentService appointmentService;

    public AppointmentUpdateAsyncTask(Context context) {
        appointmentService = new AppointmentService(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Long aLong) {
        super.onCancelled(aLong);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Long doInBackground(Appointment... params) {
        return appointmentService.updateAppointment(params[0]);
    }
}
