package com.example.myapplication.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Controller.WorkoutPlanDatabase;
import com.example.myapplication.Model.Exercise;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.ExerciseListAdapter;
import com.example.myapplication.Utilities.ExerciseLogAdapter;
import com.example.myapplication.Utilities.RecyclerItemClickListener;

import java.util.ArrayList;

public class ExerciseLogActivity extends AppCompatActivity {

    WorkoutPlanDatabase db;
    SharedPreferences presetPlan;
    ExerciseLogAdapter exerciseLogAdapter;
    RecyclerView recyclerView;
    NumberPicker weight_np, rep_np;
    private Toolbar toolBar;
    private TextView tv;

    int weight, rep;

    private ArrayList<Exercise> exereciseLog = new ArrayList<>();
    private ArrayList<Exercise> multiselect_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_log);
        recyclerView = findViewById(R.id.exercise_log_list);
        toolBar = findViewById(R.id.exlog_toolbar);
        tv = findViewById(R.id.exercise_name);

        weight_np = findViewById(R.id.weight_np);
        rep_np = findViewById(R.id.rep_np);

        db = new WorkoutPlanDatabase(this, 1);
        presetPlan = getSharedPreferences("workoutList", MODE_PRIVATE);
        SharedPreferences.Editor edit = presetPlan.edit();

        final String plan = getIntent().getStringExtra("plan");
        final String exerciseName = getIntent().getStringExtra("exerciseName");
        edit.putString("plan", plan);
        edit.apply();

        tv.setText(exerciseName);
        setSupportActionBar(toolBar);

        weight_np.setMinValue(0);
        weight_np.setMaxValue(995);
        weight_np.setWrapSelectorWheel(false);

        rep_np.setMinValue(0);
        rep_np.setMaxValue(100);
        rep_np.setWrapSelectorWheel(false);

        NumberPicker.Formatter formatter = new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                int temp = value * 5;
                return "" + temp;
            }
        };

        weight_np.setFormatter(formatter);

        weight_np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                weight = newVal*5;
            }
        });

        rep_np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                rep = newVal;
            }
        });

        if(plan != null) {
            exereciseLog = db.showExerciseLog(Integer.valueOf(plan), exerciseName);
            exerciseLogAdapter = new ExerciseLogAdapter(this, exereciseLog, multiselect_list);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(exerciseLogAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exercise_log_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.toolbar_edit:
                Toast.makeText(this, "Still have to make this", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void addToLog(View view) {
        String plan = getIntent().getStringExtra("plan");
        String exerciseName = getIntent().getStringExtra("exerciseName");
        float repMax = (float) (weight * (1 + rep / 30));
        long ex_id = db.getExID(Integer.valueOf(plan), exerciseName);
        Exercise exLog = new Exercise();
        exLog.setWeight(weight);
        exLog.setReps(rep);
        exLog.setMax(repMax);

        db.addExerciseLog(exLog, ex_id);
        refresh();
    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void refresh() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}
