package com.fitnesstracking.fitnessapp.Utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fitnesstracking.fitnessapp.Model.Exercise;
import com.fitnesstracking.fitnessapp.R;

import java.util.ArrayList;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.MyViewHolder> {
    Context mContext;
    public ArrayList<Exercise> exerciseList;
    public ArrayList<Exercise> selectedList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_listitem;
        public TextView exercise;

        public MyViewHolder(View view) {
            super(view);
            exercise = view.findViewById(R.id.wl_exercise);
            ll_listitem = view.findViewById(R.id.ll_listitem);

        }
    }

    public ExerciseListAdapter(Context context, ArrayList<Exercise> exerciseList, ArrayList<Exercise> selectedList) {
        this.mContext=context;
        this.exerciseList = exerciseList;
        this.selectedList = selectedList;
    }

    @NonNull
    @Override
    public ExerciseListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.workoutlist_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseListAdapter.MyViewHolder myViewHolder, int i) {
        Exercise exercise = exerciseList.get(i);
        myViewHolder.exercise.setText(exercise.getEx_name());

        if(selectedList.contains(exerciseList.get(i)))
            myViewHolder.ll_listitem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorSelected));
        else
            myViewHolder.ll_listitem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWhite));

    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }
}
