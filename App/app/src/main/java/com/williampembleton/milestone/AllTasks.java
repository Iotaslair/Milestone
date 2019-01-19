package com.williampembleton.milestone;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;

import static android.content.Context.MODE_PRIVATE;

public class AllTasks {

    static ArrayList<Task> tasks = new ArrayList<>();
    static ArrayList<Task> searchableTasks = new ArrayList<>();
    static SharedPreferences sharedPreferences;

    static AllTasks allTasks = null;

    private AllTasks()
    {

    }

    public static AllTasks getInstance()
    {
        if (allTasks == null)
            allTasks =  new AllTasks();
        return allTasks;
    }

    public static void addTask(Task inTask)
    {
        tasks.add(inTask);

        SharedPreferences sharedPreferences = Calendar.sharedPreferences;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(tasks);
        editor.putString("task list", json);
        editor.apply();

    }

    public static void removeTask(Task inTask)
    {
        tasks.remove(inTask);

        SharedPreferences sharedPreferences = Calendar.sharedPreferences;
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(AllTasks.getList());
        editor.putString("task list", json);
        editor.apply();
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
}

