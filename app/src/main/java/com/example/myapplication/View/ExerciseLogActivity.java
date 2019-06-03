package com.example.myapplication.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Controller.WorkoutPlanDatabase;
import com.example.myapplication.Model.Exercise;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.AlertDialogHelper;
import com.example.myapplication.Utilities.ExerciseLogAdapter;
import com.example.myapplication.Utilities.RecyclerItemClickListener;

import java.util.ArrayList;

public class ExerciseLogActivity extends AppCompatActivity implements AlertDialogHelper.AlertDialogListener {

    ActionMode mActionMode;
    Menu context_menu;
    WorkoutPlanDatabase db;
    SharedPreferences presetPlan;
    ExerciseLogAdapter exerciseLogAdapter;
    RecyclerView recyclerView;
    NumberPicker weight_np, rep_np;
    boolean isMultiSelect = false;
    private Toolbar toolBar;
    private TextView tv;
    RadioButton radioButton;
    int weight, rep;

    private ArrayList<Exercise> exereciseLog = new ArrayList<>();
    private ArrayList<Exercise> multiselect_list = new ArrayList<>();

    AlertDialogHelper alertDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_log);
        recyclerView = findViewById(R.id.exercise_log_list);
        toolBar = findViewById(R.id.exlog_toolbar);
        tv = findViewById(R.id.exercise_name);
        alertDialogHelper = new AlertDialogHelper(this);
        radioButton = findViewById(R.id.radioButton);

        weight_np = findViewById(R.id.weight_np);
        rep_np = findViewById(R.id.rep_np);

        db = new WorkoutPlanDatabase(this, 1);
        presetPlan = getSharedPreferences("workoutList", MODE_PRIVATE);
        SharedPreferences.Editor edit = presetPlan.edit();

        final String plan = getIntent().getStringExtra("plan");
        final String exerciseName = getIntent().getStringExtra("exerciseName");
        edit.putString("plan", plan);
        edit.apply();

        tv.setText(exerciseName);
        setSupportActionBar(toolBar);

        weight_np.setMinValue(0);
        weight_np.setMaxValue(995);
        weight_np.setWrapSelectorWheel(false);

        rep_np.setMinValue(0);
        rep_np.setMaxValue(100);
        rep_np.setWrapSelectorWheel(false);

        NumberPicker.Formatter formatter = new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                int temp = value * 5;
                return "" + temp;
            }
        };

        weight_np.setFormatter(formatter);

        weight_np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                weight = newVal*5;
            }
        });

        rep_np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                rep = newVal;
            }
        });

        exereciseLog = db.showExerciseLog(Integer.valueOf(plan), exerciseName);
        exerciseLogAdapter = new ExerciseLogAdapter(this, exereciseLog, multiselect_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(exerciseLogAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect) {
                    multi_select(position);
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
                                        + multiselect_list.size() + " set selected?",
                                "DELETE","CANCEL",1,false);
                    else
                        alertDialogHelper.showAlertDialog("Please Confirm","Are you sure you want to delete the  "
                                        + multiselect_list.size() + " sets selected?",
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

    public void refreshAdapter()
    {
        exerciseLogAdapter.selectedList=multiselect_list;
        exerciseLogAdapter.exerciseList = exereciseLog;
        exerciseLogAdapter.notifyDataSetChanged();
    }

    public void multi_select(int position) {
        if (mActionMode != null) {
            if (multiselect_list.contains(exereciseLog.get(position)))
                multiselect_list.remove(exereciseLog.get(position));
            else
                multiselect_list.add(exereciseLog.get(position));

            if (multiselect_list.size() == 1)
                mActionMode.setTitle("" + multiselect_list.size() + " set selected");
            else if (multiselect_list.size() > 1)
                mActionMode.setTitle("" + multiselect_list.size() + " sets selected");
            else
                mActionMode.finish();

            refreshAdapter();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exercise_log_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.toolbar_edit:
                Toast.makeText(this, "Still have to make this", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void addToLog(View view) {
        String plan = getIntent().getStringExtra("plan");
        String exerciseName = getIntent().getStringExtra("exerciseName");

        if (weight == 0 || rep == 0) {
            Toast.makeText(getApplicationContext(), "Please enter a valid value for this set.", Toast.LENGTH_SHORT).show();
            return;
        }

        float repMax = (float) (weight * (1 + rep / 30));
        long ex_id = db.getExID(Integer.valueOf(plan), exerciseName);
        Exercise exLog = new Exercise();
        exLog.setWeight(weight);
        exLog.setReps(rep);
        exLog.setMax(repMax);

        db.addExerciseLog(exLog, ex_id);
        refresh();
    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void refresh() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    public void onPositiveClick(int from) {
        if (from == 1) {
            int size = multiselect_list.size();

            exerciseLogAdapter.notifyDataSetChanged();

            for(int i=0;i<multiselect_list.size();i++) {
                exereciseLog.remove(multiselect_list.get(i));
                db.deleteExerciseLog(multiselect_list.get(i).getDate());
            }

            if (mActionMode != null) {
                mActionMode.finish();
            }
            if (size == 1)
                Toast.makeText(getApplicationContext(), size + " set deleted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), size + " sets deleted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNegativeClick(int from) {

    }

    @Override
    public void onNeutralClick(int from) {

    }
}
