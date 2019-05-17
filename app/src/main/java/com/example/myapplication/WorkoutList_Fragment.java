package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.WorkoutModel;
import com.example.myapplication.Utilities.AlertDialogHelper;
import com.example.myapplication.Utilities.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;

public class WorkoutList_Fragment extends AppCompatActivity implements AlertDialogHelper.AlertDialogListener {
    //private static int REQUEST_CODE = 0;

    ActionMode mActionMode;
    Menu context_menu;
    Toolbar toolBar;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    MultiSelectAdapter multiSelectAdapter;
    SharedPreferences presetList;
    boolean isMultiSelect = false;
    Context mContext;

    private ArrayList<WorkoutModel> workoutList = new ArrayList<>();
    private ArrayList<WorkoutModel> multiselect_list = new ArrayList<>();

    AlertDialogHelper alertDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_workout_list);
        Bundle extras = getIntent().getExtras();
        final int selected = extras.getInt("variable");
        toolBar = findViewById(R.id.exercise_layout);
        setSupportActionBar(toolBar);
        alertDialogHelper =new AlertDialogHelper(this);
        recyclerView = findViewById(R.id.recycler_view);
        presetList = getSharedPreferences("workoutList", MODE_PRIVATE);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        data_load(selected);

        multiSelectAdapter = new MultiSelectAdapter(this, workoutList, multiselect_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(multiSelectAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent setsReps = new Intent(WorkoutList_Fragment.this, SetsRepsActivity.class);
                String exerciseName =
                        ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.wl_exercise)).getText().toString();
                if (isMultiSelect) {
                    multi_select(position);
                } else
                    if (multiselect_list.size() == 0) {
                        setsReps.putExtra("exerciseName", exerciseName);
                        startActivity(setsReps);
                    }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if(!isMultiSelect) {
                    //Log.d("onItemLongClick", "isMultiSelect: " + isMultiSelect);
                    multiselect_list = new ArrayList<WorkoutModel>();
                    isMultiSelect = true;
                    //og.d("onItemLongClick set", "isMultiSelect: " + isMultiSelect);
                    if (mActionMode == null) {
                        mActionMode = startActionMode(mActionModeCallback);
                    }
                }

                multi_select(position);
            }
        }));

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View alertLayout = inflater.inflate(R.layout.custom_dialog_layout, null);
                final EditText newExercise = alertLayout.findViewById(R.id.new_ex_name);
                final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("New Exercise");
                alert.setView(alertLayout);
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String new_exercise = newExercise.getText().toString();
                        if (new_exercise.length() == 0) {
                            Toast.makeText(getApplicationContext(), "Please enter a name", Toast.LENGTH_SHORT).show();
                        } else {
                            addNewExercise(new_exercise);
                        }
                    }
                });
                alert.show();
            }
        });
    }

    public void addNewExercise(String exercise) {
        Bundle extras = getIntent().getExtras();
        final int selected = extras.getInt("variable");
        SharedPreferences.Editor fileEdit = presetList.edit();
        StringBuilder sb = new StringBuilder();
        String listName, listCheck = null;

        if (selected == 0) {
            // set current list of exercises to the string builder.
            sb.append(presetList.getString("shoulderExercises", ""));
            listName = "shoulderExercises";
            listCheck = "shoulderCheck";
        } else if (selected == 1) {
            sb.append(presetList.getString("chestExercises", ""));
            listName = "chestExercises";
            listCheck = "chestCheck";
        } else if (selected == 2) {
            sb.append(presetList.getString("bicepExercises", ""));
            listName = "bicepExercises";
            listCheck = "bicepCheck";
        } else if (selected == 3) {
            sb.append(presetList.getString("tricepExercises", ""));
            listName = "tricepExercises";
            listCheck = "tricepCheck";
        } else if (selected == 4) {
            sb.append(presetList.getString("backExercises", ""));
            listName = "backExercises";
            listCheck = "backCheck";
        } else if (selected == 5) {
            sb.append(presetList.getString("legExercises", ""));
            listName = "legExercises";
            listCheck = "legCheck";
        } else if (selected == 6) {
            sb.append(presetList.getString("absExercises", ""));
            listName = "absExercises";
            listCheck = "absCheck";
        } else {
            sb.append(presetList.getString("cardioExercises", ""));
            listName = "cardioExercises";
            listCheck = "cardioCheck";
        }

        // append the new exercise onto the string builder.
        WorkoutModel workout = new WorkoutModel(exercise);
        sb.append(",").append(workout.getExercise());

        // update shared preferences.
        fileEdit.putBoolean(listCheck, true);
        fileEdit.putString(listName, sb.toString());
        Log.d("SB", sb.toString());
        fileEdit.apply();

        workoutList.add(workout);
        multiSelectAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
            /*case R.id.action_settings:
                Toast.makeText(getApplicationContext(),"Settings Click",Toast.LENGTH_SHORT).show();
                return true;*/
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
        int index = 0;
        SharedPreferences.Editor fileEdit = presetList.edit();
        String exerciseAry[];
        StringBuilder sb = new StringBuilder();
        boolean checkEdit;
        toolBar.setTitleTextColor(getResources().getColor(R.color.colorWhite));

        if (selected == 0) {
            getSupportActionBar().setTitle(R.string.shoulder);
            checkEdit = presetList.getBoolean("shoulderCheck", false);
            fileEdit.putBoolean("shoulderCheck", checkEdit);
            if (checkEdit == false) {
                exerciseAry = getResources().getStringArray(R.array.shoulderExercises);
            } else {
                exerciseAry = presetList.getString("shoulderExercises", "").split(",");
            }

            for (String s:exerciseAry) {
                index++;
                sb.append(s);
                try {
                    if (exerciseAry[index].length() != 0) {
                        sb.append(",");
                    }
                } catch (Exception e) {
                    Log.d("data_load", "Indexing array is 0.");
                }

            }
            fileEdit.putString("shoulderExercises", sb.toString());
            fileEdit.putInt("exerciseSelected", selected);
            fileEdit.apply();
            Arrays.sort(exerciseAry);
        } else if (selected == 1) {
            getSupportActionBar().setTitle(R.string.chest);
            checkEdit = presetList.getBoolean("chestCheck", false);
            fileEdit.putBoolean("chestCheck", checkEdit);

            if (checkEdit == false) {
                exerciseAry = getResources().getStringArray(R.array.chestExercises);
            } else {
                exerciseAry = presetList.getString("chestExercises", "").split(",");
            }

            for (String s:exerciseAry) {
                index++;
                sb.append(s);
                try {
                    if (exerciseAry[index].length() != 0) {
                        sb.append(",");
                    }
                } catch (Exception e) {
                    Log.d("data_load", "Indexing array is 0.");
                }

            }

            fileEdit.putString("chestExercises", sb.toString());
            fileEdit.putInt("exerciseSelected", selected);
            fileEdit.apply();
            Arrays.sort(exerciseAry);
        } else if (selected == 2) {
            getSupportActionBar().setTitle(R.string.biceps);
            checkEdit = presetList.getBoolean("bicepCheck", false);
            fileEdit.putBoolean("bicepCheck", checkEdit);

            if (checkEdit == false) {
                exerciseAry = getResources().getStringArray(R.array.bicepExercises);
            } else {
                exerciseAry = presetList.getString("bicepExercises", "").split(",");
            }

            for (String s:exerciseAry) {
                index++;
                sb.append(s);
                try {
                    if (exerciseAry[index].length() != 0) {
                        sb.append(",");
                    }
                } catch (Exception e) {
                    Log.d("data_load", "Indexing array is 0.");
                }

            }

            fileEdit.putString("bicepExercises", sb.toString());
            fileEdit.putInt("exerciseSelected", selected);
            fileEdit.apply();
            Arrays.sort(exerciseAry);
        } else if (selected == 3) {
            getSupportActionBar().setTitle(R.string.triceps);
            checkEdit = presetList.getBoolean("tricepCheck", false);
            fileEdit.putBoolean("tricepCheck", checkEdit);

            if (checkEdit == false) {
                exerciseAry = getResources().getStringArray(R.array.tricepExercises);
            } else {
                exerciseAry = presetList.getString("tricepExercises", "").split(",");
            }

            for (String s:exerciseAry) {
                index++;
                sb.append(s);
                try {
                    if (exerciseAry[index].length() != 0) {
                        sb.append(",");
                    }
                } catch (Exception e) {
                    Log.d("data_load", "Indexing array is 0.");
                }

            }

            fileEdit.putString("tricepExercises", sb.toString());
            fileEdit.putInt("exerciseSelected", selected);
            fileEdit.apply();
            Arrays.sort(exerciseAry);
        } else if (selected == 4) {
            getSupportActionBar().setTitle(R.string.back);
            checkEdit = presetList.getBoolean("backCheck", false);
            fileEdit.putBoolean("backCheck", checkEdit);

            if (checkEdit == false) {
                exerciseAry = getResources().getStringArray(R.array.backExercises);
            } else {
                exerciseAry = presetList.getString("backExercises", "").split(",");
            }

            for (String s:exerciseAry) {
                index++;
                sb.append(s);
                try {
                    if (exerciseAry[index].length() != 0) {
                        sb.append(",");
                    }
                } catch (Exception e) {
                    Log.d("data_load", "Indexing array is 0.");
                }

            }

            fileEdit.putString("backExercises", sb.toString());
            fileEdit.putInt("exerciseSelected", selected);
            fileEdit.apply();
            Arrays.sort(exerciseAry);
        } else if (selected == 5) {
            getSupportActionBar().setTitle(R.string.legs);
            checkEdit = presetList.getBoolean("legCheck", false);
            fileEdit.putBoolean("legCheck", checkEdit);

            if (checkEdit == false) {
                exerciseAry = getResources().getStringArray(R.array.legExercises);
            } else {
                exerciseAry = presetList.getString("legExercises", "").split(",");
            }

            for (String s:exerciseAry) {
                index++;
                sb.append(s);
                try {
                    if (exerciseAry[index].length() != 0) {
                        sb.append(",");
                    }
                } catch (Exception e) {
                    Log.d("data_load", "Indexing array is 0.");
                }

            }

            fileEdit.putString("legExercises", sb.toString());
            fileEdit.putInt("exerciseSelected", selected);
            fileEdit.apply();
            Arrays.sort(exerciseAry);
            Arrays.sort(exerciseAry);
        } else if (selected == 6){
            getSupportActionBar().setTitle(R.string.abs);
            checkEdit = presetList.getBoolean("absCheck", false);
            fileEdit.putBoolean("absCheck", checkEdit);

            if (checkEdit == false) {
                exerciseAry = getResources().getStringArray(R.array.absExercises);
            } else {
                exerciseAry = presetList.getString("absExercises", "").split(",");
            }

            for (String s:exerciseAry) {
                index++;
                sb.append(s);
                try {
                    if (exerciseAry[index].length() != 0) {
                        sb.append(",");
                    }
                } catch (Exception e) {
                    Log.d("data_load", "Indexing array is 0.");
                }

            }

            fileEdit.putString("absExercises", sb.toString());
            fileEdit.putInt("exerciseSelected", selected);
            fileEdit.apply();
            Arrays.sort(exerciseAry);
        } else {
            getSupportActionBar().setTitle(R.string.cardio);
            checkEdit = presetList.getBoolean("cardioCheck", false);
            fileEdit.putBoolean("cardioCheck", checkEdit);

            if (checkEdit == false) {
                exerciseAry = getResources().getStringArray(R.array.cardioExercises);
            } else {
                exerciseAry = presetList.getString("cardioExercises", "").split(",");
            }

            for (String s:exerciseAry) {
                index++;
                sb.append(s);
                try {
                    if (exerciseAry[index].length() != 0) {
                        sb.append(",");
                    }
                } catch (Exception e) {
                    Log.d("data_load", "Indexing array is 0.");
                }

            }

            fileEdit.putString("cardioExercises", sb.toString());
            fileEdit.putInt("exerciseSelected", selected);
            fileEdit.apply();
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
                SharedPreferences.Editor fileEdit = presetList.edit();
                StringBuilder sb = new StringBuilder();
                // set current list of exercises to the string builder.
                int selected = presetList.getInt("exerciseSelected", -1);
                if (selected == 0) {
                    sb.append(presetList.getString("shoulderExercises", ""));
                } else if (selected == 1) {
                    sb.append(presetList.getString("chestExercises", ""));
                } else if (selected == 2) {
                    sb.append(presetList.getString("bicepExercises", ""));
                } else if (selected == 3) {
                    sb.append(presetList.getString("tricepExercises", ""));
                } else if (selected == 4) {
                    sb.append(presetList.getString("backExercises", ""));
                } else if (selected == 5) {
                    sb.append(presetList.getString("legExercises", ""));
                } else if (selected == 6) {
                    sb.append(presetList.getString("absExercises", ""));
                } else if (selected == 7) {
                    sb.append(presetList.getString("cardioExercises", ""));
                }

                for(int i=0;i<multiselect_list.size();i++) {
                    try {
                        String s = multiselect_list.get(i).getExercise();
                        s = s.concat(",");
                        int totalLength = multiselect_list.get(i).getExercise().length();
                        int position = sb.indexOf(s);
                        int endpos = position + totalLength + 1;
                        sb.delete(position, endpos);
                    } catch (Exception e) {
                        String s = multiselect_list.get(i).getExercise();
                        int totalLength = multiselect_list.get(i).getExercise().length();
                        int position = sb.indexOf(s);
                        int endpos = position + totalLength + 1;
                        sb.delete(position, endpos);
                    }
                    workoutList.remove(multiselect_list.get(i));
                }
                if (selected == 0) {
                    fileEdit.putString("shoulderExercises", sb.toString());
                    fileEdit.putBoolean("shoulderCheck", true);
                } else if (selected == 1) {
                    fileEdit.putString("chestExercises", sb.toString());
                    fileEdit.putBoolean("chestCheck", true);
                } else if (selected == 2) {
                    fileEdit.putString("bicepExercises", sb.toString());
                    fileEdit.putBoolean("bicepCheck", true);
                } else if (selected == 3) {
                    fileEdit.putString("tricepExercises", sb.toString());
                    fileEdit.putBoolean("tricepCheck", true);
                } else if (selected == 4) {
                    fileEdit.putString("backExercises", sb.toString());
                    fileEdit.putBoolean("backCheck", true);
                } else if (selected == 5) {
                    fileEdit.putString("legExercises", sb.toString());
                    fileEdit.putBoolean("legCheck", true);
                } else if (selected == 6) {
                    fileEdit.putString("absExercises", sb.toString());
                    fileEdit.putBoolean("absCheck", true);
                } else if (selected == 7) {
                    fileEdit.putString("cardioExercises", sb.toString());
                    fileEdit.putBoolean("cardioCheck", true);
                }

                Log.d("selected", "value: " + selected);
                fileEdit.apply();

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
            SharedPreferences.Editor fileEdit = presetList.edit();
            StringBuilder sb = new StringBuilder();
            // set current list of exercises to the string builder.
            sb.append(presetList.getString("shoulderExercises", ""));
            String new_ex = getIntent().getStringExtra("newExercise");
            Log.d("NEW_EX", new_ex);
            // append the new exercise onto the string builder.
            WorkoutModel workout = new WorkoutModel(new_ex);
            sb.append(",").append(workout.getExercise());

            // update shared preferences.
            fileEdit.putBoolean("shoulderCheck", true);
            fileEdit.putString("shoulderExercises", sb.toString());
            Log.d("SB", sb.toString());
            fileEdit.apply();

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
