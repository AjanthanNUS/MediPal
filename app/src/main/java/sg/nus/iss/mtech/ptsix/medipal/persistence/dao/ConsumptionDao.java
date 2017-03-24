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

public class ConsumptionDao extends DBDAO {

    private static final String WHERE_ID_EQUALS = DatabaseHelper.CON_ID + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());

    public ConsumptionDao(Context context) {
        super(context);
    }

    public long save(Consumption consumption) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CON_MEDICINE_ID, consumption.getMedicineID());
        values.put(DatabaseHelper.CON_QUANTITY, consumption.getQuantity());
        values.put(DatabaseHelper.CON_CONSUMED_ON, formatter.format(consumption.getConsumedOn()));
        return database.insert(DatabaseHelper.CONSUMPTION_TABLE, null, values);
    }

    public long update(Consumption consumption) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CON_MEDICINE_ID, consumption.getMedicineID());
        values.put(DatabaseHelper.CON_QUANTITY, consumption.getQuantity());
        values.put(DatabaseHelper.CON_CONSUMED_ON, formatter.format(consumption.getConsumedOn()));


        long result = database.update(DatabaseHelper.CONSUMPTION_TABLE, values,
                WHERE_ID_EQUALS,
                new String[]{String.valueOf(consumption.getId())});
        Log.d("Update Result:", "=" + result);
        return result;

    }

    public int delete(Consumption consumption) {
        return database.delete(DatabaseHelper.CONSUMPTION_TABLE, WHERE_ID_EQUALS,
                new String[]{consumption.getId() + ""});
    }

    public ArrayList<Consumption> getConsumptions() {
        ArrayList<Consumption> consumptions = new ArrayList<Consumption>();

        Cursor cursor = database.query(DatabaseHelper.CONSUMPTION_TABLE,
                new String[]{DatabaseHelper.CON_ID,
                        DatabaseHelper.CON_MEDICINE_ID,
                        DatabaseHelper.CON_QUANTITY,
                        DatabaseHelper.CON_CONSUMED_ON,
                }, null, null, null,
                null, DatabaseHelper.CON_ID + " DESC");

        while (cursor.moveToNext()) {
            Consumption consumption = new Consumption();
            consumption.setId(cursor.getInt(0));
            consumption.setMedicineID(cursor.getInt(1));
            consumption.setQuantity(cursor.getInt(2));
            try {
                consumption.setConsumedOn(formatter.parse(cursor.getString(3)));
            } catch (ParseException e) {
                consumption.setConsumedOn(null);
            }
            consumptions.add(consumption);
        }
        return consumptions;
    }

    public Consumption getConsumption(long id) {
        Consumption consumption = null;

        String sql = "SELECT * FROM " + DatabaseHelper.CONSUMPTION_TABLE
                + " WHERE " + DatabaseHelper.CON_ID + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[]{id + ""});

        if (cursor.moveToNext()) {
            consumption = new Consumption();
            consumption.setId(cursor.getInt(0));
            consumption.setMedicineID(cursor.getInt(1));
            consumption.setQuantity(cursor.getInt(2));
            try {
                consumption.setConsumedOn(formatter.parse(cursor.getString(3)));
            } catch (ParseException e) {
                consumption.setConsumedOn(null);
            }
        }
        return consumption;
    }

    public Consumption getConsumptionByRowID(long rowId) {
        Consumption consumption = null;

        String sql = "SELECT * FROM " + "( SELECT rowid, * FROM " + DatabaseHelper.CONSUMPTION_TABLE + " ) "
                + " WHERE  rowid = ?";

        Cursor cursor = database.rawQuery(sql, new String[]{rowId + ""});

        if (cursor.moveToNext()) {
            consumption = new Consumption();
            consumption.setId(cursor.getInt(1));
            consumption.setMedicineID(cursor.getInt(2));
            consumption.setQuantity(cursor.getInt(3));
            try {
                consumption.setConsumedOn(formatter.parse(cursor.getString(4)));
            } catch (ParseException e) {
                consumption.setConsumedOn(null);
            }
        }
        return consumption;
    }
}
