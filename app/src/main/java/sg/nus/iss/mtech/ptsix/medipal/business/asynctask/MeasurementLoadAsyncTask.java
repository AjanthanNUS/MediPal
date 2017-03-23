package sg.nus.iss.mtech.ptsix.medipal.business.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.business.services.MeasurementService;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.MeasurementDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Measurement;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.MeasurementAdapter;

public class MeasurementLoadAsyncTask extends AsyncTask<Integer, Void, ArrayList<Measurement>> {

    private RecyclerView recyclerView;
    private MeasurementAdapter mAdapter;
    private Context mContext;

    private MeasurementService measurementService;

    public MeasurementLoadAsyncTask(Context context, View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        measurementService = new MeasurementService(context);
        mContext = context;
    }

    @Override
    protected ArrayList<Measurement> doInBackground(Integer... arg0) {

        return measurementService.loadMeasurements(arg0[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Measurement> measurements) {
        mAdapter = new MeasurementAdapter(measurements, mContext);
        recyclerView.setAdapter(mAdapter);
    }
}

