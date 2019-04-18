package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ProfileFragment extends Fragment {
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    private ProgressBar progressBar;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    private int computeGoal(int weight, int goal) {
        int percentage;

        if(weight == 0) {
            percentage = 0;
        } else if (goal == 0) {
            percentage = 0;
        } else if(weight > goal) { //wants to lose weight
            percentage = goal/weight;
        } else  {
            percentage = weight/goal; //wants to gain weight
        } return percentage;

    }




    public void onViewCreated(View view, Bundle savedInstanceState) {
       super.onViewCreated(view, savedInstanceState);
        int weight = 0;
        int goal = 0;

        EditText userWeightt =  view.findViewById(R.id.editWeight);
        userWeightt.setText("0");

        String userWeighttt = userWeightt.getText().toString();
        weight = Integer.parseInt(userWeighttt);
        EditText userGoalWeightt =  view.findViewById(R.id.editGoalWeight);
        userGoalWeightt.setText("0");
        String userGoalWeighttt = userGoalWeightt.getText().toString();
        goal = Integer.parseInt(userGoalWeighttt);

        progressBar =  view.findViewById(R.id.progressBar);

        int percentage = computeGoal(weight, goal);
        progressBar.setProgress(percentage);

    }





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    
}
