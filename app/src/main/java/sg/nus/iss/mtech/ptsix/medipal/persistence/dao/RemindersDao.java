package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminders;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ReminderVO;

public class RemindersDao extends DBDAO {

    private static final String WHERE_ID_EQUALS = DatabaseHelper.REMINDERS_ID + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private MedicineDao medicineDao;
    private CategoriesDao categoriesDao;

    public RemindersDao(Context context) {
        super(context);
        medicineDao = new MedicineDao(context);
        categoriesDao = new CategoriesDao(context);
    }

    public long save(Reminders reminders) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.REMINDERS_FREQ, reminders.getFrequency());
        values.put(DatabaseHelper.REMINDERS_START_TIME, formatter.format(reminders.getStartTime()));
        values.put(DatabaseHelper.REMINDERS_INTERVAL, reminders.getInterval());
        return database.insert(DatabaseHelper.REMINDERS_TABLE, null, values);
    }

    public long update(Reminders reminders) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.REMINDERS_FREQ, reminders.getFrequency());
        values.put(DatabaseHelper.REMINDERS_START_TIME, formatter.format(reminders.getStartTime()));
        values.put(DatabaseHelper.REMINDERS_INTERVAL, reminders.getInterval());

        long result = database.update(DatabaseHelper.REMINDERS_TABLE, values,
                WHERE_ID_EQUALS,
                new String[]{String.valueOf(reminders.getId())});
        Log.d("Update Result:", "=" + result);
        return result;

    }

    public int delete(Reminders reminders) {
        return database.delete(DatabaseHelper.REMINDERS_TABLE, WHERE_ID_EQUALS,
                new String[]{reminders.getId() + ""});
    }

    //USING query() method
    public ArrayList<Reminders> getReminders() {
        ArrayList<Reminders> reminders_array = new ArrayList<Reminders>();

        Cursor cursor = database.query(DatabaseHelper.REMINDERS_TABLE,
                new String[]{DatabaseHelper.REMINDERS_ID,
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
                reminders.setStartTime(formatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                reminders.setStartTime(null);
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

        Cursor cursor = database.rawQuery(sql, new String[]{id + ""});

        if (cursor.moveToNext()) {
            reminders = new Reminders();
            reminders.setId(cursor.getInt(0));
            reminders.setFrequency(cursor.getInt(1));
            try {
                reminders.setStartTime(formatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                reminders.setStartTime(null);
            }
            reminders.setInterval(cursor.getInt(3));
        }
        return reminders;
    }

    public List<ReminderVO> getReminderVOs() {
        ReminderVO reminderVO;
        ArrayList<ReminderVO> reminderVOs = new ArrayList<>();
        String sql = "SELECT * FROM " + DatabaseHelper.REMINDERS_TABLE + " JOIN " + DatabaseHelper.MEDI_TABLE +
                " ON " + DatabaseHelper.REMINDERS_TABLE + "." + DatabaseHelper.REMINDERS_ID + "=" +
                DatabaseHelper.MEDI_TABLE + "." + DatabaseHelper.MEDI_REMINDER_ID;

        Cursor cursor = database.rawQuery(sql, new String[]{});

        while (cursor.moveToNext()) {
            reminderVO = new ReminderVO();
            reminderVO.setId(cursor.getInt(0));
            reminderVO.setFrequency(cursor.getInt(1));
            try {
                reminderVO.setStartTime(formatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                reminderVO.setStartTime(null);
            }
            reminderVO.setInterval(cursor.getInt(3));

            int medicineId = cursor.getInt(4);
            Medicine medicine = medicineDao.getMedicine(medicineId);
            reminderVO.setMedicine(medicine);

            int catId = cursor.getInt(7);
            Categories categories = categoriesDao.getCategories(catId);
            reminderVO.setCategory(categories);
            reminderVOs.add(reminderVO);
        }
        return reminderVOs;
    }
}


