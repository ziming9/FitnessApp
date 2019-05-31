package com.example.myapplication.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.Controller.WorkoutPlanDatabase;
import com.example.myapplication.Model.Exercise;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.ExerciseListAdapter;
import com.example.myapplication.Utilities.RecyclerItemClickListener;

import java.util.ArrayList;

public class PlanExercisesActivity extends FragmentActivity {

    WorkoutPlanDatabase db;
    SharedPreferences presetPlan;
    RecyclerView recyclerView;
    ExerciseListAdapter exerciseListAdapter;

    private ArrayList<Exercise> exereciseList = new ArrayList<>();
    private ArrayList<Exercise> multiselect_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_exercises);
        recyclerView = findViewById(R.id.planList);
        db = new WorkoutPlanDatabase(this, 1);
        presetPlan = getSharedPreferences("workoutList", MODE_PRIVATE);
        final SharedPreferences.Editor edit = presetPlan.edit();

        final String plan = getIntent().getStringExtra("plan");
        edit.putString("plan", plan);
        edit.apply();

        if(plan != null) {
            exereciseList = db.showExercises(Integer.valueOf(plan));
            exerciseListAdapter = new ExerciseListAdapter(this, exereciseList, multiselect_list);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(exerciseListAdapter);

            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView,new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String exerciseName =
                            ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.wl_exercise)).getText().toString();
                    Intent intent = new Intent(getApplicationContext(), ExerciseLogActivity.class);
                    intent.putExtra("exerciseName", exerciseName);
                    intent.putExtra("plan", plan);
                    Log.d("exerciseName", exerciseName);
                    // inflate exerciseinfolist_item xml
                    ArrayList<Exercise> exList = db.showExerciseLog(Integer.valueOf(plan), exerciseName);
                    for(Exercise s: exList) {
                        Log.d("Exercise: ", exerciseName + " Weight: " + s.getWeight() + " Reps: " + s.getReps() + " RepMax: " + s.getMax());
                    }
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            }));
        }

    }
    //Need to implement to refresh the exercise list when added in the create plan instead of going back to show it
    @Override
    protected void onResume() {

        super.onResume();
    }

    public void goBack(View view) {
        // There is a better way, check activity stacks and remove.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void add_Exercise(View view) {
        ActivityFragment exerciseFragment = new ActivityFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.add_fragment_container, exerciseFragment);
        transaction.commit();
    }
}


