package com.example.myapplication.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.Model.CreatedWorkout;

import java.util.ArrayList;

public class WorkoutPlanDatabase extends SQLiteOpenHelper {

    Context context;
    private static int VERSION = 1;
    private static String DATABASE_NAME = "WORKOUT_PLANS";
    private static String DATABASE_TABLE = "WORKOUT_TABLE";

    private static WorkoutPlanDatabase wPlanDb;

    static String COL_ID = "col_id";
    static String COL_DAY = "col_day";
    static String COL_NAME = "col_name";
    static String COL_TOTAL_EX = "total_ex";

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

    /*private static class PlanTable {
        private static final String TABLE = "plans";
        private static final String COL_DAY = "day";
        private static final String COL_NAME = "name";
        private static final String COL_TOTAL_EX = "total exercises";
    }*/

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAN_TABLE = "CREATE TABLE " + DATABASE_TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY,"
                + COL_DAY + " STRING,"
                + COL_NAME + " STRING,"
                + COL_TOTAL_EX + " INTEGER )";
        db.execSQL(CREATE_PLAN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (VERSION == oldVersion) {
            VERSION = newVersion;
            db = getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public void addPlan(CreatedWorkout createdWorkout) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DAY, createdWorkout.getDay_of_week());
        values.put(COL_NAME, createdWorkout.getName());
        values.put(COL_TOTAL_EX, createdWorkout.getTotal_workouts());

        db.insert(DATABASE_TABLE, null, values);
        //Log.d("Database", "PLAN ADDED");
        db.close();
    }

    public ArrayList<CreatedWorkout> showPlan() {
        ArrayList<CreatedWorkout> cwList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
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
        db.close();
        return cwList;
    }

    public void deletePlan(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, "COL_ID =" + id, null);
        db.close();
    }
}
