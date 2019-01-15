package com.williampembleton.milestone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskList2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list2);

        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        ArrayList<Task> tasksAL = new ArrayList();
        Iterator<Task> iterator = AllTasks.iterator();
        while(iterator.hasNext())
        {
            tasksAL.add(iterator.next());
        }

        RecyclerView.Adapter adapter = new TaskAdapter2(tasksAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
