package com.example.myapplication.View;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.icu.text.DecimalFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Controller.WorkoutPlanDatabase;
import com.example.myapplication.Model.CreatedWorkout;
import com.example.myapplication.Model.Exercise;
import com.example.myapplication.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgressFragment extends Fragment {

    WorkoutPlanDatabase db;
    LineDataSet dataSet;
    LineChart chart;
    List<Entry> entries;
    Spinner planSpinner;
    ArrayAdapter<String> exArrayAdapter;
    ArrayAdapter<String> plansArrayAdapter;
    ArrayList<CreatedWorkout> wList;
    ArrayList<String> planNames;

    XAxis x_axis;

    public static ProgressFragment newInstance() {
        return new ProgressFragment();
    }

    // X-axis formatter class
    class MyXAxisValueFormatter implements IAxisValueFormatter {

        private ArrayList<String> mValues;

        public MyXAxisValueFormatter(ArrayList<String> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            String stringValue;
            if ( value >= 0 && value <= mValues.size()) {
                // Log.d("X_AXIS","VALUE: " + value + "Index value: "+ mValues.get((int)value -1));
                stringValue = mValues.get((int) value - 1);

            }else {
                stringValue = "";
            }
            return stringValue;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new WorkoutPlanDatabase(getContext(), 1);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Exercise Spinner
        final Spinner exSpinner = this.getView().findViewById(R.id.ex_spinner);


        // Set first value hint
        ArrayList<String> exList = new ArrayList<>();
        exList.add(0,"Exercises");

            // Creates first item gray color and disables selection
            exArrayAdapter = new ArrayAdapter<String>(
                    getContext(),android.R.layout.simple_spinner_item,exList){
                @Override
                public boolean isEnabled(int position){
                    if(position == 0)
                    {
                        // Disable the first item from Spinner
                        // First item will be use for hint
                        return false;
                    }
                    else
                    {
                        return true;
                    }
                }
                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if(position == 0){
                        // Set the hint text color gray
                        tv.setTextColor(Color.GRAY);
                    }
                    else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };
            exArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            exSpinner.setAdapter(exArrayAdapter);

            exSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItemText = (String) parent.getItemAtPosition(position);
                    // If user change the default selection
                    // First item is disable and it is used for hint
                    if(position > 0){

                       if(chart != null && exSpinner.getSelectedItemPosition() != 0 && planSpinner.getSelectedItemPosition() != 0) {
                           Log.d("Date","Ex_Spinner selection: " + exSpinner.getSelectedItem().toString());

                           ArrayList<Exercise> exercises = db.showExerciseLog(wList.get(planSpinner.getSelectedItemPosition()-1).getID() ,exSpinner.getSelectedItem().toString());
                         for(Exercise e : exercises) {
                             Log.d("Date","PF: Exercise max" + e.getMax());
                             Log.d("Date","PF: Exercise date" + e.getDate(getContext()));
                         }
                           if(exercises.get(0) != null) {
                               updateChartData(exercises);
                               chart.notifyDataSetChanged();
                               chart.invalidate();
                           }
                       }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            // Plans Spinner
        planSpinner = this.getView().findViewById(R.id.plans_spinner);
        wList = db.getPlans();
        planNames = new ArrayList<>();

        for (CreatedWorkout c : wList) {
            String name = c.getName();
            planNames.add(name);
        }
        // Set first value hint
        planNames.add(0,"Pick Plan:");

        // Creates first item gray color and disables selection
        plansArrayAdapter = new ArrayAdapter<String>(
                getContext(),android.R.layout.simple_spinner_item,planNames){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        plansArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        planSpinner.setAdapter(plansArrayAdapter);

        planSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getContext(), "Choose Exercise", Toast.LENGTH_SHORT)
                            .show();


                    // Update Exercise Spinner
                    ArrayList<Exercise> exObjList = db.showExercises(wList.get(planSpinner.getSelectedItemPosition()-1).getID());
                    Log.d("CHARTTEST","ExOBJList is: " + Arrays.toString(exObjList.toArray()));
                    final ArrayList<String> exList = new ArrayList<>();
                    exList.add(0,"Exercises");
                    for (Exercise ex : exObjList) {
                        Log.d("CHARTTEST","ex max in list is  " + ex.getMax());
                        exList.add(ex.getEx_name());
                    }
                    exArrayAdapter.clear();
                    exArrayAdapter.addAll(exList);
                    exArrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ///////////////////////////////////////////////// CHART //////////////////////////////
        // Test chart
        chart = (LineChart) getView().findViewById(R.id.test_chart);
        x_axis = chart.getXAxis();
        // Create Workout to hold exercises
        CreatedWorkout workout = new CreatedWorkout("Chest","Monday");
        // Date one
        Calendar c_one = Calendar.getInstance();
        c_one.set(2019,4,1);
        // Exercise one
        Exercise ex_one = new Exercise("Barbell Bench",5,8,140,c_one);

        // Date two
        Calendar c_two = Calendar.getInstance();
        c_two.set(2019,4,6);
        // Exercise two
        Exercise ex_two  = new Exercise("Barbell Bench",5,8,150,c_two);

        // Date three
        Calendar c_three = Calendar.getInstance();
        c_three.set(2019,4,13);
        // Exercise three
        Exercise ex_three = new Exercise("Barbell Bench",5,8,145,c_three);

        // Date four
        Calendar c_four = Calendar.getInstance();
        c_four.set(2019,4,20);
        // Exercise four
        Exercise ex_four = new Exercise("Barbell Bench",5,8,155,c_four);

        // Date five
        Calendar c_five = Calendar.getInstance();
        c_five.set(2019,4,27);
        // Exercise five
        Exercise ex_five = new Exercise("Barbell Bench",5,8,150,c_five);

        // Date six
        Calendar c_six = Calendar.getInstance();
        c_six.set(2019,5,3);
        // Exercise five
        Exercise ex_six = new Exercise("Barbell Bench",5,8,160,c_six);


        // Create list of exercise objects for Entry List input
        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(ex_one);
        exercises.add(ex_two);
        exercises.add(ex_three);
        exercises.add(ex_four);
        exercises.add(ex_five);
        exercises.add(ex_six);


       updateChartData(exercises);

        //Remove description label
        chart.getDescription().setEnabled(false);
        // chart size
        chart.setMinimumHeight(1000);
        // Animate x-axis
        chart.animateX(2500);
//        chart.getXAxis().setAxisMinimum(0);
//        chart.getXAxis().setAxisMaximum((float) exercises.size());
        // chart legend
        Legend l = chart.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setTextSize(13f);
        chart.invalidate();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Spinners","inside ONSTART");

    }

    @Override
    public void onResume() {
        Log.d("Spinners","inside ONRESUME");
        super.onResume();
        // Updated database
        db = new WorkoutPlanDatabase(getContext(), 1);

        // Update plans spinner
        wList = db.getPlans();
        for(CreatedWorkout c : wList) {
            Log.d("Spinners", "Updated wList Workouts are" + c.getName());
        }
       ArrayList<String> newPlans = new ArrayList<>();
        for (CreatedWorkout c : wList) {
            String name = c.getName();
            newPlans.add(name);
        }

        // Set first value hint
        newPlans.add(0,"Pick Plan:");
        plansArrayAdapter.clear();
        plansArrayAdapter.addAll(newPlans);
        plansArrayAdapter.notifyDataSetChanged();

        // Update Exercise Spinner with selected plan
        if (planSpinner.getSelectedItemPosition() != 0 && planSpinner.getSelectedItemPosition() - 1 < wList.size() ) {
            ArrayList<Exercise> exObjList = db.showExercises(wList.get(planSpinner.getSelectedItemPosition() - 1).getID());
            Log.d("CHARTTEST","onRESUME ExOBJList is: " + Arrays.toString(exObjList.toArray()));

            final ArrayList<String> exList = new ArrayList<>();
            exList.add(0,"Exercises");
            for (Exercise ex : exObjList) {
                Log.d("CHARTTEST","ex in list is  " + ex.getEx_name());
                exList.add(ex.getEx_name());
            }
            exArrayAdapter.clear();
            exArrayAdapter.addAll(exList);
            exArrayAdapter.notifyDataSetChanged();
        }
    }

    // Update chart data
    public void updateChartData(ArrayList<Exercise> data) {
        int count = 0;
        List<Entry> new_entries = new ArrayList<Entry>();
        for (Exercise ex : data) {
            count++;
            // turn your data into Entry objects
            new_entries.add(new Entry(count, ex.getMax()));
        }

        // Add entries to a dataset
        //dataSet  = new LineDataSet(new_entries, "Weight (lbs)");

        // Create Strings array to format x-axis with date values

        ArrayList<String> dates = new ArrayList<String>();
        for(int i = 0; i < data.size(); i++) {
            if (data.get(i) != null) {
                Log.d("CHART", "data.get(i): " + data.get(i).getDate(getContext()));
                dates.add(i,data.get(i).getDate(getContext()));
            } else {
                Toast.makeText(getContext(),"No workouts logged",Toast.LENGTH_LONG).show();
            }
        }

        // Add entries to a dataset
        dataSet  = new LineDataSet(new_entries, "Weight (lbs)");

        // Create Strings array to format x-axis with date values
        ArrayList<String> newDates= new ArrayList<String>();
        for(int i = 0; i < data.size(); i++) {
            newDates.add(i,data.get(i).getDate(getContext()));
        }

        /////////// Axis formatting
        // X-axis
        x_axis = chart.getXAxis();
        x_axis.setValueFormatter(new MyXAxisValueFormatter(newDates));
        x_axis.setTextSize(12);
        x_axis.setGranularity(1f);

        // Set x-axis to bottom
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        //chart.getXAxis().setCenterAxisLabels(true);
        //x_axis.setCenterAxisLabels(true);
        chart.getXAxis().setSpaceMin(0.15f);
        chart.getXAxis().setAvoidFirstLastClipping(true);


        // Y-axis
        YAxis rightYAxis = chart.getAxisRight();
        YAxis leftYAxis = chart.getAxisLeft();
        leftYAxis.setTextSize(13);
        leftYAxis.setAxisMinimum(135);
        rightYAxis.setEnabled(false);
        leftYAxis.setSpaceTop(10);
        leftYAxis.setSpaceBottom(5);


        // Data Point formatting
        class MyValueFormatter implements IValueFormatter {

            private DecimalFormat mFormat;

            public MyValueFormatter() {
                mFormat = new DecimalFormat("###,###,###"); // use no decimals
            }

            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return mFormat.format(value); // in case you want to add percent
            }
        }

        dataSet.setValueFormatter(new MyValueFormatter());
        // Line color format
        dataSet.setColor(Color.parseColor("#e94984"));
        dataSet.setCircleColor(Color.parseColor("#e94984"));
        dataSet.setValueTextColor(Color.parseColor("#000000"));
        dataSet.setLineWidth(2);
        dataSet.setValueTextSize(12);
        dataSet.setDrawFilled(true);

        dataSet.setCircleRadius(5);
        Drawable fill_color = ContextCompat.getDrawable(getContext(), R.drawable.chart_fill);
        dataSet.setFillDrawable(fill_color);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        //Remove description label
        chart.getDescription().setEnabled(false);
        // chart size
        chart.setMinimumHeight(1000);
        // Animate x-axis
        chart.animateX(500);
        // chart legend
        Legend l = chart.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setTextSize(13f);
        chart.invalidate();
    }
}
