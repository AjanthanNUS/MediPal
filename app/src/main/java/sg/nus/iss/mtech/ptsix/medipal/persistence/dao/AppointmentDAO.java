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

import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;

/**
 * Created by win on 5/3/17.
 */

public class AppointmentDAO extends DBDAO {
    private static final String WHERE_ID_EQUALS = DatabaseHelper.APPOINTMENT_ID_COLUMN + " =?";
    private static final String WHERE_APPOINTMENTDATE_EQUALS = DatabaseHelper.APPOINTMENT_COLUMN + " =?";
    private static final SimpleDateFormat dbDateFormatter = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
    public AppointmentDAO(Context context) {
        super(context);
    }

    public long save(Appointment appointment) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.LOCATION_COLUMN, appointment.getLocation());
        values.put(DatabaseHelper.APPOINTMENT_COLUMN, dbDateFormatter.format(appointment.getAppointmentDate()));
        values.put(DatabaseHelper.DESCRIPTION_COLUMN, appointment.getDescription());
        return database.insert(DatabaseHelper.APPOINTMENT_TABLE, null, values);
    }

    public long update(Appointment appointment) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.LOCATION_COLUMN, appointment.getLocation());
        values.put(DatabaseHelper.APPOINTMENT_COLUMN, dbDateFormatter.format(appointment.getAppointmentDate()));
        values.put(DatabaseHelper.DESCRIPTION_COLUMN, appointment.getDescription());


        long result = database.update(DatabaseHelper.APPOINTMENT_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(appointment.getId()) });
        Log.d("Update Result:", "=" + result);
        return result;
    }

    public int delete(Appointment appointment) {
        return database.delete(DatabaseHelper.APPOINTMENT_TABLE, WHERE_ID_EQUALS,
                new String[] { appointment.getId() + "" });
    }

    public ArrayList<Appointment> getAppointments() {
        ArrayList<Appointment> appointments = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelper.APPOINTMENT_TABLE,
                new String[] { DatabaseHelper.APPOINTMENT_ID_COLUMN,
                        DatabaseHelper.LOCATION_COLUMN,
                        DatabaseHelper.APPOINTMENT_COLUMN,
                        DatabaseHelper.DESCRIPTION_COLUMN
                        }, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            Appointment appointment = new Appointment();
            appointment.setId(cursor.getInt(0));
            appointment.setLocation(cursor.getString(1));

            try {
                appointment.setAppointmentDate(dbDateFormatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            appointment.setDescription(cursor.getString(3));
            appointments.add(appointment);

        }
        return appointments;
    }

    public Appointment getAppointment(int appointmentId) {
        Appointment ret = null;

        String sql = "SELECT * FROM " + DatabaseHelper.APPOINTMENT_TABLE
                + " WHERE " + DatabaseHelper.APPOINTMENT_ID_COLUMN + " = ?";


        Cursor cursor = database.rawQuery(sql, new String[] { appointmentId + "" });

        while (cursor.moveToNext()) {
            ret = new Appointment();
            ret.setId(cursor.getInt(0));
            ret.setLocation(cursor.getString(1));

            try {
                ret.setAppointmentDate(dbDateFormatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ret.setDescription(cursor.getString(3));
        }

        return  ret;
    }

    public Appointment getAppointmentByDateTime(Date date)  {
        Appointment ret = null;

        String sql = "SELECT * FROM " + DatabaseHelper.APPOINTMENT_TABLE
//                + " WHERE " + DatabaseHelper.APPOINTMENT_COLUMN + " = ?";
                        + " WHERE " + DatabaseHelper.APPOINTMENT_COLUMN + " = '" + dbDateFormatter.format(date) + "'";

//        Cursor cursor = database.rawQuery(sql, new String[] { formatter.format(date) });
        Cursor cursor = database.rawQuery(sql, new String[]{});
        while (cursor.moveToNext()) {
            ret = new Appointment();
            ret.setId(cursor.getInt(0));
            ret.setLocation(cursor.getString(1));

            try {
                ret.setAppointmentDate(dbDateFormatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ret.setDescription(cursor.getString(3));
        }
        return ret;
    }

    public boolean truncateAllAppointments() {
        boolean result = false;

        database.execSQL("DROP TABLE IF EXISTS " + DatabaseHelper.APPOINTMENT_TABLE);
        database.execSQL(DatabaseHelper.CREATE_APPOINTMENT_TABLE);
        result = true;


        return result;
    }

}
