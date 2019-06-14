package com.fitnesstracking.fitnessapp.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fitnesstracking.fitnessapp.R;

import java.text.DecimalFormat;

public class ProfileActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    SharedPreferences sh;
    double weight;
    double goal;
    double percentage;
    EditText name, age, heightFT,heightIN;
    private static int REQUEST_CODE = 0;
    String nameCheckStart, heightFTCheckStart, heightINCheckStart, ageCheckStart;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        name = findViewById(R.id.enterName);
        age = findViewById(R.id.editAge);
        heightFT = findViewById(R.id.editHeightFt);
        heightIN = findViewById(R.id.editHeightIn);

        sh = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor edit  = sh.edit();

        nameCheckStart = sh.getString("nameCheck","");
        heightFTCheckStart = sh.getString("heightFTCheck","");
        heightINCheckStart = sh.getString("heightINCheck","");
        ageCheckStart = sh.getString("ageCheck","");

        //Log.d("nameCheck", nameCheckStart);

        if (nameCheckStart.length() != 0) {
            name.setText(sh.getString("nameCheck", null), TextView.BufferType.EDITABLE);
        }
        if(heightFTCheckStart.length() != 0) {
            heightFT.setText(sh.getString("heightFTCheck", null), TextView.BufferType.EDITABLE);
        }
        if(heightINCheckStart.length() != 0 ) {
            heightIN.setText(sh.getString("heightINCheck", null), TextView.BufferType.EDITABLE);
        }
        if(ageCheckStart.length() != 0) {
            age.setText(sh.getString("ageCheck", null), TextView.BufferType.EDITABLE);
        }





        Toolbar toolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);


        final EditText userWeightt =  findViewById(R.id.editWeight);
        final EditText userGoalWeightt =  findViewById(R.id.editGoalWeight);
        progressBar =  findViewById(R.id.progressBar);
        final TextView percentageText = findViewById(R.id.percentageText);

        // Top bar with back button
        getSupportActionBar().setTitle("Profile");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        userWeightt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // userWeightt.setText("0");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String userWeighttt = userWeightt.getText().toString();
                try {
                    weight = Integer.parseInt(userWeighttt);
                } catch (NumberFormatException e) {
                    weight = 0;
                }
                percentage = computeGoal(weight, goal);
                progressBar.setProgress((int)percentage);
                percentageText.setText(new DecimalFormat("##.##").format(percentage) + "%");




            }
        });

        userGoalWeightt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //  userGoalWeightt.setText("0");


            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                //String userGoalWeighttt = userGoalWeightt.getText().toString();
                try {
                    goal = Integer.parseInt(s.toString());
                } catch (NumberFormatException e) {
                    goal = 0;
                }

                percentage = computeGoal(weight, goal);
                Log.d("Editable: ", "value is : " + percentage);
                progressBar.setProgress((int)percentage);
                percentageText.setText(new DecimalFormat("##.##").format(percentage));
                percentageText.setText(new DecimalFormat("##.##").format(percentage) + "%");


            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor edit = sh.edit();
        Log.d("OnPause", name.getText().toString());
        String nameCheck = sh.getString("nameCheck", name.getText().toString());
        String heightFTCheck = sh.getString("heightFTCheck",heightFT.getText().toString());
        String heightINCheck = sh.getString("heightINCheck",heightIN.getText().toString());
        String ageCheck = sh.getString("ageCheck",age.getText().toString());


        if (nameCheck != null || heightFTCheck != null || heightINCheck != null || ageCheck != null) {
            edit.putString("nameCheck", name.getText().toString());
            if (nameCheck.length() != 0) {
                edit.putString("nameCheck",name.getText().toString());
            }
            if (heightFTCheck.length() != 0) {
                edit.putString("heightFTCheck",heightFT.getText().toString());
            }
            if (heightINCheck.length() != 0) {
                edit.putString("heightINCheck",heightIN.getText().toString());
            }
            if (ageCheck.length() != 0) {
                edit.putString("ageCheck",age.getText().toString());
            }
            edit.apply();
        }


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private double computeGoal(double weight, double goal) {
        double percentage;

        if(weight == 0) {
            percentage = 0;
        } else if (goal == 0) {
            percentage = 0;
        } else if(weight > goal) { //wants to lose weight
            percentage = goal/weight;
        } else  {
            percentage = weight/goal; //wants to gain weight
        }
        return percentage * 100;
    }




}



