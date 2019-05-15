package com.example.myapplication.Model;

import java.util.ArrayList;

public class CreatedWorkout {

    private String name;
    private String day_of_week;
    //private ArrayList<Exercise> workouts;
    private int total_workouts;

    public CreatedWorkout (String name, String day, int total_workouts) {
        this.name = name;
        this.day_of_week = day;
        //this.workouts = new ArrayList<>();
        this.total_workouts = total_workouts;
    }

    public CreatedWorkout (String name, String day) {
        this.name = name;
        this.day_of_week = day;
        //this.workouts = new ArrayList<>();
        this.total_workouts = 0;
    }


    // Add exercise
//    public void addExercise(Exercise e) {
//        this.workouts.add(e);
//    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(String day_of_week) {
        this.day_of_week = day_of_week;
    }

    public int getTotal_workouts() {
        return total_workouts;
    }

    public void setTotal_workouts() {
        this.total_workouts = total_workouts;
    }

//    public ArrayList<Exercise> getWorkouts() {
//        return workouts;
//    }

//    public void setWorkouts(ArrayList<Exercise> workouts) {
//        this.workouts = workouts;
//    }

}
