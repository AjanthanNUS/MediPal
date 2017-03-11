package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by win on 28/2/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "medipaldb";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper instance;

    public static final String APPOINTMENT_TABLE = "Appointment";
    public static final String APPOINTMENT_ID_COLUMN = "ID";
    public static final String LOCATION_COLUMN = "Location";
    public static final String APPOINTMENT_COLUMN = "Appointment";
    public static final String DESCRIPTION_COLUMN = "Description";

    public static final String CREATE_APPOINTMENT_TABLE = "CREATE TABLE "
            + APPOINTMENT_TABLE + "(" + APPOINTMENT_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + LOCATION_COLUMN + " TEXT, " + APPOINTMENT_COLUMN + " TEXT, "
            + DESCRIPTION_COLUMN + " TEXT" + ")";


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
        db.execSQL(CREATE_APPOINTMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_APPOINTMENT_TABLE);
        onCreate(db);
    }

}
