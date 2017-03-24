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
    private MeasurementDao measurementDao;

    public MeasurementService(Context context) {
        this.context = context;
    }

    public long saveMeasurement(Measurement measurement) {
        long result = 0;
        measurementDao = new MeasurementDao(context);
        measurementDao.open();
        try {
            result = measurementDao.save(measurement);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            measurementDao.close();
        }
        return result;

    }

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


        public ArrayList<Measurement> loadMeasurements(int amount) {
            measurementDao = new MeasurementDao(context);
            ArrayList<Measurement> measurements =null;

            measurementDao.open();
            if(amount==0){
                try {
                    measurements =  measurementDao.getMeasurements();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    measurements = measurementDao.getMeasurements(amount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            measurementDao.close();
            return measurements;
        }


    public void DeleteMeasurement(Measurement measurement) {
        measurementDao = new MeasurementDao(context);
        measurementDao.open();
        try {
            measurementDao.delete(measurement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        measurementDao.close();
    }
}
