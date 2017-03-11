package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;


/**
 * Created by WONG_CH on 05-Mar-17.
 */
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBDAO {
    protected SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;

    public DBDAO(Context context) {
        this.mContext = context;
        dbHelper = DatabaseHelper.getHelper(mContext);
        open();

    }

    public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = DatabaseHelper.getHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
        database = null;
    }
}

