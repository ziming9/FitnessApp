package com.example.myapplication.Controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class WorkoutPlanDatabase extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "workout_plan.db";

    private static WorkoutPlanDatabase wPlanDb;

    public enum PlanSortOrder {ALPHABETIC, UPDATE_DESC, UPDATE_ASC};

    public static WorkoutPlanDatabase getInstance(Context context) {
        if (wPlanDb == null) {
            wPlanDb = new WorkoutPlanDatabase(context);
        }
        return wPlanDb;
    }

    public WorkoutPlanDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final class PlanTable {
        private static final String TABLE = "plans";
        private static final String COL_DAY = "day";
        private static final String COL_NAME = "name";
        private static final String COL_TOTAL_EX = "total exercises";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PlanTable.TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
