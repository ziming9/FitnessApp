package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import java.text.DecimalFormat;

import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    private ProgressBar progressBar;
    double weight;
    double goal;
    double percentage;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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




    public void onViewCreated(final View view, Bundle savedInstanceState) {
       super.onViewCreated(view, savedInstanceState);


        final EditText userWeightt =  view.findViewById(R.id.editWeight);
        final EditText userGoalWeightt =  view.findViewById(R.id.editGoalWeight);
        progressBar =  view.findViewById(R.id.progressBar);
        final EditText percentageText = view.findViewById(R.id.percentageText);

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
                weight = Integer.parseInt(userWeighttt);

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
                goal = Integer.parseInt(s.toString());


                percentage = computeGoal(weight, goal);
                Log.d("Editable: ", "value is : " + percentage);
                progressBar.setProgress((int)percentage);
                percentageText.setText(new DecimalFormat("##.##").format(percentage));

                percentageText.setText(new DecimalFormat("##.##").format(percentage) + "%");


            }
        });

        //percentage = computeGoal(weight, goal);
        //progressBar.setProgress(50);


    }





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    
}
