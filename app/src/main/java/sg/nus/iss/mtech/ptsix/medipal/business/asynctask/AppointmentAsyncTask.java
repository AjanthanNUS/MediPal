package sg.nus.iss.mtech.ptsix.medipal.business.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import sg.nus.iss.mtech.ptsix.medipal.business.services.AppointmentService;
import sg.nus.iss.mtech.ptsix.medipal.common.exception.AppointmentExistException;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;

/**
 * Created by win on 5/3/17.
 */

public class AppointmentAsyncTask extends AsyncTask <Appointment, Void, Long> {

    private AppointmentService appointmentService;

    public AppointmentAsyncTask(Context context) {
        appointmentService = new AppointmentService(context);


    }

    @Override
    protected Long doInBackground(Appointment... params) {
        long result = 0;
        try {
            result = appointmentService.makeAppointment(params[0]);
        } catch (AppointmentExistException e) {

        }
        return result;
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);

    }
}
