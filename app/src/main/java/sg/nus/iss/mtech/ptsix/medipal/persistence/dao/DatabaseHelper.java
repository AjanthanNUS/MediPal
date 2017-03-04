package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by win on 28/2/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "TaskDB";
    private static final int DATABASE_VERSION = 1;
     private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getHelper(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);

    }
}
