package sg.nus.iss.mtech.ptsix.medipal.business.asynctask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.MeasurementDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Measurement;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.MeasurementAdapter;

public class MeasurementLoadAsyncTask extends AsyncTask<Integer, Void, ArrayList<Measurement>> {
    private final WeakReference<Activity> activityWeakRef;
    private MeasurementDao measurementDao;
    private RecyclerView recyclerView;
    private MeasurementAdapter mAdapter;

    private Context mContext;
    private View rootView;

    public MeasurementLoadAsyncTask(Activity context, View rootView) {
        this.activityWeakRef = new WeakReference<Activity>(context);
        measurementDao = new MeasurementDao(context);
        this.rootView = rootView;
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
    }

    @Override
    protected ArrayList<Measurement> doInBackground(Integer... arg0) {
        ArrayList<Measurement> measurements;
        int arg = arg0[0];
        if (arg == 0) {
            measurements = measurementDao.getMeasurements();
        } else {
            measurements = measurementDao.getMeasurements(arg);
        }
        return measurements;
    }

    @Override
    protected void onPostExecute(ArrayList<Measurement> measurements) {
        mAdapter = new MeasurementAdapter(measurements, this.mContext);
        recyclerView.setAdapter(mAdapter);
    }
}

