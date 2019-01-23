package com.williampembleton.milestone;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Task {
    private String title;
    private Date date;
    private ArrayList<String> tags;
    private String difficulty;
    private double timeToComplete;
    private int experience;

    public Task(String title, Date date, ArrayList<String> tags, String difficulty, double timeToComplete) {
        this.title = title;
        this.date = date;
        this.tags = tags;
        this.difficulty = difficulty;
        this.timeToComplete = timeToComplete;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public double getTimeToComplete() {
        return timeToComplete;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    //gets an integer version of difficulty, used in calculations for xp
    public int getIntDifficulty(Context context) {
        switch (difficulty) {
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
                Toast.makeText(context, "Tell the programmer they forgot to change the strings for difficulty in getIntDifficulty in Task.java", Toast.LENGTH_SHORT).show();
                return 0;
        }
    }

    //used in a few different places, mostly used to transfer data between activities about a task
    public String toString() {
        String datePattern = "MM/dd/yy";
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        String str = getTitle() + "∟";
        Date dueDate = getDate();
        str = str + formatter.format(dueDate);
        str = str + "∟" + getTimeToComplete() + "∟" + getDifficulty() + "∟" + getExperience() + "∟";
        for (int i = 0; i < tags.size(); i++) {
            str = str + tags.get(i) + "∟";
        }
        return str;
    }

}
