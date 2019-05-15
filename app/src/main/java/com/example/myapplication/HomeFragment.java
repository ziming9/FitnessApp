package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.Model.CreatedWorkout;
import com.example.myapplication.Utilities.WorkoutPlanAdapter;
import com.example.myapplication.View.NewWorkoutActivity;
import com.example.myapplication.View.ProfileActivity;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    public static int REQUEST_CODE = 1;
    GridLayout plansGrid;
    CardView planCards;
    Button profile;
    Button createPlan;
    RecyclerView recList;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profile = view.findViewById(R.id.profile_button);
        createPlan = view.findViewById(R.id.plan_button);
        recList = view.findViewById(R.id.planList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);


        /*plansGrid = view.findViewById(R.id.plan_grid);
        planCards = view.findViewById(R.id.plan_card);*/

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        createPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewWorkoutActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String namePlan = data.getStringExtra("planName");
                String dayPlan = data.getStringExtra("planDay");
                int total_workouts = 0;

                WorkoutPlanAdapter wp = new WorkoutPlanAdapter(createWorkouts(namePlan, dayPlan, total_workouts));
                recList.setAdapter(wp);
            }
        }
    }

    private ArrayList<CreatedWorkout> createWorkouts(String name, String day, int total_workouts) {
        ArrayList<CreatedWorkout> plans = new ArrayList<>();

        CreatedWorkout cw = new CreatedWorkout(name, day, total_workouts);
        plans.add(cw);

        return plans;
    }
}
