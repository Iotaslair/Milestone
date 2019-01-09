package com.williampembleton.milestone;
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

    public static void addTask(Task inTask)
    {
        tasks.add(inTask);
    }

    public static void removeTask(Task inTask)
    {
        tasks.remove(inTask);
    }

    public static Task getTask(int position)
    {
        return tasks.get(position);
    }

    public static Iterator iterator()
    {
        return tasks.iterator();
    }
}

