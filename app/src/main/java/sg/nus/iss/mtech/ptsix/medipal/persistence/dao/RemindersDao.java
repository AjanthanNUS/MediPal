package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminders;

/**
 * Created by WongCheeVui on 3/6/2017.
 */


/*
ID          interger
Frequency   interger
StartTime   date
Interval    interger

 */
public class RemindersDao  extends DBDAO {

    private static final String WHERE_ID_EQUALS = DatabaseHelper.REMINDERS_ID + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public RemindersDao(Context context) {
        super(context);
    }

    public long save(Reminders reminders) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.REMINDERS_FREQ, reminders.getFrequency());
        values.put(DatabaseHelper.REMINDERS_START_TIME, formatter.format(reminders.getEventStartTime()));
        values.put(DatabaseHelper.REMINDERS_INTERVAL, reminders.getInterval());
        return database.insert(DatabaseHelper.REMINDERS_TABLE, null, values);
    }

    public long update(Reminders reminders) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.REMINDERS_FREQ, reminders.getFrequency());
        values.put(DatabaseHelper.REMINDERS_START_TIME, formatter.format(reminders.getEventStartTime()));
        values.put(DatabaseHelper.REMINDERS_INTERVAL, reminders.getInterval());

        long result = database.update(DatabaseHelper.REMINDERS_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(reminders.getId()) });
        Log.d("Update Result:", "=" + result);
        return result;

    }

    public int delete(Reminders reminders) {
        return database.delete(DatabaseHelper.REMINDERS_TABLE, WHERE_ID_EQUALS,
                new String[] { reminders.getId() + "" });
    }
    //USING query() method
    public ArrayList<Reminders> getReminders() {
        ArrayList<Reminders> reminders_array = new ArrayList<Reminders>();

        Cursor cursor = database.query(DatabaseHelper.REMINDERS_TABLE,
                new String[] { DatabaseHelper.REMINDERS_ID,
                        DatabaseHelper.REMINDERS_FREQ,
                        DatabaseHelper.REMINDERS_START_TIME,
                        DatabaseHelper.REMINDERS_INTERVAL,
                }, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            Reminders reminders = new Reminders();
            reminders.setId(cursor.getInt(0));
            reminders.setFrequency(cursor.getInt(1));
            try {
                reminders.setEventStartTime(formatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                reminders.setEventStartTime(null);
            }
            reminders.setInterval(cursor.getInt(3));
            reminders_array.add(reminders);
        }
        return reminders_array;
    }
    //Retrieves a single reminder record with the given id
    public Reminders getReminders(long id) {
        Reminders reminders = null;

        String sql = "SELECT * FROM " + DatabaseHelper.REMINDERS_TABLE
                + " WHERE " + DatabaseHelper.REMINDERS_ID + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            reminders = new Reminders();
            reminders.setId(cursor.getInt(0));
            reminders.setFrequency(cursor.getInt(1));
            try {
                reminders.setEventStartTime(formatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                reminders.setEventStartTime(null);
            }
            reminders.setInterval(cursor.getInt(3));
        }
        return reminders;
    }
}


