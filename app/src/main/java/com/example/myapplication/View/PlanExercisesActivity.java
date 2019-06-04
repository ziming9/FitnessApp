package com.example.myapplication.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Controller.WorkoutPlanDatabase;
import com.example.myapplication.Model.Exercise;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.AlertDialogHelper;
import com.example.myapplication.Utilities.ExerciseListAdapter;
import com.example.myapplication.Utilities.RecyclerItemClickListener;

import java.util.ArrayList;

public class PlanExercisesActivity extends FragmentActivity implements AlertDialogHelper.AlertDialogListener {

    ActionMode mActionMode;
    Menu context_menu;
    WorkoutPlanDatabase db;
    SharedPreferences presetPlan;
    RecyclerView recyclerView;
    ExerciseListAdapter exerciseListAdapter;
    Toolbar toolBar;

    boolean isMultiSelect = false;

    private ArrayList<Exercise> exereciseList = new ArrayList<>();
    private ArrayList<Exercise> multiselect_list = new ArrayList<>();

    AlertDialogHelper alertDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_exercises);
        recyclerView = findViewById(R.id.planList);
        toolBar = findViewById(R.id.toolbar_layout);
        alertDialogHelper = new AlertDialogHelper(this);
        db = new WorkoutPlanDatabase(this, 1);
        presetPlan = getSharedPreferences("workoutList", MODE_PRIVATE);
        final SharedPreferences.Editor edit = presetPlan.edit();

        Log.d("LOG_TAG", "onCreate");
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
                    if (isMultiSelect) {
                        multi_select(position);
                    } else {
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

                }

                @Override
                public void onItemLongClick(View view, int position) {
                    if(!isMultiSelect) {
                        multiselect_list = new ArrayList<Exercise>();
                        isMultiSelect = true;
                        if (mActionMode == null) {
                            mActionMode = startActionMode(mActionModeCallback);
                        }
                    }

                    multi_select(position);
                }
            }));
        }

    }

    public void refreshAdapter()
    {
        exerciseListAdapter.selectedList = multiselect_list;
        exerciseListAdapter.exerciseList = exereciseList;
        exerciseListAdapter.notifyDataSetChanged();
    }


    public void multi_select(int position) {
        if (mActionMode != null) {
            if (multiselect_list.contains(exereciseList.get(position)))
                multiselect_list.remove(exereciseList.get(position));
            else
                multiselect_list.add(exereciseList.get(position));

            if (multiselect_list.size() == 1)
                mActionMode.setTitle("" + multiselect_list.size() + " exercise selected");
            else if (multiselect_list.size() > 1)
                mActionMode.setTitle("" + multiselect_list.size() + " exercises selected");
            else
                mActionMode.finish();

            refreshAdapter();

        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }

    public void goBack(View view) {
        // There is a better way, check activity stacks and remove.
        /*Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);*/
        final SharedPreferences.Editor edit = presetPlan.edit();
        edit.putString("plan", null);
        edit.apply();
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
        finish();
    }

    public void add_Exercise(View view) {
        ActivityFragment exerciseFragment = new ActivityFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.add_fragment_container, exerciseFragment);
        transaction.commit();
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.workoutlist_menu, menu);
            context_menu = menu;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete:
                    if(multiselect_list.size() == 1)
                        alertDialogHelper.showAlertDialog("Please Confirm","Are you sure you want to delete the  "
                                        + multiselect_list.size() + " exercise selected?",
                                "DELETE","CANCEL",1,false);
                    else
                        alertDialogHelper.showAlertDialog("Please Confirm","Are you sure you want to delete the  "
                                        + multiselect_list.size() + " exercises selected?",
                                "DELETE","CANCEL",1,false);
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            isMultiSelect = false;
            multiselect_list = new ArrayList<Exercise>();
            refreshAdapter();
        }
    };

    @Override
    public void onPositiveClick(int from) {
        if (from == 1) {
            int size = multiselect_list.size();

            exerciseListAdapter.notifyDataSetChanged();

            for(int i=0;i<multiselect_list.size();i++) {
                exereciseList.remove(multiselect_list.get(i));
                final String plan = getIntent().getStringExtra("plan");
                db.deleteExercise(Integer.valueOf(plan), multiselect_list.get(i).getEx_name());
            }

            if (mActionMode != null) {
                mActionMode.finish();
            }
            if (size == 1)
                Toast.makeText(getApplicationContext(), size + " exercise deleted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), size + " exercises deleted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNegativeClick(int from) {

    }

    @Override
    public void onNeutralClick(int from) {

    }
}


