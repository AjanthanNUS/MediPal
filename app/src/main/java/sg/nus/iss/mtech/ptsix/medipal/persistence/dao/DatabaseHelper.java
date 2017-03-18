package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by WONG_CH on 05-Mar-17.
 */


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Medipaldb";
    private static final int DATABASE_VERSION = 1;

    public static final String PERSONAL_BIO_TABLE = "personal_bio";

    public static final String PER_ID = "per_id";
    public static final String PER_NAME = "per_name";
    public static final String PER_DOB = "per_dob";
    public static final String PER_ID_NO = "per_id_no";
    public static final String PER_ADDRESS = "per_address";
    public static final String PER_POSTAL_CODE = "per_postal_code";
    public static final String PER_HEIGHT = "per_height";
    public static final String PER_BLOOD_TYPE = "per_blood_type";

    public static final String CREATE_PERSONAL_BIO_TABLE = "CREATE TABLE "
            + PERSONAL_BIO_TABLE + "("
            + PER_ID + " INTEGER PRIMARY KEY, "
            + PER_NAME + " TEXT, "
            + PER_DOB + " DATE, "
            + PER_ID_NO + " TEXT, "
            + PER_ADDRESS + " TEXT, "
            + PER_POSTAL_CODE + " TEXT, "
            + PER_HEIGHT + " INTEGER, "
            + PER_BLOOD_TYPE + " TEXT "
            + ")";

    public static final String APPOINTMENT_TABLE = "appointment";
    public static final String APP_ID = "app_id";
    public static final String APP_LOCATION = "app_loc";
    public static final String APP_DATE = "app_date";
    public static final String APP_DESC = "app_desc";

    public static final String CREATE_APPOINTMENT_TABLE = "CREATE TABLE "
            + APPOINTMENT_TABLE + "("
            + APP_ID + " INTEGER PRIMARY KEY, "
            + APP_LOCATION + " TEXT, "
            + APP_DATE + " DATE, "
            + APP_DESC + " TEXT "
            + ")";


    public static final String HEALTH_BIO_TABLE = "health_bio";
    public static final String HEALTH_ID = "health_id";
    public static final String HEALTH_CONDITION = "health_condition";
    public static final String HEALTH_START_DATE = "health_startdate";
    public static final String HEALTH_CONDITION_TYPE = "health_condition_type";

    public static final String CREATE_HEALTH_BIO_TABLE = "CREATE TABLE "
            + HEALTH_BIO_TABLE + "("
            + HEALTH_ID + " INTEGER PRIMARY KEY, "
            + HEALTH_CONDITION + " TEXT, "
            + HEALTH_START_DATE + " DATE, "
            + HEALTH_CONDITION_TYPE + " TEXT "
            + ")";


    public static final String ICE_TABLE = "ice_table";
    public static final String ICE_ID = "ice_id";
    public static final String ICE_NAME = "ice_name";
    public static final String ICE_CONTACT_NO = "ice_contact_no";
    public static final String ICE_CONTACT_TYPE = "ice_contact_type";
    public static final String ICE_DESC = "ice_desc";
    public static final String ICE_SEQ = "ice_seq";

    public static final String CREATE_ICE_TABLE = "CREATE TABLE "
            + ICE_TABLE + "("
            + ICE_ID + " INTEGER PRIMARY KEY, "
            + ICE_NAME + " TEXT, "
            + ICE_CONTACT_NO + " TEXT, "
            + ICE_CONTACT_TYPE + " INTEGER, "
            + ICE_DESC + " TEXT, "
            + ICE_SEQ + " INTEGER "
            + ")";


    public static final String MEAS_TABLE = "meas_table";
    public static final String MEAS_ID = "meas_id";
    public static final String MEAS_SYSTOLIC = "meas_systolic";
    public static final String MEAS_DIASTOLIC = "meas_diastolic";
    public static final String MEAS_PULSE = "meas_id_pulse";
    public static final String MEAS_TEMPERATURE = "meas_id_temperature";
    public static final String MEAS_WEIGHT = "meas_id_weight";
    public static final String MEAS_MEASURED_ON = "meas_id_measured_on";
    public static final String MEAS_MEASURED_COMMENT = "meas_comment";

    public static final String CREATE_MEAS_TABLE = "CREATE TABLE "
            + MEAS_TABLE + "("
            + MEAS_ID + " INTEGER PRIMARY KEY, "
            + MEAS_SYSTOLIC + " INTEGER, "
            + MEAS_DIASTOLIC + " INTEGER, "
            + MEAS_PULSE + " INTEGER, "
            + MEAS_TEMPERATURE + " NUMERIC, "
            + MEAS_WEIGHT + " INTEGER, "
            + MEAS_MEASURED_ON + " DATE, "
            + MEAS_MEASURED_COMMENT + " TEXT "
            + ")";

    public static final String CATS_TABLE = "cats_table";
    public static final String CATS_ID = "cats_id";
    public static final String CATS_CATEGORIES = "cats_categories";
    public static final String CATS_CODE = "cats_code";
    public static final String CATS_DESC = "cats_desc";
    public static final String CATS_REMINDERS_ENABLED = "cats_reminders_enabled";

    public static final String CREATE_CATS_TABLE = "CREATE TABLE "
            + CATS_TABLE + "("
            + CATS_ID + " INTEGER PRIMARY KEY, "
            + CATS_CATEGORIES + " TEXT, "
            + CATS_CODE + " TEXT, "
            + CATS_DESC + " TEXT, "
            + CATS_REMINDERS_ENABLED + " INTEGER "
            + ")";

    public static final String REMINDERS_TABLE = "reminders_table";
    public static final String REMINDERS_ID = "reminders_id";
    public static final String REMINDERS_FREQ = "reminders_freq";
    public static final String REMINDERS_START_TIME = "reminders_start_time";
    public static final String REMINDERS_INTERVAL = "reminders_interval";

    public static final String CREATE_REMINDERS_TABLE = "CREATE TABLE "
            + REMINDERS_TABLE + "("
            + REMINDERS_ID + " INTEGER PRIMARY KEY, "
            + REMINDERS_FREQ + " INTEGER, "
            + REMINDERS_START_TIME + " DATE, "
            + REMINDERS_INTERVAL + " INTEGER "
            + ")";

    public static final String MEDI_TABLE = "medi_table";
    public static final String MEDI_ID = "medi_id";
    public static final String MEDI_NAME = "medi_name";
    public static final String MEDI_DESC = "medi_desc";
    public static final String MEDI_CAT_ID = "medi_cat_id";
    public static final String MEDI_REMINDER_ID = "medi_reminder_id";
    public static final String MEDI_REMIND_ENABLED = "medi_reminder_enabled";
    public static final String MEDI_QUANTITY = "medi_quantity";
    public static final String MEDI_DOSAGE = "medi_dosage";
    public static final String MEDI_CONSUME_QUANTITY = "medi_consume_quantity";
    public static final String MEDI_THRESHOLD = "medi_threshold";
    public static final String MEDI_DATE_ISSUED = "medi_date_issued";
    public static final String MEDI_EXPIRE_FACTOR = "medi_expire_factor";

    public static final String CREATE_MEDI_TABLE = "CREATE TABLE "
            + MEDI_TABLE + "("
            + MEDI_ID + " INTEGER PRIMARY KEY, "
            + MEDI_NAME + " TEXT, "
            + MEDI_DESC + " TEXT, "
            + MEDI_CAT_ID + " INTEGER, "
            + MEDI_REMINDER_ID + " INTEGER, "
            + MEDI_REMIND_ENABLED + " INTEGER, "
            + MEDI_QUANTITY + " INTEGER, "
            + MEDI_DOSAGE + " INTEGER, "
            + MEDI_CONSUME_QUANTITY + " INTEGER, "
            + MEDI_THRESHOLD + " INTEGER, "
            + MEDI_DATE_ISSUED + " DATE, "
            + MEDI_EXPIRE_FACTOR + " INTEGER, "
            + "FOREIGN KEY(" + MEDI_CAT_ID + ") REFERENCES " + CATS_TABLE + "(cats_id), "
            + "FOREIGN KEY(" + MEDI_REMINDER_ID + ") REFERENCES " + REMINDERS_TABLE + "(reminders_id) "
            + ")";

    public static final String CONSUMPTION_TABLE = "Consumption";
    public static final String CON_ID = "con_id";
    public static final String CON_MEDICINE_ID = "con_med_id";
    public static final String CON_QUANTITY = "con_quantity";
    public static final String CON_CONSUMED_ON = "con_consumed_on";


    public static final String CREATE_CONSUMPTION_TABLE = "CREATE TABLE "
            + CONSUMPTION_TABLE + "("
            + CON_ID + " INTEGER PRIMARY KEY, "
            + CON_MEDICINE_ID + " INTEGER, "
            + CON_QUANTITY + " INTEGER, "
            + CON_CONSUMED_ON + " DATE, "
            + "FOREIGN KEY(" + CON_MEDICINE_ID + ") REFERENCES " + MEDI_TABLE + "(medi_id) "
            + ")";

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PERSONAL_BIO_TABLE);
        db.execSQL(CREATE_APPOINTMENT_TABLE);
        db.execSQL(CREATE_CONSUMPTION_TABLE);
        db.execSQL(CREATE_HEALTH_BIO_TABLE);
        db.execSQL(CREATE_CATS_TABLE);

        String cat_str = "INSERT INTO " + CATS_TABLE + " ("
                + CATS_CATEGORIES + ", "
                + CATS_CODE + ", "
                + CATS_DESC + ", "
                + CATS_REMINDERS_ENABLED +
                ") Values ('Supplement', 'SUP', 'User may opt to set reminder for consumption of supplement.', '0')";
        db.execSQL(cat_str);

        cat_str = "INSERT INTO " + CATS_TABLE + " ("
                + CATS_CATEGORIES + ", "
                + CATS_CODE + ", "
                + CATS_DESC + ", "
                + CATS_REMINDERS_ENABLED +
                ") Values ('Chronic', 'CHR', 'This is to categorise medication for long-term/life-time consumption for diseases, i.e. diabetes, hypertension, heart regulation, etc.', '1')";
        db.execSQL(cat_str);

        cat_str = "INSERT INTO " + CATS_TABLE + " ("
                + CATS_CATEGORIES + ", "
                + CATS_CODE + ", "
                + CATS_DESC + ", "
                + CATS_REMINDERS_ENABLED +
                ") Values ('Incidental', 'INC', 'For common cold, flu or symptoms happen to be unplanned or subordinate conjunction with something and prescription from general practitioners.', '1')";
        db.execSQL(cat_str);

        cat_str = "INSERT INTO " + CATS_TABLE + " ("
                + CATS_CATEGORIES + ", "
                + CATS_CODE + ", "
                + CATS_DESC + ", "
                + CATS_REMINDERS_ENABLED +
                ") Values ('Complete Course', 'COM', 'This may applies to medication like antibiotics for sinus infection, pneumonia, bronchitis, acne, strep throat, cellulitis, etc.', '1')";
        db.execSQL(cat_str);

        cat_str = "INSERT INTO " + CATS_TABLE + " ("
                + CATS_CATEGORIES + ", "
                + CATS_CODE + ", "
                + CATS_DESC + ", "
                + CATS_REMINDERS_ENABLED +
                ") Values ('Self Apply', 'SEL', 'To note down any self-prescribed or consume medication, i.e. applying band aids, balms, etc.', '-1')";
        db.execSQL(cat_str);

        db.execSQL(CREATE_MEDI_TABLE);
        db.execSQL(CREATE_ICE_TABLE);
        db.execSQL(CREATE_MEAS_TABLE);
        db.execSQL(CREATE_REMINDERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + PERSONAL_BIO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + APPOINTMENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CONSUMPTION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + HEALTH_BIO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CATS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MEDI_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ICE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MEAS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + REMINDERS_TABLE);
        onCreate(db);
    }
}
