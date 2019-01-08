package com.williampembleton.milestone;
import java.util.ArrayList;

public class AllTasks {

    static ArrayList<Task> Tasks = new ArrayList<>();

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
        Tasks.add(inTask);
    }

    public static void removeTask(Task inTask)
    {
        Tasks.remove(inTask);
    }
}

