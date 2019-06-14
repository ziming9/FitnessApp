package com.fitnesstracking.fitnessapp.Model;

public class CreatedWorkout {

    private int id;
    private String name;
    private String day_of_week;
    private int total_workouts;

    public CreatedWorkout() {

    }

    public CreatedWorkout (String name, String day, int total_workouts) {
        this.name = name;
        this.day_of_week = day;
        this.total_workouts = total_workouts;
    }

    public CreatedWorkout (String name, String day) {
        this.name = name;
        this.day_of_week = day;
        this.total_workouts = 0;
    }

    // Getters and setters
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

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

    public void setTotal_workouts(int total) {
        this.total_workouts = total;
    }

}
