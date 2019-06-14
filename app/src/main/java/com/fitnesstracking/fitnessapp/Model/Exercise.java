package com.fitnesstracking.fitnessapp.Model;

import android.content.Context;
import android.icu.util.Calendar;
import android.util.Log;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Exercise {

    private String ex_name;
    private boolean isFinished;
    // Muscle group
    private String type;
    private int sets;
    private int reps;
    private double weight;
    private float max;
    // Date completed
    private Calendar date;
    private String date_string;

    // Constructor
    public Exercise(String name, int sets, int reps, int weight, Calendar date) {
        this.ex_name = name;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.date = date;
    }

    public Exercise(String name, int reps, double weight, float repmax) {
        this.ex_name = name;
        this.reps = reps;
        this.weight = weight;
        this.max = repmax;
    }

    public Exercise() {

    }

    // One Rep Max
    public String OneRepMax(int reps, double weight){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        float val = (float) weight * (1 + (float) reps / 30);

        return df.format(val);
    }

    // Getter and setter methods
    public String getEx_name() {
        return ex_name;
    }

    public void setEx_name(String ex_name) {
        this.ex_name = ex_name;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public float getMax() {
        return Float.parseFloat(OneRepMax(reps,weight));
    }

    public void setMax(float max) {
        this.max = max;
    }
    // Returns date as String "dd/mm/yy"
    public String getDate(Context context) {
        DateFormat df = android.text.format.DateFormat.getDateFormat(context);
        Log.d("DATE","date is:" + this.date.getTime().toString());
        return df.format(this.date.getTime());
    }

    public String getDate() {
        return this.date_string;
    }

    public void setDate(String strDate) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);

        try {
            c.setTime(sdf.parse(strDate));
            this.date = c;
            this.date_string = strDate;
            Log.d("Date","setTime() worked");
        }
        catch(Exception e) {
            Log.d("Date","Date exception caught:" + e);
        }
    }

    public void setFinished(boolean finished) {
        this.isFinished = finished;
    }

    public boolean getFinished() {
        return isFinished;
    }

}
