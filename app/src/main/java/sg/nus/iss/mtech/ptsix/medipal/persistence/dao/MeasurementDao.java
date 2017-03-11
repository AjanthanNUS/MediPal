package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.text.ParseException;

import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Measurement;

/**
 * Created by WongCheeVui on 3/6/2017.
 */


/*
ID              interger
Systolic        interger
Diastolic       interger
Pulse           interger
Temperature     Decimal(5,2)
Weight          interger
MeasureOn       DateTime

 */

public class MeasurementDao extends DBDAO {

    private static final String WHERE_ID_EQUALS = DatabaseHelper.MEAS_ID + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public MeasurementDao(Context context) {
        super(context);
    }

    public long save(Measurement measurement) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MEAS_SYSTOLIC, measurement.getEventSystolic());
        values.put(DatabaseHelper.MEAS_DIASTOLIC, measurement.getEventDiastolic());
        values.put(DatabaseHelper.MEAS_PULSE, measurement.getEventPulse());
        values.put(DatabaseHelper.MEAS_TEMPERATURE, measurement.getEventTemperature());
        values.put(DatabaseHelper.MEAS_WEIGHT, measurement.getEventWeight());
        values.put(DatabaseHelper.MEAS_MEASURED_ON,  formatter.format(measurement.getEventMeasureOn()));
        return database.insert(DatabaseHelper.MEAS_TABLE, null, values);
    }

    public long update(Measurement measurement) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MEAS_SYSTOLIC, measurement.getEventSystolic());
        values.put(DatabaseHelper.MEAS_DIASTOLIC, measurement.getEventDiastolic());
        values.put(DatabaseHelper.MEAS_PULSE, measurement.getEventPulse());
        values.put(DatabaseHelper.MEAS_TEMPERATURE, measurement.getEventTemperature());
        values.put(DatabaseHelper.MEAS_WEIGHT, measurement.getEventWeight());
        values.put(DatabaseHelper.MEAS_MEASURED_ON,  formatter.format(measurement.getEventMeasureOn()));


        long result = database.update(DatabaseHelper.MEAS_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(measurement.getId()) });
        Log.d("Update Result:", "=" + result);
        return result;

    }

    public int delete(Measurement measurement) {
        return database.delete(DatabaseHelper.MEAS_TABLE, WHERE_ID_EQUALS,
                new String[] { measurement.getId() + "" });
    }
    //USING query() method
    public ArrayList<Measurement> getMeasurements() {
        ArrayList<Measurement> measurements = new ArrayList<Measurement>();

        Cursor cursor = database.query(DatabaseHelper.MEAS_TABLE,
                new String[] { DatabaseHelper.MEAS_ID,
                        DatabaseHelper.MEAS_SYSTOLIC,
                        DatabaseHelper.MEAS_DIASTOLIC,
                        DatabaseHelper.MEAS_PULSE,
                        DatabaseHelper.MEAS_TEMPERATURE,
                        DatabaseHelper.MEAS_WEIGHT,
                        DatabaseHelper.MEAS_MEASURED_ON,
                }, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            Measurement measurement = new Measurement();
            measurement.setId(cursor.getInt(0));
            measurement.setEventSystolic(cursor.getInt(1));
            measurement.setEventDiastolic(cursor.getInt(2));
            measurement.setEventPulse(cursor.getInt(3));
            measurement.setEventTemperature(cursor.getFloat(4));
            measurement.setEventWeight(cursor.getInt(5));
            try {
                measurement.setEventMeasureOn(formatter.parse(cursor.getString(6)));
            } catch (ParseException e) {
                measurement.setEventMeasureOn(null);
            }
            measurements.add(measurement);
        }
        return measurements;
    }
    //Retrieves a single reminder record with the given id
    public Measurement getMeasurement(long id) {
        Measurement measurement = null;

        String sql = "SELECT * FROM " + DatabaseHelper.MEAS_TABLE
                + " WHERE " + DatabaseHelper.MEAS_ID + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            measurement = new Measurement();
            measurement.setId(cursor.getInt(0));
            measurement.setEventSystolic(cursor.getInt(1));
            measurement.setEventDiastolic(cursor.getInt(2));
            measurement.setEventPulse(cursor.getInt(3));
            measurement.setEventTemperature(cursor.getFloat(4));
            measurement.setEventWeight(cursor.getInt(5));
            try {
                measurement.setEventMeasureOn(formatter.parse(cursor.getString(6)));
            } catch (ParseException e) {
                measurement.setEventMeasureOn(null);
            }
        }
        return measurement;
    }
}


