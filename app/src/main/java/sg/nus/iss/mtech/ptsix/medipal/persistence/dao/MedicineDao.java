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

public class MedicineDao extends DBDAO {

    private static final String WHERE_ID_EQUALS = DatabaseHelper.MEDI_ID + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public MedicineDao(Context context) {
        super(context);
    }

    public long save(Medicine medicine) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MEDI_NAME, medicine.getMedicine());
        values.put(DatabaseHelper.MEDI_DESC, medicine.getDescription());
        values.put(DatabaseHelper.MEDI_CAT_ID, medicine.getCatId());
        values.put(DatabaseHelper.MEDI_REMINDER_ID, medicine.getReminderId());
        values.put(DatabaseHelper.MEDI_REMIND_ENABLED, medicine.getRemind());
        values.put(DatabaseHelper.MEDI_QUANTITY, medicine.getQuantity());
        values.put(DatabaseHelper.MEDI_DOSAGE, medicine.getDosage());
        values.put(DatabaseHelper.MEDI_CONSUME_QUANTITY, medicine.getConsumeQuantity());
        values.put(DatabaseHelper.MEDI_THRESHOLD, medicine.getThreshold());
        values.put(DatabaseHelper.MEDI_DATE_ISSUED,  formatter.format(medicine.getDateIssued()));
        values.put(DatabaseHelper.MEDI_EXPIRE_FACTOR, medicine.getExpireFactor());
        return database.insert(DatabaseHelper.MEDI_TABLE, null, values);
    }

    public long update(Medicine medicine) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MEDI_NAME, medicine.getMedicine());
        values.put(DatabaseHelper.MEDI_DESC, medicine.getDescription());
        values.put(DatabaseHelper.MEDI_CAT_ID, medicine.getCatId());
        values.put(DatabaseHelper.MEDI_REMINDER_ID, medicine.getReminderId());
        values.put(DatabaseHelper.MEDI_REMIND_ENABLED, medicine.getRemind());
        values.put(DatabaseHelper.MEDI_QUANTITY, medicine.getQuantity());
        values.put(DatabaseHelper.MEDI_DOSAGE, medicine.getDosage());
        values.put(DatabaseHelper.MEDI_CONSUME_QUANTITY, medicine.getConsumeQuantity());
        values.put(DatabaseHelper.MEDI_THRESHOLD, medicine.getThreshold());
        values.put(DatabaseHelper.MEDI_DATE_ISSUED,  formatter.format(medicine.getDateIssued()));
        values.put(DatabaseHelper.MEDI_EXPIRE_FACTOR, medicine.getExpireFactor());


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
            medicine.setMedicine(cursor.getString(1));
            medicine.setDescription(cursor.getString(2));
            medicine.setCatId(cursor.getInt(3));
            medicine.setReminderId(cursor.getInt(4));
            medicine.setRemind(cursor.getInt(5));
            medicine.setQuantity(cursor.getInt(6));
            medicine.setDosage(cursor.getInt(7));
            medicine.setConsumeQuantity(cursor.getInt(8));
            medicine.setThreshold(cursor.getInt(9));
            try {
                medicine.setDateIssued(formatter.parse(cursor.getString(10)));
            } catch (ParseException e) {
                medicine.setDateIssued(null);
            }
            medicine.setExpireFactor(cursor.getInt(11));
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
            medicine.setMedicine(cursor.getString(1));
            medicine.setDescription(cursor.getString(2));
            medicine.setCatId(cursor.getInt(3));
            medicine.setReminderId(cursor.getInt(4));
            medicine.setRemind(cursor.getInt(5));
            medicine.setQuantity(cursor.getInt(6));
            medicine.setDosage(cursor.getInt(7));
            medicine.setConsumeQuantity(cursor.getInt(8));
            medicine.setThreshold(cursor.getInt(9));
            try {
                medicine.setDateIssued(formatter.parse(cursor.getString(10)));
            } catch (ParseException e) {
                medicine.setDateIssued(null);
            }
            medicine.setExpireFactor(cursor.getInt(11));
        }
        return medicine;
    }

    public ArrayList<Medicine> getMedicineByName(String name) {
        ArrayList<Medicine> medicines = new ArrayList<Medicine>();

        String sql = "SELECT * FROM " + DatabaseHelper.MEDI_TABLE
                + " WHERE " + DatabaseHelper.MEDI_NAME + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { name + "" });

        while (cursor.moveToNext()) {
            Medicine medicine = new Medicine();
            medicine.setId(cursor.getInt(0));
            medicine.setMedicine(cursor.getString(1));
            medicine.setDescription(cursor.getString(2));
            medicine.setCatId(cursor.getInt(3));
            medicine.setReminderId(cursor.getInt(4));
            medicine.setRemind(cursor.getInt(5));
            medicine.setQuantity(cursor.getInt(6));
            medicine.setDosage(cursor.getInt(7));
            medicine.setConsumeQuantity(cursor.getInt(8));
            medicine.setThreshold(cursor.getInt(9));
            try {
                medicine.setDateIssued(formatter.parse(cursor.getString(10)));
            } catch (ParseException e) {
                medicine.setDateIssued(null);
            }
            medicine.setExpireFactor(cursor.getInt(11));
            medicines.add(medicine);
        }
        return medicines;
    }
}
