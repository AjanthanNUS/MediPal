package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.MeasurementDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Measurement;

public class MeasurementService {

    private static final String TAG = MeasurementService.class.getSimpleName();
    private Context context;

    public MeasurementService(Context context) {
        this.context = context;
    }
/*
    public Measurement saveMeasurement(Measurement measurement) {
        MeasurementDao measurementDao = new MeasurementDao(context);
        long result = 0;
        measurementDao.open();

        try {
            if (measurement instanceof BloodPressure) {
                measurementDao.saveBloodPressure((BloodPressure) measurement);
            }
        } catch (Exception e) {

        } finally {
            measurementDao.close();
        }
        return measurement;
    }
    */

    public List<Measurement> getBloodPressures(Date fromDate, Date toDate) {
        List<Measurement> ret = new ArrayList<>();
        MeasurementDao measurementDao = new MeasurementDao(context);

        try {
            ret = measurementDao.getMeasurementsReport(fromDate, toDate);

        } catch (Exception e) {
            Log.w(TAG, e.toString());
        } finally {
            measurementDao.close();
        }

        return ret;
    }

    public List<Measurement> getWeights(Date fromDate, Date toDate) {
        List<Measurement> ret = new ArrayList<>();
        MeasurementDao measurementDao = new MeasurementDao(context);

        try {
            ret = measurementDao.getWeightReport(fromDate, toDate);

        } catch (Exception e) {
            Log.w(TAG, e.toString());
        } finally {
            measurementDao.close();
        }

        return ret;
    }


}
