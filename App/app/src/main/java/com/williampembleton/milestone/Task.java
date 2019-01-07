package com.williampembleton.milestone;

import java.util.ArrayList;
import java.util.Date;

public class Task{
    private String title;
    private Date date;
    private ArrayList<String> tags;
    private String difficulty;
    private int timeToComplete, experience;




    public Task(String title, Date date, ArrayList<String> tags, String difficulty, int timeToComplete, int experience)
    {
        this.title = title;
        this.date = date;
        this.tags = tags;
        this.difficulty = difficulty;
        this.timeToComplete = timeToComplete;
        this.experience = experience;
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

    public int getTimeToComplete() {
        return timeToComplete;
    }

    public void setTimeToComplete(int timeToComplete) {
        this.timeToComplete = timeToComplete;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

}
