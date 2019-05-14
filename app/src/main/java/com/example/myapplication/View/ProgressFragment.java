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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        Exercise ex_one = new Exercise("Barbell Bench",5,8,140,c_one);

        // Date two
        Calendar c_two = Calendar.getInstance();
        c_two.set(2019,5,6);
        // Exercise two
        Exercise ex_two  = new Exercise("Barbell Bench",5,8,150,c_two);

        // Date three
        Calendar c_three = Calendar.getInstance();
        c_three.set(2019,5,13);
        // Exercise three
        Exercise ex_three = new Exercise("Barbell Bench",5,8,145,c_three);

        // Date four
        Calendar c_four = Calendar.getInstance();
        c_four.set(2019,5,20);
        // Exercise four
        Exercise ex_four = new Exercise("Barbell Bench",5,8,155,c_four);

        // Date five
        Calendar c_five = Calendar.getInstance();
        c_five.set(2019,5,27);
        // Exercise five
        Exercise ex_five = new Exercise("Barbell Bench",5,8,150,c_five);

        // Date six
        Calendar c_six = Calendar.getInstance();
        c_six.set(2019,6,3);
        // Exercise five
        Exercise ex_six = new Exercise("Barbell Bench",5,8,160,c_six);

        // Date seven
        Calendar c_seven = Calendar.getInstance();
        c_seven.set(2019,6,10);
        // Exercise seven
        Exercise ex_seven = new Exercise("Barbell Bench",5,8,170,c_seven);

        // Create list of exercise objects for Entry List input
        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(ex_one);
        exercises.add(ex_two);
        exercises.add(ex_three);
        exercises.add(ex_four);
        exercises.add(ex_five);
        exercises.add(ex_six);
     //   exercises.add(ex_seven);


        // Entry lists created from exercise list
        List<Entry> entries = new ArrayList<Entry>();

        int count = 0;
        for (Exercise data : exercises) {
            count++;
            // turn your data into Entry objects
            entries.add(new Entry(count,data.getMax()));
        }

        // Add entries to a dataset
        LineDataSet dataSet = new LineDataSet(entries, "Weight");

        // Create Strings array to format x-axis with date values

        String [] dates = new String[exercises.size()];
        for(int i = 0; i < exercises.size(); i++) {
            dates[i] = exercises.get(i).getDate(getContext());
        }

        // X-axis formatter class
        class MyXAxisValueFormatter implements IAxisValueFormatter {

            private String[] mValues;

            public MyXAxisValueFormatter(String[] values) {
                this.mValues = values;
            }

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                // "value" represents the position of the label on the axis (x or y)
                return mValues[(int) value];
            }
        }

        // Axis formatting
        // Set dates string to x-axis of chart
        XAxis x_axis = chart.getXAxis();
        x_axis.setValueFormatter(new MyXAxisValueFormatter(dates));
        x_axis.setTextSize(11);
       // x_axis.setLabelRotationAngle(-60f);
        // Set x-axis to bottom
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        //chart.getXAxis().setCenterAxisLabels(true);
        //x_axis.setCenterAxisLabels(true);
        // Remove gridlines
        //chart.getAxisLeft().setDrawGridLines(false);
        //chart.getXAxis().setDrawGridLines(false);
        chart.getRendererXAxis().getPaintAxisLabels().setTextAlign(Paint.Align.LEFT);

        // Set only one y-axis
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
        dataSet.setValueTextColor(Color.parseColor("#00285e"));
        dataSet.setLineWidth(2);
        dataSet.setValueTextSize(12);
        dataSet.setDrawFilled(true);


        dataSet.setCircleRadius(5);
        Drawable fill_color = ContextCompat.getDrawable(getContext(), R.drawable.chart_fill);
        dataSet.setFillDrawable(fill_color);

        // Create lineData object based on dataset
        LineData lineData = new LineData(dataSet);

        //Remove description label
        chart.getDescription().setEnabled(false);
        // Link lineData to chart
        chart.setData(lineData);
        // chart size
        chart.setMinimumHeight(1000);
        // Animate x-axis
        chart.animateX(5000);
//        chart.getXAxis().setAxisMinimum(0);
//        chart.getXAxis().setAxisMaximum((float) exercises.size());
                // chart legend
        Legend l = chart.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setTextSize(13f);
        l.setXEntrySpace(25f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(15f);
        chart.invalidate();
    }

}
