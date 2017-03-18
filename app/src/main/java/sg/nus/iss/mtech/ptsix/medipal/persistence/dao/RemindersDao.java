package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminder;

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

    public long save(Reminder reminder) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.REMINDERS_FREQ, reminder.getFrequency());
        values.put(DatabaseHelper.REMINDERS_START_TIME, formatter.format(reminder.getStartTime()));
        values.put(DatabaseHelper.REMINDERS_INTERVAL, reminder.getInterval());
        return database.insert(DatabaseHelper.REMINDERS_TABLE, null, values);
    }

    public long update(Reminder reminder) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.REMINDERS_FREQ, reminder.getFrequency());
        values.put(DatabaseHelper.REMINDERS_START_TIME, formatter.format(reminder.getStartTime()));
        values.put(DatabaseHelper.REMINDERS_INTERVAL, reminder.getInterval());

        long result = database.update(DatabaseHelper.REMINDERS_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(reminder.getId()) });
        Log.d("Update Result:", "=" + result);
        return result;

    }

    public int delete(Reminder reminder) {
        return database.delete(DatabaseHelper.REMINDERS_TABLE, WHERE_ID_EQUALS,
                new String[] { reminder.getId() + "" });
    }
    //USING query() method
    public ArrayList<Reminder> getReminders() {
        ArrayList<Reminder> reminder_array = new ArrayList<Reminder>();

        Cursor cursor = database.query(DatabaseHelper.REMINDERS_TABLE,
                new String[] { DatabaseHelper.REMINDERS_ID,
                        DatabaseHelper.REMINDERS_FREQ,
                        DatabaseHelper.REMINDERS_START_TIME,
                        DatabaseHelper.REMINDERS_INTERVAL,
                }, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            Reminder reminder = new Reminder();
            reminder.setId(cursor.getInt(0));
            reminder.setFrequency(cursor.getInt(1));
            try {
                reminder.setStartTime(formatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                reminder.setStartTime(null);
            }
            reminder.setInterval(cursor.getInt(3));
            reminder_array.add(reminder);
        }
        return reminder_array;
    }
    //Retrieves a single reminder record with the given id
    public Reminder getReminders(long id) {
        Reminder reminder = null;

        String sql = "SELECT * FROM " + DatabaseHelper.REMINDERS_TABLE
                + " WHERE " + DatabaseHelper.REMINDERS_ID + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            reminder = new Reminder();
            reminder.setId(cursor.getInt(0));
            reminder.setFrequency(cursor.getInt(1));
            try {
                reminder.setStartTime(formatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                reminder.setStartTime(null);
            }
            reminder.setInterval(cursor.getInt(3));
        }
        return reminder;
    }
}


