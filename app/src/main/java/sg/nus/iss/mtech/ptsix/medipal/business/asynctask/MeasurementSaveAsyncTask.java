package sg.nus.iss.mtech.ptsix.medipal.business.asynctask;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.MeasurementDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Measurement;

public class MeasurementSaveAsyncTask extends AsyncTask<Measurement, Void, Void> {
    private final WeakReference<Activity> activityWeakRef;
    private MeasurementDao measurementDao;

    public MeasurementSaveAsyncTask(Activity context) {
        this.activityWeakRef = new WeakReference<Activity>(context);
        measurementDao = new MeasurementDao(context);
    }

    @Override
    protected Void doInBackground(Measurement... arg0) {

        measurementDao.save(arg0[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void measurement) {

    }
}

