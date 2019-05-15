package com.example.myapplication.Utilities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Model.CreatedWorkout;
import com.example.myapplication.R;

import java.util.ArrayList;

public class WorkoutPlanAdapter extends RecyclerView.Adapter<WorkoutPlanAdapter.WorkoutPlanViewHolder> {
    private ArrayList<CreatedWorkout> workoutPlan;

    public WorkoutPlanAdapter(ArrayList<CreatedWorkout> workoutPlan) {
        this.workoutPlan = workoutPlan;
    }

    @NonNull
    @Override
    public WorkoutPlanAdapter.WorkoutPlanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plan_layout, viewGroup, false);
        return new WorkoutPlanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutPlanAdapter.WorkoutPlanViewHolder workoutPlanViewHolder, int i) {
        CreatedWorkout cw = workoutPlan.get(i);
        workoutPlanViewHolder.planName.setText(cw.getName());
        workoutPlanViewHolder.planDay.setText(cw.getDay_of_week());
        workoutPlanViewHolder.total_ex.setText(String.valueOf(cw.getTotal_workouts()));
    }

    @Override
    public int getItemCount() {
        return workoutPlan.size();
    }

    public static class WorkoutPlanViewHolder extends RecyclerView.ViewHolder{

        protected TextView planName;
        protected TextView planDay;
        protected TextView total_ex;

        public WorkoutPlanViewHolder(@NonNull View itemView) {
            super(itemView);
            planName = itemView.findViewById(R.id.plan_name);
            planDay = itemView.findViewById(R.id.plan_day);
            total_ex = itemView.findViewById(R.id.total_ex);
        }
    }
}
