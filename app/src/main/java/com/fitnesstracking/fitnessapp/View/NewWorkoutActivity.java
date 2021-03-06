package com.fitnesstracking.fitnessapp.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnesstracking.fitnessapp.Controller.WorkoutPlanDatabase;
import com.fitnesstracking.fitnessapp.Model.CreatedWorkout;
import com.fitnesstracking.fitnessapp.R;

public class NewWorkoutActivity extends AppCompatActivity {

    Intent intent = new Intent();
    EditText planName;
    WorkoutPlanDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newplan_activity);
        Toolbar toolbar = findViewById(R.id.toolbar_layout);
        planName = findViewById(R.id.create_plan_name);
        db = new WorkoutPlanDatabase(this, 1);
        setSupportActionBar(toolbar);





        getSupportActionBar().setTitle("Create Workout Plan");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final Spinner daySpinner = findViewById(R.id.days_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        daySpinner.setAdapter(adapter);

        final String[] days = getResources().getStringArray(R.array.days);

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TO-DO; Pass the value to the home fragment.
                String selected = daySpinner.getSelectedItem().toString();
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                intent.putExtra("planDay", selected);
                Log.d("Spinner Select", "Value is: " + selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Toolbar menu.
        getMenuInflater().inflate(R.menu.newplan_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_save:
                if (planName.length() == 0) {
                    Toast.makeText(getApplicationContext(),"Please enter a name for your plan",Toast.LENGTH_SHORT).show();
                } else {
                    String day = intent.getStringExtra("planDay");
                    CreatedWorkout cw = new CreatedWorkout();
                    cw.setDay_of_week(day);
                    cw.setName(planName.getText().toString());
                    cw.setTotal_workouts(0);
                    db.addPlan(cw);

                    setResult(RESULT_OK, intent);
                    finish();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
