package com.example.healthcareapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HealthcareDB";
    private static final int DATABASE_VERSION = 6;

    // Appointments Table
    private static final String TABLE_APPOINTMENTS = "appointments";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PATIENT_NAME = "patient_name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_MESSAGE = "message";
    private static final String COL_SPECIALIZATION = "specialization";
    private static final String COLUMN_DOCTOR = "doctor";
    private static final String COLUMN_SERVICE = "service";
    private static final String COLUMN_STATUS = "status";

    // doctor Table
    private static final String TABLE_DOCTORS = "doctors";
    private static final String COLUMN_DOCTOR_ID = "did";
    private static final String COLUMN_DOCTOR_NAME = "name";
    private static final String COLUMN_DOCTOR_SPECIALIZATION = "specialization";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_WORKINGHOUR = "workingHour";
    private static final String COLUMN_IMAGEID = "ImageResId";
    private static final String COLUMN_SERVICES = "service";

     //patient table
    private static final String TABLE_PATIENT = "Patients";
    private static final String COL_ID      = "id";
    private static final String COL_NAME    = "name";
    private static final String COL_PHONE   = "phone";
    private static final String COL_EMAIL   = "email";
    private static final String COL_DOB     = "dob";
    private static final String COL_CARD     = "card";
    private static final String COL_ADDRESS = "address";
    private static final String COL_GENDER  = "gender";

    //users table
    private static final String TABLE_USERS = "users";
    private static final String CON_ID      = "id";
    private static final String CON_NAME    = "name";
    private static final String CON_EMAIL   = "email";
    private static final String CON_PHONE   = "phone";
    private static final String CON_PASSWORD     = "password";

    //Medications Table
    public static final String TABLE_MEDICATIONS = "medications";
    public static final String COLM_ID = "id";
    public static final String COLM_NAME = "name";
    public static final String COLM_DOSAGE = "dosage";
    public static final String COLM_FREQUENCY = "frequency";
    public static final String COLM_TIME = "time";
    public static final String COLM_INSTRUCTIONS = "instructions";
    public static final String COLM_START_DATE = "start_date";
    public static final String COLM_END_DATE = "end_date";
    public static final String COLM_MEAL_TIME = "meal_time";
    public static final String COLM_SUPPLY = "supply";
    public static final String COLM_TAKEN = "taken";

    // Dose Logs Table
    public static final String TABLE_LOGS = "dose_logs";
    public static final String COL_LOG_ID = "log_id";
    public static final String COL_MED_ID = "med_id";
    public static final String COL_TIMESTAMP = "timestamp";
    public static final String COL_STATUS = "status";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_APPOINTMENTS_TABLE = "CREATE TABLE " + TABLE_APPOINTMENTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PATIENT_NAME + " TEXT,"
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_TIME + " TEXT,"
                + COLUMN_MESSAGE + " TEXT,"
                + COL_SPECIALIZATION + " TEXT,"
                + COLUMN_DOCTOR + " TEXT,"
                + COLUMN_SERVICE + " TEXT,"
                + COLUMN_STATUS + " TEXT)";
        db.execSQL(CREATE_APPOINTMENTS_TABLE);
        String CREATE_DOCTORS_TABLE = "CREATE TABLE " + TABLE_DOCTORS + "("
                + COLUMN_DOCTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DOCTOR_NAME + " TEXT, "
                + COLUMN_DOCTOR_SPECIALIZATION + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_RATING + " REAL, "
                + COLUMN_WORKINGHOUR + " TEXT, "
                + COLUMN_SERVICES + " TEXT, "
                + COLUMN_IMAGEID + " INTEGER)";
        db.execSQL(CREATE_DOCTORS_TABLE);
        String sql = "CREATE TABLE " + TABLE_PATIENT + " ("
                + COL_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME    + " TEXT NOT NULL, "
                + COL_PHONE   + " TEXT UNIQUE NOT NULL, "
                + COL_EMAIL   + " TEXT, "
                + COL_DOB     + " TEXT, "
                + COL_CARD    + " INTEGER, "
                + COL_ADDRESS + " TEXT, "
                + COL_GENDER  + " TEXT"
                + ")";
        db.execSQL(sql);

        String sq = "CREATE TABLE " + TABLE_USERS + " ("
                + CON_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CON_NAME    + " TEXT NOT NULL, "
                + CON_EMAIL   + " TEXT UNIQUE NOT NULL, "
                + CON_PHONE   + " TEXT UNIQUE NOT NULL, "
                + CON_PASSWORD     + " TEXT NOT NULL"
                + ")";
        db.execSQL(sq);

        String CREATE_MEDICATIONS_TABLE = "CREATE TABLE " + TABLE_MEDICATIONS + "("
                + COLM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLM_NAME + " TEXT,"
                + COLM_DOSAGE + " TEXT,"
                + COLM_FREQUENCY + " TEXT,"
                + COLM_TIME + " TEXT,"
                + COLM_INSTRUCTIONS + " TEXT,"
                + COLM_START_DATE + " LONG,"
                + COLM_END_DATE + " LONG,"
                + COLM_MEAL_TIME + " TEXT,"
                + COLM_SUPPLY + " INTEGER,"
                + COLM_TAKEN + " INTEGER DEFAULT 0)";
        db.execSQL(CREATE_MEDICATIONS_TABLE);

        String CREATE_LOGS_TABLE = "CREATE TABLE " + TABLE_LOGS + "("
                + COL_LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_MED_ID + " INTEGER,"
                + COL_TIMESTAMP + " LONG,"
                + COL_STATUS + " TEXT)";

        db.execSQL(CREATE_LOGS_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGS);
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_DOCTORS + " ADD COLUMN " + COLUMN_WORKINGHOUR + " TEXT");
        }
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE " + TABLE_PATIENT + " ADD COLUMN " + COL_CARD + " INTEGER");
        }
        onCreate(db);
    }

    public long addAppointment(String patientName,String phone,String date,String time,String message,String specialization,String doctor,String service,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        String appointment = " INSERT INTO " + TABLE_APPOINTMENTS + "(" +
                COLUMN_PATIENT_NAME + ", " +
                COLUMN_PHONE + ", " +
                COLUMN_DATE + ", " +
                COLUMN_TIME + ", " +
                COLUMN_MESSAGE + ","+
                COL_SPECIALIZATION + ", " +
                COLUMN_DOCTOR + ", " +
                COLUMN_SERVICE + ", " +
                COLUMN_STATUS + ") VALUES (?,?,?,?,?,?,?,?,?)";
        SQLiteStatement st = db.compileStatement(appointment);
        st.bindString(1, patientName);
        st.bindString(2,phone);
        st.bindString(3, date);
        st.bindString(4, time);
        st.bindString(5,message);
        st.bindString(6,specialization);
        st.bindString(7,doctor);
        st.bindString(8,service);
        st.bindString(9,status);
        long id = st.executeInsert();
        db.close();
        return id;
    }

    public void insertDoctor(String name, String specialization, String description, float rating, String workingHour, String service, int imageResId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO " + TABLE_DOCTORS + " (" +
                COLUMN_DOCTOR_NAME + ", " +
                COLUMN_DOCTOR_SPECIALIZATION + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_RATING + ", " +
                COLUMN_WORKINGHOUR + ", " +
                COLUMN_SERVICES + ", " +
                COLUMN_IMAGEID + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, name);
        statement.bindString(2, specialization);
        statement.bindString(3, description);
        statement.bindDouble(4, rating);
        statement.bindString(5, workingHour);
        statement.bindString(6, service);
        statement.bindLong(7, imageResId);
        statement.executeInsert();
        db.close();
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctorList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DOCTORS, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_NAME));
                String specialization = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_SPECIALIZATION));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                float rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING));
                String service = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICES));
                String workingHour = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WORKINGHOUR));
                int ImageresId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGEID));
                doctorList.add(new Doctor(id, name, specialization, description, rating, workingHour, ImageresId,service));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return doctorList;
    }
    //Inserts a new patient record;
    public long addPatient(String name, String phone, String email, String dob,int card, String address, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO " + TABLE_PATIENT + " (" +
                COL_NAME + ", " +
                COL_PHONE + ", " +
                COL_EMAIL + ", " +
                COL_DOB + ", " +
                COL_CARD + ", "+
                COL_ADDRESS + ", " +
                COL_GENDER + ") VALUES (?, ?, ?, ?, ?, ?,?)";
        SQLiteStatement use = db.compileStatement(sql);
        use.bindString(1, name);
        use.bindString(2, phone);
        use.bindString(3, email);
        use.bindString(4, dob);
        use.bindLong(5, card);
        use.bindString(6, address);
        use.bindString(7, gender);
        long result = use.executeInsert();
        db.close();
        return result;
    }
    public patient getPatient(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PATIENT,
                new String[]{COL_NAME, COL_PHONE, COL_EMAIL, COL_DOB, COL_CARD, COL_ADDRESS, COL_GENDER},
                COL_PHONE + "=?",
                new String[]{phone}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            patient patient = new patient(
                    cursor.getString(0), // name
                    cursor.getString(1), // phone
                    cursor.getString(2), // email
                    cursor.getString(3), // dob
                    cursor.getInt(4),    // card
                    cursor.getString(5),// address
                    cursor.getString(6)  // gender
            );
            cursor.close();
            return patient;
        }
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }
    public long addUser(String name, String email, String phone, String password) {
        if (isUserExists(email, phone)) {
            return -1; // User already exists
        }
        SQLiteDatabase db = this.getWritableDatabase();
        String user = "INSERT INTO " +TABLE_USERS + " (" +
                CON_NAME + ", " +
                CON_EMAIL + ", " +
                CON_PHONE + ", " +
                CON_PASSWORD + ") VALUES (?,?,?,?)";
        SQLiteStatement use = db.compileStatement(user);
        use.bindString(1, name);
        use.bindString(2, email);
        use.bindString(3, phone);
        use.bindString(4, password);
        long id = use.executeInsert();
        db.close();
        return id;
    }
    public boolean isUserExists(String email,String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{CON_ID},
                CON_EMAIL + "=? OR " + CON_PHONE + "=?",
                new String[]{email, phone},
                null, null, null);
        boolean exists = (cursor != null && cursor.moveToFirst());
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return exists;
    }
    public boolean checkUserCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {CON_ID};
        String selection = CON_EMAIL + " = ? AND " + CON_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs,
                null, null, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }
    public long addMedication(String name, String dosage, String frequency, String time, String instructions,
                              long startDate, long endDate, String mealTime, int supply) {
        SQLiteDatabase db = this.getWritableDatabase();
        String drug = "INSERT INTO " +TABLE_MEDICATIONS + " (" +
                COLM_NAME + ", " +
                COLM_DOSAGE + ", " +
                COLM_FREQUENCY + ", " +
                COLM_TIME + ", " +
                COLM_INSTRUCTIONS + ", " +
                COLM_START_DATE + ", " +
                COLM_END_DATE + ", " +
                COLM_MEAL_TIME + ", " +
                COLM_SUPPLY + ") VALUES (?,?,?,?,?,?,?,?,?)";
        SQLiteStatement med = db.compileStatement(drug);
        med.bindString(1, name);
        med.bindString(2, dosage);
        med.bindString(3, frequency);
        med.bindString(4, time);
        med.bindString(5, instructions);
        med.bindLong(6, startDate);
        med.bindLong(7, endDate);
        med.bindString(8, mealTime);
        med.bindLong(9, supply);
        long id = med.executeInsert();
        db.close();
        return id;
    }
    public void updateDoseStatus(int medId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLM_TAKEN, getTakenCount(medId) + 1);
        db.update(TABLE_MEDICATIONS, values, COL_ID + " = ?", new String[]{String.valueOf(medId)});
    }
    public void addDoseLog(int medId, long timestamp, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_MED_ID, medId);
        values.put(COL_TIMESTAMP, timestamp);
        values.put(COL_STATUS, status);
        db.insert(TABLE_LOGS, null, values);
    }
    public int getTakenCount(int medId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLM_TAKEN + " FROM " + TABLE_MEDICATIONS +
                " WHERE " + COL_ID + " = " + medId, null);
        return cursor.moveToFirst() ? cursor.getInt(0) : 0;
    }

    public Cursor getLowSupplyMeds() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_MEDICATIONS +
                " WHERE " + COLM_SUPPLY + " - " + COLM_TAKEN + " <= 3", null);
    }
}


