package com.example.myapplication;

import android.content.Intent;
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

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class SetsRepsActivity extends AppCompatActivity {
    double weight = 0.0;
    int reps = 0;
    private Toolbar toolBar;
    EditText weightValue, repValue;
    String value;
    int finalValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets_reps);

        toolBar = findViewById(R.id.setsreps_layout);
        weightValue = findViewById(R.id.weight_value);
        repValue = findViewById(R.id.rep_value);
        setSupportActionBar(toolBar);

        Bundle extras = getIntent().getExtras();
        String excerciseName = extras.getString("exerciseName");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setTitle(excerciseName);
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
        repValue.setText("0.0");
        weight = 0;
        reps = 0;
    }

    public void save(View view) {
        if (weight == 0 || reps == 0) {
            Toast.makeText(this, "Please enter a value for this set", Toast.LENGTH_SHORT).show();
            return;
        }

        /*
        TextView index = findViewById(R.id.index);
        TextView weights = findViewById(R.id.weights);
        TextView reps = findViewById(R.id.reps);

        index.setText("" +index);
        index.setText("" + weights);
        index.setText("" + reps);
        */

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
