package com.example.myapplication.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.myapplication.Controller.WorkoutPlanDatabase;
import com.example.myapplication.Model.Exercise;
import com.example.myapplication.Model.WorkoutModel;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.ExerciseListAdapter;
import com.example.myapplication.Utilities.MultiSelectAdapter;

import java.util.ArrayList;

public class PlanExercisesActivity extends FragmentActivity {

    WorkoutPlanDatabase db;
    SharedPreferences presetPlan;
    RecyclerView recyclerView;
    ExerciseListAdapter exerciseListAdapter;

    private ArrayList<Exercise> exereciseList = new ArrayList<>();
    private ArrayList<Exercise> multiselect_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_exercises);
        recyclerView = findViewById(R.id.planList);
        db = new WorkoutPlanDatabase(this, 1);
        presetPlan = getSharedPreferences("workoutList", MODE_PRIVATE);
        SharedPreferences.Editor edit = presetPlan.edit();

        String plan = getIntent().getStringExtra("plan");
        edit.putString("plan", plan);
        edit.apply();

        exerciseListAdapter = new ExerciseListAdapter(this, exereciseList, multiselect_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(exerciseListAdapter);


    }

    public void goBack(View view) {
        // There is a better way, check activity stacks and remove.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void add_Exercise(View view) {
        ActivityFragment exerciseFragment = new ActivityFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.add_fragment_container, exerciseFragment);
        transaction.commit();
    }
}


