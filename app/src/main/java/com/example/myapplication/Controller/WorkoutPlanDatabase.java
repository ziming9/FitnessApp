package com.example.myapplication.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.Model.CreatedWorkout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WorkoutPlanDatabase extends SQLiteOpenHelper {

    Context context;
    private static int VERSION = 1;
    private static String DATABASE_NAME = "WORKOUT_PLANS.db";
    private static String DATABASE_TABLE = "WORKOUT_TABLE";
    private static String EXERCISE_TABLE = "EXERCISE_TABLE";
    private static String EXERCISE_LOG_TABLE = "EXERCISE_LOG_TABLE";

    private static WorkoutPlanDatabase wPlanDb;

    static String COL_ID = "plan_id";
    static String COL_DAY = "day";
    static String COL_NAME = "plan_name";
    static String COL_TOTAL_EX = "total_ex";

    static String COL_EX_ID = "exercise_id";
    static String COL_EX_NAME = "exercise_name";

    static String COL_WEIGHT = "weight";
    static String COL_REPS = "reps";
    static String COL_REPMAX = "onerep_max";
    static String COL_DATE = "date_created";

    //public enum PlanSortOrder {ALPHABETIC, UPDATE_DESC, UPDATE_ASC};

    public static WorkoutPlanDatabase getInstance(Context context, int version) {
        if (wPlanDb == null) {
            wPlanDb = new WorkoutPlanDatabase(context, version);
        }
        return wPlanDb;
    }

    public WorkoutPlanDatabase(Context context, int version) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
        VERSION = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAN_TABLE = "CREATE TABLE " + DATABASE_TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY,"
                + COL_DAY + " STRING,"
                + COL_NAME + " STRING,"
                + COL_TOTAL_EX + " INTEGER )";

        String CREATE_EXERCISE_TABLE = "CREATE TABLE " + EXERCISE_TABLE + " ("
                + COL_EX_ID + " INTEGER PRIMARY KEY,"
                + COL_ID + " INTEGER,"
                + COL_EX_NAME + " STRING )";

        String CREATE_EXERCISE_LOG_TABLE = "CREATE TABLE " + EXERCISE_LOG_TABLE + " ("
                + COL_EX_ID + " INTEGER PRIMARY KEY,"
                + COL_WEIGHT + " FLOAT,"
                + COL_REPS + " INTEGER,"
                + COL_REPMAX + " FLOAT,"
                + COL_DATE + " DATETIME )";

        db.execSQL(CREATE_PLAN_TABLE);
        db.execSQL(CREATE_EXERCISE_TABLE);
        db.execSQL(CREATE_EXERCISE_LOG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (VERSION == oldVersion) {
            VERSION = newVersion;
            db = getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_LOG_TABLE);

            onCreate(db);
        }
    }

    public void addPlan(CreatedWorkout createdWorkout) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DAY, createdWorkout.getDay_of_week());
        values.put(COL_NAME, createdWorkout.getName());
        values.put(COL_TOTAL_EX, createdWorkout.getTotal_workouts());

        Log.d("ID", ""+createdWorkout.getID());
        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }

    public ArrayList<CreatedWorkout> showPlan() {
        ArrayList<CreatedWorkout> cwList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE + " ORDER BY CASE "
                    + "WHEN COL_DAY = 'Sunday' THEN 1 "
                    + "WHEN COL_DAY = 'Monday' THEN 2 "
                    + "WHEN COL_DAY = 'Tuesday' THEN 3 "
                    + "WHEN COL_DAY = 'Wednesday' THEN 4 "
                    + "WHEN COL_DAY = 'Thursday' THEN 5 "
                    + "WHEN COL_DAY = 'Friday' THEN 6 "
                    + "WHEN COL_DAY = 'Saturday' THEN 7 "
                    + "END ASC", null );
            if (cursor.moveToFirst()) {
                do {
                    CreatedWorkout cw = new CreatedWorkout();
                    cw.setID(Integer.parseInt(cursor.getString(0)));
                    cw.setDay_of_week(cursor.getString(1));
                    cw.setName(cursor.getString(2));
                    cw.setTotal_workouts(Integer.parseInt(cursor.getString(3)));
                    cwList.add(cw);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            cwList = null;
        }

        db.close();
        return cwList;
    }

    public void deletePlan(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, "COL_ID =" + id, null);
        db.close();
    }

    /*public void createExerciseTable() {
        ArrayList<CreatedWorkout> cwList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM " + DATABASE_TABLE, null);
        if (cursor.moveToFirst()) {
            do {
                CreatedWorkout cw = new CreatedWorkout();
                cw.setID(Integer.parseInt(cursor.getString(0)));
                cwList.add(cw);
            } while (cursor.moveToNext());
        }

        *//*for(CreatedWorkout cw:cwList) {
            String TABLE_NAME = String.valueOf(cw.getID());
            String CREATE_INNER_PLAN = "CREATE TABLE " + TABLE_NAME + " ("
                    + COL_EX_NAME + " STRING )";
            db.execSQL(CREATE_INNER_PLAN);
        }*//*
    }*/

    public void addExercise(int id, String exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ID, id);
        values.put(COL_EX_NAME, exercise);
        db.insert(EXERCISE_TABLE, null, values);
        db.close();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
