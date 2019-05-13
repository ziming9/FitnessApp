package com.example.myapplication.Model;

import android.content.Context;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Exercise {

    private String ex_name;
    // Muscle group
    private String type;
    private Integer sets;
    private Integer reps;
    private Integer weight;
    private Integer max;
    // Date completed
    private Calendar date;

    // Constructor
    public Exercise(String name, int sets, int reps, int weight,Calendar date) {
        this.ex_name = name;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.date = date;
    }

    // One Rep Max
    public int OneRepMax(int reps, int weight){
        return  weight * (1 + reps / 30);
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getMax() {
        return OneRepMax(reps,weight);
    }

    // Returns date as String "dd/mm/yy"
    public String getDate(Context context) {
        DateFormat df = android.text.format.DateFormat.getDateFormat(context);
        return df.format(this.date.getTime());
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

}
