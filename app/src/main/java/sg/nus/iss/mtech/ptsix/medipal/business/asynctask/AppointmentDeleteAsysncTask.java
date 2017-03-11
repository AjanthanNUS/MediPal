package sg.nus.iss.mtech.ptsix.medipal.business.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import sg.nus.iss.mtech.ptsix.medipal.business.services.AppointmentService;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;

/**
 * Created by win on 7/3/17.
 */

public class AppointmentDeleteAsysncTask extends AsyncTask<Appointment, Void, Long>  {

    private AppointmentService appointmentService;

    public AppointmentDeleteAsysncTask(Context context) {
        appointmentService = new AppointmentService(context);

    }

    @Override
    protected Long doInBackground(Appointment... params) {
        return  appointmentService.deleteAppointment(params[0]);

    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
    }
}
