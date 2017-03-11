package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.PersonalBio;


public class PersonalBioDao extends DBDAO {

    private static final String WHERE_ID_EQUALS = DatabaseHelper.PER_ID + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public PersonalBioDao(Context context) {
        super(context);
    }

    public long save(PersonalBio personalBio) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PER_NAME, personalBio.getEventName());
        values.put(DatabaseHelper.PER_DOB, formatter.format(personalBio.getEventDOB()));
        values.put(DatabaseHelper.PER_ID_NO, personalBio.getEventIDNo());
        values.put(DatabaseHelper.PER_ADDRESS, personalBio.getEventAddress());
        values.put(DatabaseHelper.PER_POSTAL_CODE, personalBio.getEventPostalCode());
        values.put(DatabaseHelper.PER_HEIGHT, personalBio.getEventHeight());
        values.put(DatabaseHelper.PER_BLOOD_TYPE, personalBio.getEventBloodType());

        return database.insert(DatabaseHelper.PERSONAL_BIO_TABLE, null, values);
    }

    public long update(PersonalBio personalBio) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PER_NAME, personalBio.getEventName());
        values.put(DatabaseHelper.PER_DOB, formatter.format(personalBio.getEventDOB()));
        values.put(DatabaseHelper.PER_ID_NO, personalBio.getEventIDNo());
        values.put(DatabaseHelper.PER_ADDRESS, personalBio.getEventAddress());
        values.put(DatabaseHelper.PER_POSTAL_CODE, personalBio.getEventPostalCode());
        values.put(DatabaseHelper.PER_HEIGHT, personalBio.getEventHeight());
        values.put(DatabaseHelper.PER_BLOOD_TYPE, personalBio.getEventBloodType());


        long result = database.update(DatabaseHelper.PERSONAL_BIO_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(personalBio.getId()) });
        Log.d("Update Result:", "=" + result);
        return result;

    }

    public int delete(PersonalBio personalBio) {
        return database.delete(DatabaseHelper.PERSONAL_BIO_TABLE, WHERE_ID_EQUALS,
                new String[] { personalBio.getId() + "" });
    }

    //USING query() method
    public ArrayList<PersonalBio> getPersonalBios() {
        ArrayList<PersonalBio> personalBios = new ArrayList<PersonalBio>();

        Cursor cursor = database.query(DatabaseHelper.PERSONAL_BIO_TABLE,
                new String[] { DatabaseHelper.PER_ID,
                        DatabaseHelper.PER_NAME }, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            PersonalBio personalBio = new PersonalBio();
            personalBio.setId(cursor.getInt(0));
            personalBio.setEventName(cursor.getString(1));

            personalBios.add(personalBio);
        }
        return personalBios;
    }

    //USING rawQuery() method
    public ArrayList<PersonalBio> getPersonalBiosRQ() {
        ArrayList<PersonalBio> personalBios = new ArrayList<PersonalBio>();

        String sql = "SELECT " + DatabaseHelper.PER_ID + ","
                + DatabaseHelper.PER_NAME;

        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            PersonalBio personalBio = new PersonalBio();
            personalBio.setId(cursor.getInt(0));
            personalBio.setEventName(cursor.getString(1));

            personalBios.add(personalBio);
        }
        return personalBios;
    }

    //USING query() method
    public ArrayList<PersonalBio> getPersonalBio() {
        ArrayList<PersonalBio> ices = new ArrayList<PersonalBio>();

        Cursor cursor = database.query(DatabaseHelper.PER_ID,
                new String[] { DatabaseHelper.PER_NAME,
                        DatabaseHelper.PER_DOB,
                        DatabaseHelper.PER_ID_NO,
                        DatabaseHelper.PER_ADDRESS,
                        DatabaseHelper.PER_POSTAL_CODE,
                        DatabaseHelper.PER_HEIGHT,
                        DatabaseHelper.PER_BLOOD_TYPE,
                }, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            PersonalBio personalBio = new PersonalBio();
            personalBio.setId(cursor.getInt(0));
            personalBio.setEventName(cursor.getString(1));
            try {
                personalBio.setEventDOB(formatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                personalBio.setEventDOB(null);
            }
            personalBio.setEventIDNo(cursor.getString(3));
            personalBio.setEventAddress(cursor.getString(4));
            personalBio.setEventPostalCode(cursor.getString(5));
            personalBio.setEventHeight(cursor.getInt(6));
            personalBio.setEventBloodType(cursor.getString(7));
        }
        return ices;
    }
    //Retrieves a single reminder record with the given id
    public PersonalBio getPersonalBio(long id) {
        PersonalBio personalBio = null;

        String sql = "SELECT * FROM " + DatabaseHelper.PERSONAL_BIO_TABLE
                + " WHERE " + DatabaseHelper.PER_ID + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            personalBio = new PersonalBio();
            personalBio.setId(cursor.getInt(0));
            personalBio.setEventName(cursor.getString(1));
            try {
                personalBio.setEventDOB(formatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                personalBio.setEventDOB(null);
            }
            personalBio.setEventIDNo(cursor.getString(3));
            personalBio.setEventAddress(cursor.getString(4));
            personalBio.setEventPostalCode(cursor.getString(5));
            personalBio.setEventHeight(cursor.getInt(6));
            personalBio.setEventBloodType(cursor.getString(7));

        }
        return personalBio;
    }
}
