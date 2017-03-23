package sg.nus.iss.mtech.ptsix.medipal.business.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import sg.nus.iss.mtech.ptsix.medipal.business.services.MeasurementService;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.MeasurementDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Measurement;

public class MeasurementSaveAsyncTask extends AsyncTask<Measurement, Void, Void> {
    private MeasurementDao measurementDao;
    private MeasurementService measurementService;
    private Context mContext;

    public MeasurementSaveAsyncTask(Context context) {
        measurementService = new MeasurementService(context);
        mContext = context;
    }

    @Override
    protected Void doInBackground(Measurement... arg0) {

        measurementService.saveMeasurement(arg0[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void measurement) {

    }
}

