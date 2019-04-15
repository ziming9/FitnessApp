package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WorkoutListFragment extends AppCompatActivity {

    private String[] workoutsArray = { "Barbell Shoulder Press", "Dumbbell Shoulder Press",
            "Dumbbell Lateral Raise", "Front Raise", "Face Pull","Cable Reverse Fly", "Workout",
            "Workout","Workout","Workout","Workout","Workout","Workout","Workout","Workout",
            "Workout","Workout","Workout","Workout","Workout","Workout","Workout"};

    private ListView workoutsListView;
    private ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_workout_list);

        // ListView implementation
        workoutsListView = (ListView) findViewById(R.id.workout_list);

        // this-The current activity context.
        // Second param is the resource Id for list layout row item
        // Third param is input array
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, workoutsArray);
        workoutsListView.setAdapter(arrayAdapter);

    }
}
