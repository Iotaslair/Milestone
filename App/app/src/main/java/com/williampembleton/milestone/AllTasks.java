package com.williampembleton.milestone;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;

public class AllTasks {

    static ArrayList<Task> tasks = new ArrayList<>();

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

    public static void addTask(Task inTask,SharedPreferences sharedPreferences)
    {
        tasks.add(inTask);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(tasks);
        editor.putString("task list", json);
        editor.apply();

    }

    public static void removeTask(Task inTask)
    {
        tasks.remove(inTask);
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
}

