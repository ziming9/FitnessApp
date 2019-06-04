package com.example.myapplication.Utilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Controller.WorkoutPlanDatabase;
import com.example.myapplication.Model.CreatedWorkout;
import com.example.myapplication.R;
import com.example.myapplication.View.PlanExercisesActivity;
import com.example.myapplication.View.ProgressFragment;

import java.util.ArrayList;
import java.util.Random;

public class WorkoutPlanAdapter extends RecyclerView.Adapter<WorkoutPlanAdapter.WorkoutPlanViewHolder> {
    private ArrayList<CreatedWorkout> workoutPlan;
    public Context mContext;
    WorkoutPlanDatabase db;

    public WorkoutPlanAdapter(ArrayList<CreatedWorkout> workoutPlan, Context mContext) {
        this.workoutPlan = workoutPlan;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public WorkoutPlanAdapter.WorkoutPlanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plan_layout, viewGroup, false);
        return new WorkoutPlanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final WorkoutPlanAdapter.WorkoutPlanViewHolder workoutPlanViewHolder, final int i) {
        final CreatedWorkout cw = workoutPlan.get(i);
        int[] card_colors = workoutPlanViewHolder.itemView.getResources().getIntArray(R.array.card_colors);
        int rand_card_colors = card_colors[new Random().nextInt(card_colors.length)];

        workoutPlanViewHolder.planCard.setBackgroundColor(rand_card_colors);
        workoutPlanViewHolder.planName.setText(cw.getName());
        workoutPlanViewHolder.planDay.setText(cw.getDay_of_week());
        workoutPlanViewHolder.total_ex.setText(String.valueOf(cw.getTotal_workouts()));
        workoutPlanViewHolder.planOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, workoutPlanViewHolder.planOption);
                popupMenu.inflate(R.menu.card_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.card_delete:
                                Toast.makeText(mContext, cw.getName() + " Deleted", Toast.LENGTH_SHORT).show();
                                db = new WorkoutPlanDatabase(mContext, 1);
                                workoutPlan.remove(i);
                                db.deletePlan(cw.getID());
                                notifyDataSetChanged();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        workoutPlanViewHolder.planCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Opens up list of exercises
                //Toast.makeText(mContext, "Opening " + cw.getName() + " ....", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, PlanExercisesActivity.class);
                intent.putExtra("plan", String.valueOf(cw.getID()));
                intent.putExtra("plan_selected", true);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return workoutPlan.size();
    }

    public static class WorkoutPlanViewHolder extends RecyclerView.ViewHolder{

        protected TextView planName;
        protected TextView planDay;
        protected TextView total_ex;
        protected TextView planOption;
        protected CardView planCard;

        public WorkoutPlanViewHolder(@NonNull View itemView) {
            super(itemView);
            planName = itemView.findViewById(R.id.plan_name);
            planDay = itemView.findViewById(R.id.plan_day);
            total_ex = itemView.findViewById(R.id.total_ex);
            planOption = itemView.findViewById(R.id.plan_options);
            planCard = itemView.findViewById(R.id.plan_card);
        }
    }
}
