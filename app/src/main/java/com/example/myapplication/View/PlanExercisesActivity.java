package com.example.myapplication.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.ActivityFragment;
import com.example.myapplication.Controller.WorkoutPlanDatabase;
import com.example.myapplication.R;

public class PlanExercisesActivity extends FragmentActivity {

    WorkoutPlanDatabase db;
    SharedPreferences presetPlan;
    boolean plan_selected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_exercises);
        db = new WorkoutPlanDatabase(this, 1);
        presetPlan = getSharedPreferences("workoutList", MODE_PRIVATE);
        SharedPreferences.Editor edit = presetPlan.edit();

        String plan = getIntent().getStringExtra("plan");
        edit.putString("plan", plan);
        edit.apply();
    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void add_Exercise(View view) {
        ActivityFragment exerciseFragment = new ActivityFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.add_fragment_container, exerciseFragment);
        transaction.commit();
    }
}


