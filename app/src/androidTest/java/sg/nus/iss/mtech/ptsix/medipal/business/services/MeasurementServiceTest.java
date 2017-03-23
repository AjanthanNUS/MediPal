package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.business.asynctask.MeasurementSaveAsyncTask;
import sg.nus.iss.mtech.ptsix.medipal.common.exception.MeasurementExistException;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.MeasurementDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Measurement;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Created by win on 5/3/17.
 */

@RunWith(AndroidJUnit4.class)
public class MeasurementServiceTest {
    //  private static final SimpleDateFormat formatter = new SimpleDateFormat("d-MMM-yyyy H:mm", Locale.ENGLISH);
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();

        MeasurementDao measurementDao = new MeasurementDao(context);
        measurementDao.open();
        measurementDao.close();

    }

    @Test
    public void AddMeasurement() throws Exception {

        Measurement measurement = new Measurement();
        MeasurementService measurementService = new MeasurementService(context);

        Date date=new Date();
        measurement.setEventMeasureOn(date);
        measurement.setEventWeight(50);
        measurement.setEventTemperature(53.2f);
        measurement.setEventPulse(100);
        measurement.setEventDiastolic(120);
        measurement.setEventSystolic(130);
        measurement.setComment("Testing");

        measurementService.saveMeasurement(measurement);

        ArrayList<Measurement> Array_MeasurementDB = measurementService.loadMeasurements(0);

        int size = Array_MeasurementDB.size();
        Measurement measurementdb = Array_MeasurementDB.get(size-1);
        assertEquals(measurement.getEventDiastolic(), measurementdb.getEventDiastolic());

        Date date_measurement = measurement.getEventMeasureOn();
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        String date_string=sdf.format(date_measurement.getTime());

        Date date_measurementdb = measurementdb.getEventMeasureOn();
        SimpleDateFormat sdf_db=new SimpleDateFormat("dd/MM/yyyy");
        String date_string_db=sdf.format(date_measurementdb.getTime());

        assertEquals(date_string, date_string_db);
        assertEquals(measurement.getEventSystolic(), measurementdb.getEventSystolic());
        assertEquals(measurement.getEventPulse(), measurementdb.getEventPulse());
        assertEquals(measurement.getEventWeight(), measurementdb.getEventWeight());
        assertEquals(measurement.getEventTemperature(), measurementdb.getEventTemperature());
        assertEquals(measurement.getComment(), measurementdb.getComment());
        measurementService.DeleteMeasurement(measurementdb);

    }

    @Test
    public void deleteMeasurement() throws Exception {

        Measurement measurement = new Measurement();
        MeasurementService measurementService = new MeasurementService(context);

        measurementService.saveMeasurement(measurement);

        ArrayList<Measurement> Array_MeasurementDB = measurementService.loadMeasurements(0);
        int size = Array_MeasurementDB.size();

        measurementService.DeleteMeasurement(Array_MeasurementDB.get(size-1));

        ArrayList<Measurement> Array_MeasurementDB2 = measurementService.loadMeasurements(0);
        int size2 = Array_MeasurementDB2.size();

        assertEquals((size-1), size2);

    }

}