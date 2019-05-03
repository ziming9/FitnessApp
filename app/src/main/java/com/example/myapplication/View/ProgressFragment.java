package com.example.myapplication.View;


import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Model.CreatedWorkout;
import com.example.myapplication.Model.Exercise;
import com.example.myapplication.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;
import java.util.List;

public class ProgressFragment extends Fragment {
    public static ProgressFragment newInstance() {
        return new ProgressFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Test chart
        LineChart chart = (LineChart) getView().findViewById(R.id.test_chart);

        // Create Workout to hold exercises
        CreatedWorkout workout = new CreatedWorkout("Chest","Monday");

        // Date one
        Calendar c_one = Calendar.getInstance();
        // Exercise one
        Exercise ex_one = new Exercise("Barbell Bench",5,8,150,c_one);

        // Date two
        Calendar c_two = Calendar.getInstance();
        c_two.set(2019,5,5);
        // Exercise two
        Exercise ex_two  = new Exercise("Barbell Bench",5,8,160,c_two);

        // Date three
        Calendar c_three = Calendar.getInstance();
        c_three.set(2019,5,7);
        // Exercise three
        Exercise ex_three = new Exercise("Barbell Bench",5,8,165,c_three);

        // Date four
        Calendar c_four = Calendar.getInstance();
        c_three.set(2019,5,9);
        // Exercise four
        Exercise ex_four = new Exercise("Barbell Bench",5,8,175,c_four);

        // Date five
        Calendar c_five = Calendar.getInstance();
        c_three.set(2019,5,14);
        // Exercise five
        Exercise ex_five = new Exercise("Barbell Bench",5,8,175,c_five);

        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(ex_one);
        exercises.add(ex_two);
        exercises.add(ex_three);
        exercises.add(ex_four);
        exercises.add(ex_five);


        // Chart
        List<Entry> entries = new ArrayList<Entry>();

        int count = 0;
        for (Exercise data : exercises) {
            count++;
            // turn your data into Entry objects
            entries.add(new Entry(count,data.getMax()));
        }

        // add entries to dataset
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        dataSet.setColor(Color.parseColor("#e94984"));
        dataSet.setValueTextColor(Color.parseColor("#e94984"));


        // Set on chart
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }

}
