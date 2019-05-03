package com.example.myapplication.Model;

import android.icu.util.Calendar;

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

    // Returns date as String
    public String getDate() {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yy", Locale.US);
        return dateformat.format(this.date);
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

}
