package com.williampembleton.milestone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        ArrayList<Task> tasksAL = new ArrayList();
        Iterator<Task> iterator = AllTasks.iterator();
        while (iterator.hasNext()) {
            tasksAL.add(iterator.next());
        }

        TaskAdapter adapter = new TaskAdapter(tasksAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_task_list);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_calendar: {
                Intent intent = new Intent(TaskList.this, Calendar.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_new_task: {
                Intent intent = new Intent(TaskList.this, NewTask.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_task_list: {
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }
}
