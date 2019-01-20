package com.williampembleton.milestone;
import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import static android.content.Context.MODE_PRIVATE;

public class AllTasks extends IntentService {

    static ArrayList<Task> tasks = new ArrayList<>();
    static ArrayList<Task> searchableTasks = new ArrayList<>();

    static AllTasks allTasks = null;

    public AllTasks()
    {
        super("AllTasks");
    }


    public static void addTask(Task inTask)
    {
        tasks.add(inTask);
        saveTasks();
    }

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

    public static ArrayList<Task> getSearchableTasks() { return searchableTasks; }

    public static void setSearchableTasks(ArrayList<Task> searchableTasks) { AllTasks.searchableTasks = searchableTasks; }

    public static Iterator<Task> searchableIterator(){return searchableTasks.iterator();}

    public static int size(){return tasks.size();}

    public static ArrayList<Task> getList(){return tasks;}

    @Override
    protected void onHandleIntent(Intent intent) {
        for(Task x: tasks)
        {
            if(x.getDate().after(new Date()))
            {
                Log.d("ME TESTING", "Decreased Health");
                Player.decreaseHealth(1);
            }
        }
    }
}

