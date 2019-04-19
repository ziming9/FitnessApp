package com.example.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import com.example.myapplication.Model.WorkoutModel;
import com.example.myapplication.Utilities.AlertDialogHelper;
import com.example.myapplication.Utilities.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;

public class WorkoutList_Fragment extends AppCompatActivity implements AlertDialogHelper.AlertDialogListener {
    ActionMode mActionMode;
    Menu context_menu;
    Toolbar toolBar;

    FloatingActionButton fab;
    RecyclerView recyclerView;
    MultiSelectAdapter multiSelectAdapter;
    boolean isMultiSelect = false;

    private ArrayList<WorkoutModel> workoutList = new ArrayList<>();
    private ArrayList<WorkoutModel> multiselect_list = new ArrayList<>();

    AlertDialogHelper alertDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_workout_list);
        Bundle extras = getIntent().getExtras();
        int selected = extras.getInt("variable");
        toolBar = findViewById(R.id.exercise_layout);
        setSupportActionBar(toolBar);
        alertDialogHelper =new AlertDialogHelper(this);
        recyclerView = findViewById(R.id.recycler_view);

        data_load(selected);

        multiSelectAdapter = new MultiSelectAdapter(this, workoutList, multiselect_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(multiSelectAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect) {
                    multi_select(position);
                }else
                    Toast.makeText(getApplicationContext(), "OnClick: Need to Implement Next Feature", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if(!isMultiSelect) {
                    multiselect_list = new ArrayList<WorkoutModel>();
                    isMultiSelect = true;

                    if (mActionMode == null) {
                        mActionMode = startActionMode(mActionModeCallback);
                    }
                }

                multi_select(position);
            }
        }));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                alertDialogHelper.showAlertDialog("","Do you want to add new exercises?",
                        "ADD","LATER",2,false);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(),"Settings Click",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_exit:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void multi_select(int position) {
        if (mActionMode != null) {
            if (multiselect_list.contains(workoutList.get(position)))
                multiselect_list.remove(workoutList.get(position));
            else
                multiselect_list.add(workoutList.get(position));

            if (multiselect_list.size() == 1)
                mActionMode.setTitle("" + multiselect_list.size() + " exercise selected");
            else if (multiselect_list.size() > 1)
                mActionMode.setTitle("" + multiselect_list.size() + " exercises selected");
            else
                mActionMode.finish();

            refreshAdapter();

        }
    }

    public void refreshAdapter()
    {
        multiSelectAdapter.selected_usersList=multiselect_list;
        multiSelectAdapter.workoutList=workoutList;
        multiSelectAdapter.notifyDataSetChanged();
    }

    public void data_load(int selected) {
        String exerciseAry[];
        if (selected == 0) {
            exerciseAry = getResources().getStringArray(R.array.shoulderExcercises);
            Arrays.sort(exerciseAry);
        } else if (selected == 1) {
            exerciseAry = getResources().getStringArray(R.array.chestExercises);
            Arrays.sort(exerciseAry);
        } else if (selected == 2) {
            exerciseAry = getResources().getStringArray(R.array.bicepExcercises);
            Arrays.sort(exerciseAry);
        } else if (selected == 3) {
            exerciseAry = getResources().getStringArray(R.array.tricepExercises);
            Arrays.sort(exerciseAry);
        } else if (selected == 4) {
            exerciseAry = getResources().getStringArray(R.array.backExercises);
            Arrays.sort(exerciseAry);
        } else if (selected == 5) {
            exerciseAry = getResources().getStringArray(R.array.legExercises);
            Arrays.sort(exerciseAry);
        } else if (selected == 6){
            exerciseAry = getResources().getStringArray(R.array.absExercises);
            Arrays.sort(exerciseAry);
        } else {
            exerciseAry = getResources().getStringArray(R.array.cardioExercises);
            Arrays.sort(exerciseAry);
        }
        for (int i = 0; i < exerciseAry.length; i++) {
            WorkoutModel workout = new WorkoutModel(exerciseAry[i]);
            workoutList.add(workout);
        }
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
            multiselect_list = new ArrayList<WorkoutModel>();
            refreshAdapter();
        }
    };

    @Override
    public void onPositiveClick(int from) {
        if(from==1)
        {
            if(multiselect_list.size()>0)
            {
                for(int i=0;i<multiselect_list.size();i++)
                    workoutList.remove(multiselect_list.get(i));

                int size = multiselect_list.size();

                multiSelectAdapter.notifyDataSetChanged();

                if (mActionMode != null) {
                    mActionMode.finish();
                }
                if (size == 1)
                    Toast.makeText(getApplicationContext(), size + " exercise deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), size + " exercises deleted", Toast.LENGTH_SHORT).show();
            }
        }
        else if(from==2)
        {
            if (mActionMode != null) {
                mActionMode.finish();
            }
            Log.d("onPostiveClick", "Reached here");
            WorkoutModel workout = new WorkoutModel("Exercise #"+workoutList.size());
            workoutList.add(workout);
            multiSelectAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onNegativeClick(int from) {

    }

    @Override
    public void onNeutralClick(int from) {

    }
}
