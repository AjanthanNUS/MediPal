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
        values.put(DatabaseHelper.CATS_CATEGORIES, categories.getCategory());
        values.put(DatabaseHelper.CATS_CODE, categories.getCode());
        values.put(DatabaseHelper.CATS_DESC, categories.getDescription());
        values.put(DatabaseHelper.CATS_REMINDERS_ENABLED, categories.getRemind());
        return database.insert(DatabaseHelper.CATS_TABLE, null, values);
    }

    public long update(Categories categories) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CATS_CATEGORIES, categories.getCategory());
        values.put(DatabaseHelper.CATS_CODE, categories.getCode());
        values.put(DatabaseHelper.CATS_DESC, categories.getDescription());
        values.put(DatabaseHelper.CATS_REMINDERS_ENABLED, categories.getRemind());


        long result = database.update(DatabaseHelper.CATS_TABLE, values,
                WHERE_ID_EQUALS,
                new String[]{String.valueOf(categories.getId())});
        Log.d("Update Result:", "=" + result);
        return result;

    }

    public int delete(Categories categories) {
        return database.delete(DatabaseHelper.CATS_TABLE, WHERE_ID_EQUALS,
                new String[]{categories.getId() + ""});
    }

    public ArrayList<Categories> getCategories() {
        ArrayList<Categories> categories_array = new ArrayList<Categories>();

        Cursor cursor = database.query(DatabaseHelper.CATS_TABLE,
                new String[]{DatabaseHelper.CATS_ID,
                        DatabaseHelper.CATS_CATEGORIES,
                        DatabaseHelper.CATS_CODE,
                        DatabaseHelper.CATS_DESC,
                        DatabaseHelper.CATS_REMINDERS_ENABLED,
                }, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            Categories categories = new Categories();
            categories.setId(cursor.getInt(0));
            categories.setCategory(cursor.getString(1));
            categories.setCode(cursor.getString(2));
            categories.setDescription(cursor.getString(3));
            categories.setRemind(cursor.getInt(4));
            categories_array.add(categories);
        }
        return categories_array;
    }

    public Categories getCategories(long id) {
        Categories categories = null;

        String sql = "SELECT * FROM " + DatabaseHelper.CATS_TABLE
                + " WHERE " + DatabaseHelper.CATS_ID + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[]{id + ""});

        if (cursor.moveToNext()) {
            categories = new Categories();
            categories.setId(cursor.getInt(0));
            categories.setCategory(cursor.getString(1));
            categories.setCode(cursor.getString(2));
            categories.setDescription(cursor.getString(3));
            categories.setRemind(cursor.getInt(4));
        }
        return categories;
    }

    public ArrayList<Categories> getCategoriesByCode(String code) {
        ArrayList<Categories> categories_array = new ArrayList<Categories>();

        String sql = "SELECT * FROM " + DatabaseHelper.CATS_TABLE
                + " WHERE " + DatabaseHelper.CATS_CODE + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[]{code});

        while (cursor.moveToNext()) {
            Categories categories = new Categories();
            categories.setId(cursor.getInt(0));
            categories.setCategory(cursor.getString(1));
            categories.setCode(cursor.getString(2));
            categories.setDescription(cursor.getString(3));
            categories.setRemind(cursor.getInt(4));
            categories_array.add(categories);
        }
        return categories_array;
    }

    public boolean truncateAllCategories() {
        boolean result = false;

        database.execSQL("DROP TABLE IF EXISTS " + DatabaseHelper.MEDI_TABLE);
        database.execSQL(DatabaseHelper.CREATE_MEDI_TABLE);

        database.execSQL("DROP TABLE IF EXISTS " + DatabaseHelper.CATS_TABLE);
        database.execSQL(DatabaseHelper.CREATE_CATS_TABLE);
        result = true;

        return result;
    }
}
