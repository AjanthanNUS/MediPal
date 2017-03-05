package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by win on 28/2/17.
 */

public class DBDAO {
    protected SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private Context context;

    public DBDAO(Context context) {
        this.context = context;
        databaseHelper = DatabaseHelper.getHelper(context);
        database = databaseHelper.getWritableDatabase();
        open();
    }

    public void open() {
        if (databaseHelper == null) {
            databaseHelper = DatabaseHelper.getHelper(context);
            database = databaseHelper.getWritableDatabase();
        }
    }

    public void close() {
        databaseHelper.close();
        database = null;
    }



}
