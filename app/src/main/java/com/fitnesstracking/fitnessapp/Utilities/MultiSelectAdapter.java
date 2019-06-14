package com.fitnesstracking.fitnessapp.Utilities;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fitnesstracking.fitnessapp.Model.WorkoutModel;
import com.fitnesstracking.fitnessapp.R;

import java.util.ArrayList;

public class MultiSelectAdapter extends RecyclerView.Adapter<MultiSelectAdapter.MyViewHolder> {
    public ArrayList<WorkoutModel> workoutList=new ArrayList<>();
    public ArrayList<WorkoutModel> selected_usersList=new ArrayList<>();
    Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_listitem;
        public TextView exercise;

        public MyViewHolder(View view) {
            super(view);
            exercise = view.findViewById(R.id.wl_exercise);
            ll_listitem = view.findViewById(R.id.ll_listitem);

        }
    }


    public MultiSelectAdapter(Context context,ArrayList<WorkoutModel> workoutList,ArrayList<WorkoutModel> selectedList) {
        this.mContext=context;
        this.workoutList = workoutList;
        this.selected_usersList = selectedList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workoutlist_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WorkoutModel workout = workoutList.get(position);
        holder.exercise.setText(workout.getExercise());

        if(selected_usersList.contains(workoutList.get(position)))
            holder.ll_listitem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorSelected));
        else
            holder.ll_listitem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWhite));

    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }
}

