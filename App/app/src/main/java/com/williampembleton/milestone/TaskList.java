package com.williampembleton.milestone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Task> tasksAL = new ArrayList();
        Iterator<Task> iterator = AllTasks.iterator();
        while(iterator.hasNext())
        {
            tasksAL.add(iterator.next());
        }

        Task[] tasks = new Task[tasksAL.size()];
        tasks = tasksAL.toArray(tasks);


        ListAdapter adapter = new ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1,tasks);
        ListView listView = findViewById(R.id.Tasks);
        listView.setAdapter(adapter);
    }
}
