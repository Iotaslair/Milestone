package com.williampembleton.milestone;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class Task {
    private String title;
    private Date date;
    private ArrayList<String> tags;
    private String difficulty;
    private double timeToComplete;
    private int experience;
    private int repeat;

    public Task(String title, Date date, ArrayList<String> tags, String difficulty, double timeToComplete, int repeat) {
        this.title = title;
        this.date = date;
        this.tags = tags;
        this.difficulty = difficulty;
        this.timeToComplete = timeToComplete;
        this.repeat = repeat;
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

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
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

    public boolean repeatOnThisDay(Date testingDate) {
        SimpleDateFormat monthFormatter = new SimpleDateFormat("MM/dd/YYYY");
        monthFormatter.setLenient(false);
        Log.d("Me Testing", "Date passed in " + monthFormatter.format(testingDate));
        Date dueDate = getDate();

        String dueDateString = monthFormatter.format(dueDate);
        Date dueDateCopy = null;
        try {
            dueDateCopy = monthFormatter.parse(dueDateString);
        } catch (Exception e) {
            Log.d("TASK FORMATTING ERROR", "In repeat on this day");
        }
        Log.d("Me Testing", "Task's due date " + monthFormatter.format(dueDate));
        Log.d("Me Testing", "dueDateString " + dueDateString);
        Log.d("Me Testing ", "dueDateCopy " + monthFormatter.format(dueDateCopy));
        /*
        while (dueDateCopy.before(testingDate)) {
            GregorianCalendar calendar = new GregorianCalendar(dueDateCopy.getYear() + 1900, dueDateCopy.getMonth(), dueDateCopy.getDate() - 1);
            String convertedString = "" + (1 + calendar.get(GregorianCalendar.MONTH)) + "/";
            convertedString += (1 + calendar.get(GregorianCalendar.DAY_OF_MONTH)) + "/";
            convertedString += calendar.get(GregorianCalendar.YEAR);
            //Log.d("Me Testing", "before adding " + convertedString);

            calendar.add(GregorianCalendar.DAY_OF_MONTH, getRepeat());


            String stringPlusRepeat = "" + (1 + calendar.get(GregorianCalendar.MONTH)) + "/";
            stringPlusRepeat += (1 + calendar.get(GregorianCalendar.DAY_OF_MONTH)) + "/";
            stringPlusRepeat += calendar.get(GregorianCalendar.YEAR);
            //Log.d("Me Testing", "after adding " + stringPlusRepeat);

            SimpleDateFormat formatter = new SimpleDateFormat("MM/ddd/yyyy");
            formatter.setLenient(false);
            try {
                dueDateCopy = formatter.parse(stringPlusRepeat);
                if (dueDateCopy.equals(testingDate))
                    return true;
            } catch (Exception e) {
                //Log.d("ME TESTING", "Failure parsing " + e.toString());
            }
        }
        */


        return false;
    }

}
