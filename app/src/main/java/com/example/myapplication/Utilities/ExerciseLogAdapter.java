package com.example.myapplication.Utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.myapplication.Model.Exercise;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ExerciseLogAdapter extends RecyclerView.Adapter<ExerciseLogAdapter.MyViewHolder> {
    Context mContext;
    private ArrayList<Exercise> exerciseList;
    private ArrayList<Exercise> selectedList;

    public ExerciseLogAdapter(Context context, ArrayList<Exercise> exerciseList, ArrayList<Exercise> selectedList) {
        this.mContext=context;
        this.exerciseList = exerciseList;
        this.selectedList = selectedList;
    }


    @NonNull
    @Override
    public ExerciseLogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exerciselist_item, viewGroup, false);
        return new ExerciseLogAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExerciseLogAdapter.MyViewHolder myViewHolder, int i) {
        Exercise exercise = exerciseList.get(i);
        String weight = Double.valueOf(exercise.getWeight()).toString();
        myViewHolder.offset.setText(String.valueOf(getItemCount()));  // number
        myViewHolder.weight.setText(weight);          // weight
        myViewHolder.rep.setText(String.valueOf(exercise.getReps()));  // reps
        myViewHolder.repMax.setText(String.valueOf(exercise.getMax()));          // 1RM
        myViewHolder.rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!myViewHolder.rb.isSelected()) {
                    myViewHolder.rb.setChecked(true);
                    myViewHolder.rb.setSelected(true);
                } else {
                    myViewHolder.rb.setChecked(false);
                    myViewHolder.rb.setSelected(false);
                }
            }
        });

        if(selectedList.contains(exerciseList.get(i)))
            myViewHolder.exLog.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorSelected));
        else
            myViewHolder.exLog.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWhite));

    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView offset, weight, rep, repMax;
        public LinearLayout exLog;
        RadioButton rb;

        public MyViewHolder(@NonNull View view) {
            super(view);

            offset = view.findViewById(R.id.number);
            weight = view.findViewById(R.id.weightsNum);
            rep = view.findViewById(R.id.repsNum);
            repMax = view.findViewById(R.id.oneRepMax);
            exLog = view.findViewById(R.id.ex_log);
            rb = view.findViewById(R.id.radioButton);

        }
    }
}
