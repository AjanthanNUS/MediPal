package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.ICE;

/**
 * Created by WongCheeVui on 3/6/2017.
 */

/*
ID              interger
Name            string
ContactNo       string
ContactType     interger
Description     string
Sequence        interger


 */
public class IceDao extends DBDAO {

    private static final String WHERE_ID_EQUALS = DatabaseHelper.ICE_ID + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public IceDao(Context context) {
        super(context);
    }

    public long save(ICE ice) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ICE_NAME, ice.getEventName());
        values.put(DatabaseHelper.ICE_CONTACT_NO, ice.getEventContactNo());
        values.put(DatabaseHelper.ICE_CONTACT_TYPE, ice.getContactType());
        values.put(DatabaseHelper.ICE_DESC, ice.getEventDescription());
        values.put(DatabaseHelper.ICE_SEQ, ice.getSequence());

        return database.insert(DatabaseHelper.ICE_TABLE, null, values);
    }

    public long update(ICE ice) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ICE_NAME, ice.getEventName());
        values.put(DatabaseHelper.ICE_CONTACT_NO, ice.getEventContactNo());
        values.put(DatabaseHelper.ICE_CONTACT_TYPE, ice.getContactType());
        values.put(DatabaseHelper.ICE_DESC, ice.getEventDescription());
        values.put(DatabaseHelper.ICE_SEQ, ice.getSequence());


        long result = database.update(DatabaseHelper.ICE_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(ice.getId()) });
        Log.d("Update Result:", "=" + result);
        return result;

    }

    public int delete(ICE ice) {
        return database.delete(DatabaseHelper.ICE_TABLE, WHERE_ID_EQUALS,
                new String[] { ice.getId() + "" });
    }
    //USING query() method
    public ArrayList<ICE> getICEs() {
        ArrayList<ICE> ices = new ArrayList<ICE>();

        Cursor cursor = database.query(DatabaseHelper.ICE_TABLE,
                new String[] { DatabaseHelper.ICE_NAME,
                        DatabaseHelper.ICE_CONTACT_NO,
                        DatabaseHelper.ICE_CONTACT_NO,
                        DatabaseHelper.ICE_CONTACT_TYPE,
                        DatabaseHelper.ICE_DESC,
                        DatabaseHelper.ICE_SEQ,
                }, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            ICE ice = new ICE();
            ice.setId(cursor.getInt(0));
            ice.setEventName(cursor.getString(1));
            ice.setEventContactNo(cursor.getString(2));
            ice.setContactType(cursor.getInt(3));
            ice.setEventDescription(cursor.getString(4));
            ice.setSequence(cursor.getInt(5));
            ices.add(ice);
        }
        return ices;
    }
    //Retrieves a single reminder record with the given id
    public ICE getICE(long id) {
        ICE ice = null;

        String sql = "SELECT * FROM " + DatabaseHelper.ICE_TABLE
                + " WHERE " + DatabaseHelper.ICE_ID + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            ice = new ICE();
            ice.setId(cursor.getInt(0));
            ice.setEventName(cursor.getString(1));
            ice.setEventContactNo(cursor.getString(2));
            ice.setContactType(cursor.getInt(3));
            ice.setEventDescription(cursor.getString(4));
            ice.setSequence(cursor.getInt(5));
        }
        return ice;
    }
}

