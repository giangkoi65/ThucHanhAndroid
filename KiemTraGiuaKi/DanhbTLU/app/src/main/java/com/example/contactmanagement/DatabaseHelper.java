package com.example.contactmanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contact_management.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_UNITS = "units";
    public static final String COLUMN_UNIT_ID = "unit_id";
    public static final String COLUMN_UNIT_NAME = "unit_name";
    public static final String COLUMN_UNIT_CONTACT = "unit_contact";

    public static final String TABLE_EMPLOYEES = "employees";
    public static final String COLUMN_EMPLOYEE_ID = "employee_id";
    public static final String COLUMN_EMPLOYEE_NAME = "employee_name";
    public static final String COLUMN_EMPLOYEE_CONTACT = "employee_contact";
    public static final String COLUMN_EMPLOYEE_EMAIL = "employee_email";
    public static final String COLUMN_EMPLOYEE_GENDER = "employee_gender";
    public static final String COLUMN_EMPLOYEE_UNIT_ID = "employee_unit_id";

    private static final String CREATE_TABLE_UNITS = "CREATE TABLE " + TABLE_UNITS + " ("
            + COLUMN_UNIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_UNIT_NAME + " TEXT NOT NULL, "
            + COLUMN_UNIT_CONTACT + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_EMPLOYEES = "CREATE TABLE " + TABLE_EMPLOYEES + " ("
            + COLUMN_EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_EMPLOYEE_NAME + " TEXT NOT NULL, "
            + COLUMN_EMPLOYEE_CONTACT + " TEXT NOT NULL, "
            + COLUMN_EMPLOYEE_EMAIL + " TEXT NOT NULL, "
            + COLUMN_EMPLOYEE_GENDER + " TEXT NOT NULL, "
            + COLUMN_EMPLOYEE_UNIT_ID + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_EMPLOYEE_UNIT_ID + ") REFERENCES " + TABLE_UNITS + "(" + COLUMN_UNIT_ID + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_UNITS);
        db.execSQL(CREATE_TABLE_EMPLOYEES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNITS);
        onCreate(db);
    }
}
