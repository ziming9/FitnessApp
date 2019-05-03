package com.example.myapplication.Model;

import java.util.ArrayList;

public class CreatedWorkout {

    private String name;
    private String day_of_week;
    private ArrayList<Exercise> workouts;

    public CreatedWorkout (String name, String day) {
        this.name = name;
        this.day_of_week = day;
        this.workouts = new ArrayList<>();
    }


    // Add exercise
    public void addExercise(Exercise e) {
        this.workouts.add(e);
    }

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

    public ArrayList<Exercise> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(ArrayList<Exercise> workouts) {
        this.workouts = workouts;
    }

}
