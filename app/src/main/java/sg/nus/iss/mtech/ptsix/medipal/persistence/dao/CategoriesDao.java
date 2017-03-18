package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;

public class CategoriesDao extends DBDAO {

    private static final String WHERE_ID_EQUALS = DatabaseHelper.CATS_ID + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public CategoriesDao(Context context) {
        super(context);
    }

    public long save(Categories categories) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CATS_CATEGORIES, categories.getEventCategory());
        values.put(DatabaseHelper.CATS_CODE, categories.getEventCode());
        values.put(DatabaseHelper.CATS_DESC, categories.getEventDescription());
        values.put(DatabaseHelper.CATS_REMINDERS_ENABLED, categories.getEventRemind());
        return database.insert(DatabaseHelper.CATS_TABLE, null, values);
    }

    public long update(Categories categories) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CATS_CATEGORIES, categories.getEventCategory());
        values.put(DatabaseHelper.CATS_CODE, categories.getEventCode());
        values.put(DatabaseHelper.CATS_DESC, categories.getEventDescription());
        values.put(DatabaseHelper.CATS_REMINDERS_ENABLED, categories.getEventRemind());


        long result = database.update(DatabaseHelper.CATS_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(categories.getId()) });
        Log.d("Update Result:", "=" + result);
        return result;

    }

    public int delete(Categories categories) {
        return database.delete(DatabaseHelper.CATS_TABLE, WHERE_ID_EQUALS,
                new String[] { categories.getId() + "" });
    }

    //USING query() method
    public ArrayList<Categories> getCategories() {
        ArrayList<Categories> categories_array = new ArrayList<Categories>();

        Cursor cursor = database.query(DatabaseHelper.CATS_TABLE,
                new String[] { DatabaseHelper.CATS_ID,
                        DatabaseHelper.CATS_CATEGORIES,
                        DatabaseHelper.CATS_CODE,
                        DatabaseHelper.CATS_DESC,
                        DatabaseHelper.CATS_REMINDERS_ENABLED,
                }, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            Categories categories = new Categories();
            categories.setId(cursor.getInt(0));
            categories.setEventCategory(cursor.getString(1));
            categories.setEventCode(cursor.getString(2));
            categories.setEventDescription(cursor.getString(3));
            categories.setEventRemind(cursor.getInt(4));
            categories_array.add(categories);
        }
        return categories_array;
    }
    //Retrieves a single reminder record with the given id
    public Categories getCategories(long id) {
        Categories categories = null;

        String sql = "SELECT * FROM " + DatabaseHelper.CATS_TABLE
                + " WHERE " + DatabaseHelper.CATS_ID + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            categories = new Categories();
            categories.setId(cursor.getInt(0));
            categories.setEventCategory(cursor.getString(1));
            categories.setEventCode(cursor.getString(2));
            categories.setEventDescription(cursor.getString(3));
            categories.setEventRemind(cursor.getInt(4));
        }
        return categories;
    }
}
