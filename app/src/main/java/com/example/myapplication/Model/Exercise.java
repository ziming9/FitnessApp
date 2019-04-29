package com.example.myapplication.Model;

public class Exercise {

    private String ex_name;
    private Integer sets;
    private Integer reps;

    // Constructor
    Exercise(String name, int sets, int reps) {
        this.ex_name = name;
        this.sets = sets;
        this.reps = reps;
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

}
