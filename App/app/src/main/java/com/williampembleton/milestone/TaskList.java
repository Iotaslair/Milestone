package com.williampembleton.milestone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    FloatingActionButton fab = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        //sets up toolbar
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        setupRecyclerView();
        setupFab();
        setupDrawer(savedInstanceState, toolbar);

    }

    //sets up the list of all the tasks
    public void setupRecyclerView() {
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
        adapter.setViewAndContext(drawerLayout,getApplicationContext());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx,int dy){
                super.onScrolled(recyclerView, dx, dy);

                if (dy >0) {
                    // Scroll Down
                    if (fab.isShown()) {
                        fab.hide();
                    }
                }
                else if (dy <0) {
                    // Scroll Up
                    if (!fab.isShown()) {
                        fab.show();
                    }
                }
            }
        });
    }

    //sets up the fab in the calendar activity
    public void setupFab()
    {
        fab = findViewById(R.id.fabToNewTask);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskList.this, NewTask.class);
                startActivity(intent);
            }
        });
    }

    //sets up the navigation drawer (thing you pull in from the left)
    public void setupDrawer(Bundle savedInstanceState, Toolbar toolbar) {
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ArrayList<Integer> playerInfo = Player.playerInfo;

        View headerView = navigationView.getHeaderView(0);
        ProgressBar healthBar = headerView.findViewById(R.id.healthBar);
        ProgressBar expBar = headerView.findViewById(R.id.expBar);
        TextView levelText = headerView.findViewById(R.id.playerLevel);
        healthBar.setProgress(playerInfo.get(0),true);
        expBar.setProgress(playerInfo.get(1));
        expBar.setMax(playerInfo.get(2));
        levelText.setText("Player level " + playerInfo.get(3));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_task_list);
        }
    }
    //used for navigation menu so when something is clicked I can do stuff with it
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

    //when back is pressed and the drawer is pressed you close the navigation drawer instead of exit the activity
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_searchable, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ArrayList<Task> foundTasks = new ArrayList<>();
                Iterator<Task> allTasks = AllTasks.iterator();

                while(allTasks.hasNext())
                {
                    Task temp = allTasks.next();
                    if(temp.getTitle().contains(s))
                    {
                        foundTasks.add(temp);
                    }
                    else
                    {
                        ArrayList<String> tags = temp.getTags();
                        for(String tag: tags)
                        {
                            if(tag.contains(s))
                            {
                                foundTasks.add(temp);
                                break;
                            }
                        }
                    }
                }

                if(foundTasks.isEmpty())
                    Snackbar.make(drawerLayout, "Couldn't find any tasks with that name or tasks with that tag", Snackbar.LENGTH_LONG).show();
                else
                {
                    AllTasks.setSearchableTasks(foundTasks);
                    Intent intent = new Intent(TaskList.this, TaskListSearched.class);
                    startActivity(intent);
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
