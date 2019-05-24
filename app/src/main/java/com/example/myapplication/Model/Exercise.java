package com.example.myapplication.Model;

import android.content.Context;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Exercise {

    private String ex_name;
    // Muscle group
    private String type;
    private int sets;
    private int reps;
    private double weight;
    private float max;
    // Date completed
    private Calendar date;

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
    public float OneRepMax(int reps, double weight){
        return  (float) weight * (1 + reps / 30);
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

    public Float getMax() {
        return OneRepMax(reps,weight);
    }

    public void setMax(Float max) {
        this.max = max;
    }
    // Returns date as String "dd/mm/yy"
    public String getDate(Context context) {
        DateFormat df = android.text.format.DateFormat.getDateFormat(context);
        Log.d("DATE","date is:" + this.date.getTime().toString());
        return df.format(this.date.getTime());
    }

    public void setDate(Calendar date) {
        this.date = date;
    }


}
