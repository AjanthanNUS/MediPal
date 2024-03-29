package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Measurement;

public class MeasurementDao extends DBDAO {

    private static final String WHERE_ID_EQUALS = DatabaseHelper.MEAS_ID + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

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
        values.put(DatabaseHelper.MEAS_MEASURED_ON, formatter.format(measurement.getEventMeasureOn()));
        values.put(DatabaseHelper.MEAS_MEASURED_COMMENT, measurement.getComment());
        return database.insert(DatabaseHelper.MEAS_TABLE, null, values);
    }

    /*
    public long saveBloodPressure(BloodPressure bloodPressure) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MEAS_SYSTOLIC, bloodPressure.getEventSystolic());
        values.put(DatabaseHelper.MEAS_DIASTOLIC, bloodPressure.getEventDiastolic());

        return database.insert(DatabaseHelper.MEAS_TABLE, null, values);
    }
    */

    public long update(Measurement measurement) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MEAS_SYSTOLIC, measurement.getEventSystolic());
        values.put(DatabaseHelper.MEAS_DIASTOLIC, measurement.getEventDiastolic());
        values.put(DatabaseHelper.MEAS_PULSE, measurement.getEventPulse());
        values.put(DatabaseHelper.MEAS_TEMPERATURE, measurement.getEventTemperature());
        values.put(DatabaseHelper.MEAS_WEIGHT, measurement.getEventWeight());
        values.put(DatabaseHelper.MEAS_MEASURED_ON, formatter.format(measurement.getEventMeasureOn()));
        values.put(DatabaseHelper.MEAS_MEASURED_COMMENT, measurement.getComment());

        long result = database.update(DatabaseHelper.MEAS_TABLE, values,
                WHERE_ID_EQUALS,
                new String[]{String.valueOf(measurement.getId())});
        Log.d("Update Result:", "=" + result);
        return result;

    }

    public int delete(Measurement measurement) {
        return database.delete(DatabaseHelper.MEAS_TABLE, WHERE_ID_EQUALS,
                new String[]{measurement.getId() + ""});
    }

    public ArrayList<Measurement> getMeasurements() {
        ArrayList<Measurement> measurements = new ArrayList<Measurement>();

        Cursor cursor = database.query(DatabaseHelper.MEAS_TABLE,
                new String[]{DatabaseHelper.MEAS_ID,
                        DatabaseHelper.MEAS_SYSTOLIC,
                        DatabaseHelper.MEAS_DIASTOLIC,
                        DatabaseHelper.MEAS_PULSE,
                        DatabaseHelper.MEAS_TEMPERATURE,
                        DatabaseHelper.MEAS_WEIGHT,
                        DatabaseHelper.MEAS_MEASURED_ON,
                        DatabaseHelper.MEAS_MEASURED_COMMENT,
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
            measurement.setComment(cursor.getString(7));
            measurements.add(measurement);
        }
        return measurements;
    }

    public ArrayList<Measurement> getMeasurements(int amount) {
        ArrayList<Measurement> measurements = new ArrayList<Measurement>();
        int i = 0;
        Cursor cursor = database.query(DatabaseHelper.MEAS_TABLE,
                new String[]{DatabaseHelper.MEAS_ID,
                        DatabaseHelper.MEAS_SYSTOLIC,
                        DatabaseHelper.MEAS_DIASTOLIC,
                        DatabaseHelper.MEAS_PULSE,
                        DatabaseHelper.MEAS_TEMPERATURE,
                        DatabaseHelper.MEAS_WEIGHT,
                        DatabaseHelper.MEAS_MEASURED_ON,
                        DatabaseHelper.MEAS_MEASURED_COMMENT,
                }, null, null, null,
                null, null);

        int count = cursor.getCount();
        if (count > amount) {
            cursor.moveToPosition(count - amount - 1);
        }
        while (cursor.moveToNext()) {
            i++;
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
            measurement.setComment(cursor.getString(7));
            measurements.add(measurement);
        }
        return measurements;
    }

    public Measurement getMeasurement(long id) {
        Measurement measurement = null;

        String sql = "SELECT * FROM " + DatabaseHelper.MEAS_TABLE
                + " WHERE " + DatabaseHelper.MEAS_ID + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[]{id + ""});

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
            measurement.setComment(cursor.getString(7));
        }
        return measurement;
    }

    public ArrayList<Measurement> getMeasurementsReport(Date fromDate, Date toDate)
    {
        ArrayList<Measurement> measurements = new ArrayList<Measurement>();

        String sql = "SELECT * FROM " + DatabaseHelper.MEAS_TABLE
                   + " WHERE " + DatabaseHelper.MEAS_MEASURED_ON + " >= ? AND "
                   + DatabaseHelper.MEAS_MEASURED_ON + " <= ? ";

        Cursor cursor = database.rawQuery(sql, new String [] {formatter.format(fromDate), formatter.format(toDate)});


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
            measurement.setComment(cursor.getString(7));
            measurements.add(measurement);
        }
        return measurements;
    }

    public ArrayList<Measurement> getWeightReport (Date fromDate, Date toDate)
    {
        ArrayList<Measurement> measurements = new ArrayList<Measurement>();

        String sql = "SELECT * FROM " + DatabaseHelper.MEAS_TABLE
                + " WHERE " + DatabaseHelper.MEAS_MEASURED_ON + " >= ? AND "
                + DatabaseHelper.MEAS_MEASURED_ON + " <= ? AND "
                + DatabaseHelper.MEAS_WEIGHT + " <> 0" ;


        Cursor cursor = database.rawQuery(sql, new String [] {formatter.format(fromDate), formatter.format(toDate)});


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
            measurement.setComment(cursor.getString(7));
            measurements.add(measurement);
        }
        return measurements;
    }
}


