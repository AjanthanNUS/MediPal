package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;

/**
 * Created by WongCheeVui on 3/6/2017.
 */

/*
 ID              interger
 Medicine        string
 Description     string
 CatID string    interger
 ReminderID      interger
 Remind          Bool
 Quantity        interger
 Dosage          interger
 ComsumeQuantity interger
 Threshold       interger
 DateIssued      Date
 ExpireFactor    interger
 */
public class MedicineDao extends DBDAO {
    
    private static final String WHERE_ID_EQUALS = DatabaseHelper.MEDI_ID + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    
    public MedicineDao(Context context) {
        super(context);
    }
    
    public long save(Medicine medicine) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MEDI_NAME, medicine.getEventMedicine());
        values.put(DatabaseHelper.MEDI_DESC, medicine.getEventDescription());
        values.put(DatabaseHelper.MEDI_CAT_ID, medicine.getEventCatID());
        values.put(DatabaseHelper.MEDI_REMINDER_ID, medicine.getEventReminderID());
        values.put(DatabaseHelper.MEDI_REMIND_ENABLED, medicine.getEventRemindEnabled());
        values.put(DatabaseHelper.MEDI_QUANTITY, medicine.getEventQuantity());
        values.put(DatabaseHelper.MEDI_DOSAGE, medicine.getEventDosage());
        values.put(DatabaseHelper.MEDI_CONSUME_QUANTITY, medicine.getEventConsumeQuantity());
        values.put(DatabaseHelper.MEDI_THRESHOLD, medicine.getEventThreshold());
        values.put(DatabaseHelper.MEDI_DATE_ISSUED,  formatter.format(medicine.getEventDateIssued()));
        values.put(DatabaseHelper.MEDI_EXPIRE_FACTOR, medicine.getEventExpireFactor());
        return database.insert(DatabaseHelper.MEDI_TABLE, null, values);
    }
    
    public long update(Medicine medicine) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MEDI_NAME, medicine.getEventMedicine());
        values.put(DatabaseHelper.MEDI_DESC, medicine.getEventDescription());
        values.put(DatabaseHelper.MEDI_CAT_ID, medicine.getEventCatID());
        values.put(DatabaseHelper.MEDI_REMINDER_ID, medicine.getEventReminderID());
        values.put(DatabaseHelper.MEDI_REMIND_ENABLED, medicine.getEventRemindEnabled());
        values.put(DatabaseHelper.MEDI_QUANTITY, medicine.getEventQuantity());
        values.put(DatabaseHelper.MEDI_DOSAGE, medicine.getEventDosage());
        values.put(DatabaseHelper.MEDI_CONSUME_QUANTITY, medicine.getEventConsumeQuantity());
        values.put(DatabaseHelper.MEDI_THRESHOLD, medicine.getEventThreshold());
        values.put(DatabaseHelper.MEDI_DATE_ISSUED,  formatter.format(medicine.getEventDateIssued()));
        values.put(DatabaseHelper.MEDI_EXPIRE_FACTOR, medicine.getEventExpireFactor());
        
        
        long result = database.update(DatabaseHelper.MEDI_TABLE, values,
                                      WHERE_ID_EQUALS,
                                      new String[] { String.valueOf(medicine.getId()) });
        Log.d("Update Result:", "=" + result);
        return result;
        
    }
    
    public int delete(Medicine medicine) {
        return database.delete(DatabaseHelper.MEDI_TABLE, WHERE_ID_EQUALS,
                               new String[] { medicine.getId() + "" });
    }
    
    //USING query() method
    public ArrayList<Medicine> getMedicines() {
        ArrayList<Medicine> medicines = new ArrayList<Medicine>();
        
        Cursor cursor = database.query(DatabaseHelper.MEDI_TABLE,
                                       new String[] { DatabaseHelper.MEDI_ID,
                                           DatabaseHelper.MEDI_NAME,
                                           DatabaseHelper.MEDI_DESC,
                                           DatabaseHelper.MEDI_CAT_ID,
                                           DatabaseHelper.MEDI_REMINDER_ID,
                                           DatabaseHelper.MEDI_REMIND_ENABLED,
                                           DatabaseHelper.MEDI_QUANTITY,
                                           DatabaseHelper.MEDI_DOSAGE,
                                           DatabaseHelper.MEDI_CONSUME_QUANTITY,
                                           DatabaseHelper.MEDI_THRESHOLD,
                                           DatabaseHelper.MEDI_DATE_ISSUED,
                                           DatabaseHelper.MEDI_EXPIRE_FACTOR,
                                       }, null, null, null,
                                       null, null);
        
        while (cursor.moveToNext()) {
            Medicine medicine = new Medicine();
            medicine.setId(cursor.getInt(0));
            medicine.setEventMedicine(cursor.getString(1));
            medicine.setEventDescription(cursor.getString(2));
            medicine.setEventCatID(cursor.getInt(3));
            medicine.setEventReminderID(cursor.getInt(4));
            medicine.setEventRemindEnabled(cursor.getInt(5));
            medicine.setEventQuantity(cursor.getInt(6));
            medicine.setEventDosage(cursor.getInt(7));
            medicine.setEventConsumeQuantity(cursor.getInt(8));
            medicine.setEventThreshold(cursor.getInt(9));
            try {
                medicine.setEventDateIssued(formatter.parse(cursor.getString(10)));
            } catch (ParseException e) {
                medicine.setEventDateIssued(null);
            }
            medicine.setEventExpireFactor(cursor.getInt(11));
            medicines.add(medicine);
        }
        return medicines;
    }
    
    //Retrieves a single reminder record with the given id
    public Medicine getMedicine(long id) {
        Medicine medicine = null;
        
        String sql = "SELECT * FROM " + DatabaseHelper.MEDI_TABLE
        + " WHERE " + DatabaseHelper.MEDI_ID + " = ?";
        
        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });
        
        if (cursor.moveToNext()) {
            medicine = new Medicine();
            medicine.setId(cursor.getInt(0));
            medicine.setEventMedicine(cursor.getString(1));
            medicine.setEventDescription(cursor.getString(2));
            medicine.setEventCatID(cursor.getInt(3));
            medicine.setEventReminderID(cursor.getInt(4));
            medicine.setEventRemindEnabled(cursor.getInt(5));
            medicine.setEventQuantity(cursor.getInt(6));
            medicine.setEventDosage(cursor.getInt(7));
            medicine.setEventConsumeQuantity(cursor.getInt(8));
            medicine.setEventThreshold(cursor.getInt(9));
            try {
                medicine.setEventDateIssued(formatter.parse(cursor.getString(10)));
            } catch (ParseException e) {
                medicine.setEventDateIssued(null);
            }
            medicine.setEventExpireFactor(cursor.getInt(11));
        }
        return medicine;
    }
}
