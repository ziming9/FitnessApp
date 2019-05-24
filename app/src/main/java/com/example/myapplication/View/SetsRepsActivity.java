package com.example.myapplication.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Controller.WorkoutPlanDatabase;
import com.example.myapplication.Model.CreatedWorkout;
import com.example.myapplication.Model.Exercise;
import com.example.myapplication.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class SetsRepsActivity extends AppCompatActivity {
    double weight = 0.0;
    int reps = 0;
    private Toolbar toolBar;
    EditText weightValue, repValue;
    String value;
    int finalValue;
    SharedPreferences sh;
    WorkoutPlanDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets_reps);

        toolBar = findViewById(R.id.setsreps_toolbar);
        weightValue = findViewById(R.id.weight_value);


        repValue = findViewById(R.id.rep_value);

        // Set color and default values
        weightValue.setTextColor(getResources().getColor(R.color.colorWhite));
        weightValue.setText("0.0");
        repValue.setTextColor(getResources().getColor(R.color.colorWhite));
        repValue.setText("0");

        setSupportActionBar(toolBar);
        toolBar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        sh = getSharedPreferences("workoutList", MODE_PRIVATE);
        db = new WorkoutPlanDatabase(this, 1);

        Bundle extras = getIntent().getExtras();
        String exerciseName = extras.getString("exerciseName");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setTitle(exerciseName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void increaseWeight(View view) {
        value = weightValue.getText().toString();

        Log.d("Integer", "Value: " + value);
        try {
            finalValue = Integer.parseInt(value);
            weight = finalValue;
        } catch (NumberFormatException ex){
            Log.d("TryCatch", "value is null");
        }

        weight = weight + 5.0;
        weightValue.setText(Double.toString(weight));

    }

    public void decreaseWeight(View view) {
        value = weightValue.getText().toString();

        try {
            finalValue = Integer.parseInt(value);
            weight = finalValue;
        } catch (NumberFormatException ex){
            Log.d("TryCatch", "value is null");
        }

        weight = weight - 5.0;
        if (weight < 0) {
            weight = 0.0;
        }
        weightValue.setText(Double.toString(weight));

    }

    public void increaseRep(View view) {
        value = repValue.getText().toString();

        try {
            finalValue = Integer.parseInt(value);
            reps = finalValue;
        } catch (NumberFormatException ex){
            Log.d("TryCatch", "value is null");
        }

        reps++;
        repValue.setText(Integer.toString(reps));
    }

    public void decreaseRep(View view) {
        value = repValue.getText().toString();

        try {
            finalValue = Integer.parseInt(value);
            reps = finalValue;
        } catch (NumberFormatException ex){
            Log.d("TryCatch", "value is null");
        }

        reps--;
        if (reps < 0) {
            reps = 0;
        }
        repValue.setText(Integer.toString(reps));
    }

    public void clear(View view) {
        weightValue.setText("0.0");
        repValue.setText("0");
        weight = 0;
        reps = 0;
    }

    public void save(View view) {
        boolean addProcess;
        SharedPreferences.Editor edit = sh.edit();
        Bundle extras = getIntent().getExtras();
        String exerciseName = extras.getString("exerciseName");
        edit.putString("savedExercise", exerciseName);
        edit.apply();
        String plan = sh.getString("plan", null);
        float repMax = (float) (weight * (1 + reps / 30));
        Exercise newExercise = new Exercise(exerciseName, reps, weight, repMax);
        addProcess = db.addExercise(Integer.valueOf(plan), exerciseName, newExercise);

        if (weight == 0 || reps == 0) {
            Toast.makeText(this, "Please enter a value for this set", Toast.LENGTH_SHORT).show();
            return;
        }

        if (addProcess != false) {
            finish();
            Intent intent = new Intent(this, PlanExercisesActivity.class);
            startActivity(intent);
        }

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                String plan = sh.getString("plan", null);
                if (plan != null) {
                    Intent intent = new Intent(this, PlanExercisesActivity.class);
                    intent.putExtra("plan", plan);
                    startActivity(intent);
                } else {
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
