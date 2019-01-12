package com.williampembleton.milestone;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

        FloatingActionButton fabToNewTask = findViewById(R.id.fabToNewTask);
        fabToNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskList.this, NewTask.class);
                startActivity(intent);
            }
        });

        ArrayList<String> tasksAL = new ArrayList();
        Iterator<Task> iterator = AllTasks.iterator();
        while(iterator.hasNext())
        {
            tasksAL.add(iterator.next().toString());
        }

        String[] tasks = new String[tasksAL.size()];
        tasks = tasksAL.toArray(tasks);


        ListAdapter adapter = new TaskAdapter(this,tasks);
        ListView listView = findViewById(R.id.Tasks);
        listView.setAdapter(adapter);
    }
}
