package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.Controller.WorkoutPlanDatabase;
import com.example.myapplication.Model.CreatedWorkout;
import com.example.myapplication.Utilities.WorkoutPlanAdapter;
import com.example.myapplication.View.NewWorkoutActivity;
import com.example.myapplication.View.ProfileActivity;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    public static int REQUEST_CODE = 1;
    Button profile;
    Button createPlan;
    RecyclerView recList;
    WorkoutPlanDatabase db;

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
        db = new WorkoutPlanDatabase(getContext(), 1);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.scrollToPositionWithOffset(0,0);
        recList.setLayoutManager(llm);

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

        //ArrayList<CreatedWorkout> cwList = db.showPlan();
        if (db.showPlan() != null) {
            ArrayList<CreatedWorkout> cwList = db.showPlan();
            WorkoutPlanAdapter wp = new WorkoutPlanAdapter(cwList, getContext());
            recList.setAdapter(wp);
            Log.d("Size:", ""+ wp.getItemCount());

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (db.showPlan() != null) {
                    ArrayList<CreatedWorkout> cwList = db.showPlan();
                    WorkoutPlanAdapter wp = new WorkoutPlanAdapter(cwList, getContext());
                    recList.setAdapter(wp);

                    for (CreatedWorkout cw: cwList) {
                        String log = "ID: " + cw.getID() + " Name: " + cw.getName();
                        Log.d("Plan: ", log);
                    }
                }
            }
        }
    }
}
