package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.HealthBio;

/**
 * Created by WongCheeVui on 3/6/2017.
 */

/*
ID              interger
Condition       String
StartDate       Date
ConditionType   String

 */
public class HealthBioDao extends DBDAO {

    private static final String WHERE_ID_EQUALS = DatabaseHelper.HEALTH_ID + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public HealthBioDao(Context context) {
        super(context);
    }

    public long save(HealthBio healthBio) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.HEALTH_CONDITION, healthBio.getEventCondition());
        values.put(DatabaseHelper.HEALTH_START_DATE,  formatter.format(healthBio.getEventStartDate()));
        values.put(DatabaseHelper.HEALTH_CONDITION_TYPE, healthBio.getEventConditionType());
        return database.insert(DatabaseHelper.HEALTH_BIO_TABLE, null, values);
    }

    public long update(HealthBio healthBio) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.HEALTH_CONDITION, healthBio.getEventCondition());
        values.put(DatabaseHelper.HEALTH_START_DATE,  formatter.format(healthBio.getEventStartDate()));
        values.put(DatabaseHelper.HEALTH_CONDITION_TYPE, healthBio.getEventConditionType());


        long result = database.update(DatabaseHelper.HEALTH_BIO_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(healthBio.getId()) });
        Log.d("Update Result:", "=" + result);
        return result;

    }

    public int delete(HealthBio healthBio) {
        return database.delete(DatabaseHelper.HEALTH_BIO_TABLE, WHERE_ID_EQUALS,
                new String[] { healthBio.getId() + "" });
    }

    public void delete() {
         database.execSQL("DELETE FROM " + DatabaseHelper.HEALTH_BIO_TABLE );
    }

    //USING query() method
    public ArrayList<HealthBio> getHealthBios() {
        ArrayList<HealthBio> healthBios = new ArrayList<HealthBio>();

        Cursor cursor = database.query(DatabaseHelper.HEALTH_BIO_TABLE,
                new String[] { DatabaseHelper.HEALTH_ID,
                        DatabaseHelper.HEALTH_CONDITION,
                        DatabaseHelper.HEALTH_START_DATE,
                        DatabaseHelper.HEALTH_CONDITION_TYPE,
                }, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            HealthBio healthBio = new HealthBio();
            healthBio.setId(cursor.getInt(0));
            healthBio.setEventCondition(cursor.getString(1));
            try {
                healthBio.setEventStartDate(formatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                healthBio.setEventStartDate(null);
            }
            healthBio.setEventConditionType(cursor.getString(3));
            healthBios.add(healthBio);
        }
        return healthBios;
    }
    //Retrieves a single reminder record with the given id
    public HealthBio getHealthBio(long id) {
        HealthBio healthBio = null;

        String sql = "SELECT * FROM " + DatabaseHelper.HEALTH_BIO_TABLE
                + " WHERE " + DatabaseHelper.HEALTH_ID + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            healthBio = new HealthBio();
            healthBio.setId(cursor.getInt(0));
            healthBio.setEventCondition(cursor.getString(1));
            try {
                healthBio.setEventStartDate(formatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                healthBio.setEventStartDate(null);
            }
            healthBio.setEventConditionType(cursor.getString(3));
        }
        return healthBio;
    }
}

