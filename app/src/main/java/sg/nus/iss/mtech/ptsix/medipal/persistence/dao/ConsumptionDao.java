package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Consumption;

/**
 * Created by WongCheeVui on 3/6/2017.
 */

/*
ID              interger
MedicineID      interger
Quantity        interger
ConsumedOn      Date

 */
public class ConsumptionDao extends DBDAO {

    private static final String WHERE_ID_EQUALS = DatabaseHelper.CON_ID + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public ConsumptionDao(Context context) {
        super(context);
    }

    public long save(Consumption consumption) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CON_MEDICINE_ID, consumption.getEventMedicineID());
        values.put(DatabaseHelper.CON_QUANTITY, consumption.getEventQuantity());
        values.put(DatabaseHelper.CON_CONSUMED_ON, formatter.format(consumption.getEventConsumedOn()));
        return database.insert(DatabaseHelper.CONSUMPTION_TABLE, null, values);
    }

    public long update(Consumption consumption) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CON_MEDICINE_ID, consumption.getEventMedicineID());
        values.put(DatabaseHelper.CON_QUANTITY, consumption.getEventQuantity());
        values.put(DatabaseHelper.CON_CONSUMED_ON, formatter.format(consumption.getEventConsumedOn()));


        long result = database.update(DatabaseHelper.CONSUMPTION_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(consumption.getId()) });
        Log.d("Update Result:", "=" + result);
        return result;

    }

    public int delete(Consumption consumption) {
        return database.delete(DatabaseHelper.CONSUMPTION_TABLE, WHERE_ID_EQUALS,
                new String[] { consumption.getId() + "" });
    }

    //USING query() method
    public ArrayList<Consumption> getConsumptions() {
        ArrayList<Consumption> consumptions = new ArrayList<Consumption>();

        Cursor cursor = database.query(DatabaseHelper.CONSUMPTION_TABLE,
                new String[] { DatabaseHelper.CON_ID,
                        DatabaseHelper.CON_MEDICINE_ID,
                        DatabaseHelper.CON_QUANTITY,
                        DatabaseHelper.CON_CONSUMED_ON,
                }, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            Consumption consumption = new Consumption();
            consumption.setId(cursor.getInt(0));
            consumption.setEventMedicineID(cursor.getInt(1));
            consumption.setEventQuantity(cursor.getInt(2));
            try {
                consumption.setEventConsumedOn(formatter.parse(cursor.getString(3)));
            } catch (ParseException e) {
                consumption.setEventConsumedOn(null);
            }
            consumptions.add(consumption);
        }
        return consumptions;
    }
    //Retrieves a single reminder record with the given id
    public Consumption getConsumption(long id) {
        Consumption consumption = null;

        String sql = "SELECT * FROM " + DatabaseHelper.CONSUMPTION_TABLE
                + " WHERE " + DatabaseHelper.CON_ID + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            consumption = new Consumption();
            consumption.setId(cursor.getInt(0));
            consumption.setEventMedicineID(cursor.getInt(1));
            consumption.setEventQuantity(cursor.getInt(2));
            try {
                consumption.setEventConsumedOn(formatter.parse(cursor.getString(3)));
            } catch (ParseException e) {
                consumption.setEventConsumedOn(null);
            }
        }
        return consumption;
    }
}
