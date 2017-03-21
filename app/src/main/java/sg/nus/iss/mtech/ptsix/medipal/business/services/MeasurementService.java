package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.content.Context;

public class MeasurementService {
    private Context context;

    public MeasurementService(Context context) {
        this.context = context;;
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


}
