package com.williampembleton.milestone;
import java.util.ArrayList;

public class AllTasks {

    static ArrayList<Task> Tasks = new ArrayList<>();

    AllTasks allTasks = null;

    private AllTasks()
    {

    }

    public AllTasks getInstance()
    {
        if (allTasks == null)
            return new AllTasks();
        else
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

