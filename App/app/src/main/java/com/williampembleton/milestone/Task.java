package com.williampembleton.milestone;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class Task{
    private String title;
    private Date date;
    private ArrayList<String> tags;
    private String difficulty;
    private double timeToComplete;
    private int experience;




    public Task(String title, Date date, ArrayList<String> tags, String difficulty, double timeToComplete)
    {
        this.title = title;
        this.date = date;
        this.tags = tags;
        this.difficulty = difficulty;
        this.timeToComplete = timeToComplete;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public double getTimeToComplete() {
        return timeToComplete;
    }

    public void setTimeToComplete(double timeToComplete) {
        this.timeToComplete = timeToComplete;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getIntDifficulty(Context context)
    {
        switch (difficulty)
        {
            case "Easy":
                return 1;
            case "Semi-Easy":
                return 2;
            case "Average":
                return 3;
            case "Kinda Hard":
                return 4;
            case "Difficult":
                return 5;
            default:
                Toast.makeText(context, "You forgot to change the strings for difficulty in getIntDifficulty in Task.java", Toast.LENGTH_SHORT).show();
                return 0;
        }
    }

}
