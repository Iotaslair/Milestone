package com.williampembleton.milestone;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class AllTasks {

    static ArrayList<Task> tasks = new ArrayList<>();
    static ArrayList<Task> searchableTasks = new ArrayList<>();

    public AllTasks() {}


    public static void addTask(Task inTask)
    {
        tasks.add(inTask);
        saveTasks();
    }

    public static void addTask(int position, Task inTask)
    {
        tasks.add(position, inTask);
        saveTasks();
    }

    //saves all your tasks so they are saved when you exit the app
    private static void saveTasks()
    {
        SharedPreferences sharedPreferences = Calendar.sharedPreferences;
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(AllTasks.getList());
        editor.putString("task list", json);
        editor.apply();
    }

    public static void removeTask(Task inTask)
    {
        tasks.remove(inTask);
        saveTasks();
    }

    public static Task getTask(int position)
    {
        return tasks.get(position);
    }

    public static void setList(ArrayList<Task> list)
    {
        tasks = list;
    }

    public static Iterator iterator()
    {
        return tasks.iterator();
    }

    //used when searching and TaskListSearched
    public static void setSearchableTasks(ArrayList<Task> searchableTasks) { AllTasks.searchableTasks = searchableTasks; }

    //used when searching and TaskListSearched
    public static Iterator<Task> searchableIterator(){return searchableTasks.iterator();}

    public static int size(){return tasks.size();}

    public static ArrayList<Task> getList(){return tasks;}

    //called by Calendar and checks if players have completed all their tasks
    public static void streak(Context context) {
        int goodDay = 0;
        for(Task x: tasks)
        {
            long plusOneDay = x.getDate().getTime() + (24 * 60 * 60 * 1000);
            Date convertedDatePlusOne = new Date(plusOneDay);
            if(convertedDatePlusOne.before(new Date()))
            {
                Log.d("ME TESTING", "Decreased Health");
                Player.decreaseHealth(1);
                goodDay++;
            }
        }

        if(goodDay == 0)
            Player.increaseStreak();
        else
            Toast.makeText(context, "You take " + goodDay + " damage from failed tasks", Toast.LENGTH_LONG).show();

    }
}

