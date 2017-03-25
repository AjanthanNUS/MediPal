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

public class AppointmentDao extends DBDAO {

    private static final String WHERE_ID_EQUALS = DatabaseHelper.APP_ID + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());

    public AppointmentDao(Context context) {
        super(context);
    }

    public long save(Appointment appointment) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.APP_LOCATION, appointment.getLocation());
        values.put(DatabaseHelper.APP_DATE, formatter.format(appointment.getAppointmentDate()));
        values.put(DatabaseHelper.APP_DESC, appointment.getDescription());
        return database.insert(DatabaseHelper.APPOINTMENT_TABLE, null, values);
    }

    public long update(Appointment appointment) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.APP_LOCATION, appointment.getLocation());
        values.put(DatabaseHelper.APP_DATE, formatter.format(appointment.getAppointmentDate()));
        values.put(DatabaseHelper.APP_DESC, appointment.getDescription());


        long result = database.update(DatabaseHelper.APPOINTMENT_TABLE, values,
                WHERE_ID_EQUALS,
                new String[]{String.valueOf(appointment.getId())});
        Log.d("Update Result:", "=" + result);
        return result;
    }

    public int delete(Appointment appointment) {
        return database.delete(DatabaseHelper.APPOINTMENT_TABLE, WHERE_ID_EQUALS,
                new String[]{appointment.getId() + ""});
    }

    //USING query() method
    public ArrayList<Appointment> getAppointments() {
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();

        Cursor cursor = database.query(DatabaseHelper.APPOINTMENT_TABLE,
                new String[]{DatabaseHelper.APP_ID,
                        DatabaseHelper.APP_LOCATION,
                        DatabaseHelper.APP_DATE,
                        DatabaseHelper.APP_DESC,
                }, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            Appointment appointment = new Appointment();
            appointment.setId(cursor.getInt(0));
            appointment.setLocation(cursor.getString(1));
            try {
                appointment.setAppointmentDate(formatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                appointment.setAppointmentDate(null);
            }
            appointment.setDescription(cursor.getString(3));
            appointments.add(appointment);
        }
        return appointments;
    }

    public Appointment getAppointment(long id) {
        Appointment appointment = null;

        String sql = "SELECT * FROM " + DatabaseHelper.APPOINTMENT_TABLE
                + " WHERE " + DatabaseHelper.APP_ID + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[]{id + ""});

        if (cursor.moveToNext()) {
            appointment = new Appointment();
            appointment.setId(cursor.getInt(0));
            appointment.setLocation(cursor.getString(1));
            try {
                appointment.setAppointmentDate(formatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                appointment.setAppointmentDate(null);
            }
            appointment.setDescription(cursor.getString(3));


        }
        return appointment;
    }

    public Appointment getAppointmentByDateTime(Date date) {
        Appointment ret = null;

        String sql = "SELECT * FROM " + DatabaseHelper.APPOINTMENT_TABLE
//                + " WHERE " + DatabaseHelper.APPOINTMENT_COLUMN + " = ?";
                + " WHERE " + DatabaseHelper.APP_DATE + " = '" + formatter.format(date) + "'";

//        Cursor cursor = database.rawQuery(sql, new String[] { formatter.format(date) });
        Cursor cursor = database.rawQuery(sql, new String[]{});
        while (cursor.moveToNext()) {
            ret = new Appointment();
            ret.setId(cursor.getInt(0));
            ret.setLocation(cursor.getString(1));

            try {
                ret.setAppointmentDate(formatter.parse(cursor.getString(2)));
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
