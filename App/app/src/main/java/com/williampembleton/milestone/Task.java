package com.williampembleton.milestone;

import java.util.ArrayList;
import java.util.Date;

public class Task{
    private String title;
    private Date date;
    private ArrayList<String> tags;
    private int difficulty;

    public Task(String title, Date date, ArrayList<String> tags, int difficulty)
    {
        this.title = title;
        this.date = date;
        this.tags = tags;
        this.difficulty = difficulty;
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

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

}
