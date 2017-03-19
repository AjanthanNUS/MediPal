package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;

import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.PersonalBio;

public class PersonalBioDao extends DBDAO {

    private static final String WHERE_ID_EQUALS = DatabaseHelper.PER_ID + " =?";

    public PersonalBioDao(Context context) {
        super(context);
    }

    public long savePersonalBio(PersonalBio personalBio) {
        long result = 0;
        if (personalBio.getId() > 0) {
            result = update(personalBio);
        } else {
            result = create(personalBio);
        }
        return result;
    }

    public long create(PersonalBio personalBio) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PER_NAME, personalBio.getUserName());
        values.put(DatabaseHelper.PER_DOB, personalBio.getUserDOB().toString());
        values.put(DatabaseHelper.PER_ID_NO, personalBio.getUserIDNo());
        values.put(DatabaseHelper.PER_ADDRESS, personalBio.getAddress());
        values.put(DatabaseHelper.PER_POSTAL_CODE, personalBio.getPostalcode());
        values.put(DatabaseHelper.PER_HEIGHT, personalBio.getHeight());
        values.put(DatabaseHelper.PER_BLOOD_TYPE, personalBio.getBloodType());

        return database.insert(DatabaseHelper.PERSONAL_BIO_TABLE, null, values);
    }

    public long update(PersonalBio personalBio) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PER_NAME, personalBio.getUserName());
        values.put(DatabaseHelper.PER_DOB, CommonUtil.date2ddMMMYYYY(personalBio.getUserDOB()));
        values.put(DatabaseHelper.PER_ID_NO, personalBio.getUserIDNo());
        values.put(DatabaseHelper.PER_ADDRESS, personalBio.getAddress());
        values.put(DatabaseHelper.PER_POSTAL_CODE, personalBio.getPostalcode());
        values.put(DatabaseHelper.PER_HEIGHT, personalBio.getHeight());
        values.put(DatabaseHelper.PER_BLOOD_TYPE, personalBio.getBloodType());

        long result = database.update(DatabaseHelper.PERSONAL_BIO_TABLE, values,
                WHERE_ID_EQUALS,
                new String[]{String.valueOf(personalBio.getId())});
        Log.d("Update Result:", "=" + result);
        return result;

    }

    //Retrieves a single reminder record with the given id
    public PersonalBio getPersonalBio() {
        PersonalBio personalBio = null;

        String sql = "SELECT * FROM " + DatabaseHelper.PERSONAL_BIO_TABLE;

        Cursor cursor = database.query(DatabaseHelper.PERSONAL_BIO_TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            personalBio = new PersonalBio();
            personalBio.setId(cursor.getInt(0));
            personalBio.setUserName(cursor.getString(1));
            try {
                personalBio.setUserDOB(CommonUtil.ddmmmyyyy2date(cursor.getString(2)));
            } catch (ParseException e) {
                personalBio.setUserDOB(null);
            }
            personalBio.setUserIDNo(cursor.getString(3));
            personalBio.setAddress(cursor.getString(4));
            personalBio.setPostalcode(cursor.getString(5));
            personalBio.setHeight(cursor.getInt(6));
            personalBio.setBloodType(cursor.getString(7));
        }
        return personalBio;
    }
}
