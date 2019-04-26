package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WorkoutListFragment extends AppCompatActivity {

    //private ArrayList<String> exercises;
    private String[] exerciseAry;
    private ListView workoutsListView;
    private ArrayAdapter arrayAdapter;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> workoutList_item = new ArrayList<>();
    private ArrayList<String> workoutList;
    Toolbar toolBar;

    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_workout_list);

        // ListView implementation
        //workoutsListView = findViewById(R.id.recycler_view);
        toolBar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolBar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // this-The current activity context.
        // Second param is the resource Id for list layout row item
        // Third param is input array
        //arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, shoulderExercise);
        //workoutsListView.setAdapter(arrayAdapter);

        Bundle extras = getIntent().getExtras();
        int selected = extras.getInt("variable");

        if (selected == 0) {
            exerciseAry = getResources().getStringArray(R.array.shoulderExercises);
            workoutList = new ArrayList<>(Arrays.asList(exerciseAry));
            Arrays.sort(exerciseAry);
            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exerciseAry);
        } else if (selected == 1) {
            exerciseAry = getResources().getStringArray(R.array.chestExercises);
            workoutList = new ArrayList<>(Arrays.asList(exerciseAry));
            Arrays.sort(exerciseAry);
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, exerciseAry);
        } else if (selected == 2) {
            exerciseAry = getResources().getStringArray(R.array.bicepExercises);
            Arrays.sort(exerciseAry);
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, exerciseAry);
        } else if (selected == 3) {
            exerciseAry = getResources().getStringArray(R.array.tricepExercises);
            Arrays.sort(exerciseAry);
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, exerciseAry);
        } else if (selected == 4) {
            exerciseAry = getResources().getStringArray(R.array.backExercises);
            Arrays.sort(exerciseAry);
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, exerciseAry);
        } else if (selected == 5) {
            exerciseAry = getResources().getStringArray(R.array.legExercises);
            Arrays.sort(exerciseAry);
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, exerciseAry);
        } else if (selected == 6) {
            exerciseAry = getResources().getStringArray(R.array.absExercises);
            Arrays.sort(exerciseAry);
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, exerciseAry);
        }else {
            exerciseAry = getResources().getStringArray(R.array.cardioExercises);
            workoutList = new ArrayList<>(Arrays.asList(exerciseAry));
            Arrays.sort(exerciseAry);
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, exerciseAry);
        }

        workoutsListView.setAdapter(arrayAdapter);

        /*adapter = new ArrayAdapter<>(this, R.layout.workoutlist_item, R.id.workout_item, workoutList);
        workoutsListView.setAdapter(adapter);

        workoutsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        workoutsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
            }
        });

        workoutsListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                if (checked) {
                    count++;
                    workoutList_item.add(workoutList.get(position));
                } else {
                    count--;
                    workoutList_item.remove(workoutList.get(position));
                }

                if (count > 1) {
                    mode.setTitle(count + " exercises selected");

                } else {
                    mode.setTitle(count + " exercise selected");
                }

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {

                getSupportActionBar().hide();
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.workoutlist_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.delete:
                        for(String selected : workoutList_item) {
                            Log.d("OnClickListener", selected);
                            adapter.remove(selected);
                            adapter.notifyDataSetChanged();
                            mode.finish();
                        }
                        if (count > 1) {
                            Toast.makeText(getBaseContext(), count + " exercises removed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getBaseContext(), count + " exercise removed", Toast.LENGTH_SHORT).show();
                        }
                        count = 0;
                        return true;
                    default:
                        return false;
                }

            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                Log.d("OnDestroy", "GetSupportBar");
                getSupportActionBar().show();
            }
        });*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.d("OnSupportNav", "Back button pressed");
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Toolbar menu.
        getMenuInflater().inflate(R.menu.workoutlist_menu, menu);
        return true;
    }

}
