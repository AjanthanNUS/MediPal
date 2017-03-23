package sg.nus.iss.mtech.ptsix.medipal.business.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import sg.nus.iss.mtech.ptsix.medipal.business.services.MeasurementService;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Measurement;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MeasurementActivity;


public class MeasurementDeleteAsyncTask extends AsyncTask<Measurement, Void, Void> {

    private MeasurementService measurementService;
    private Context mContext;

    public MeasurementDeleteAsyncTask(Context context) {
        measurementService = new MeasurementService(context);
        mContext = context;
    }

    @Override
    protected Void doInBackground(Measurement... arg0) {

        measurementService.DeleteMeasurement(arg0[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void measurement) {
        ((MeasurementActivity)mContext).recreate();
    }
}
