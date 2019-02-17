package com.williampembleton.milestone;

import android.content.Intent;
import android.graphics.Color;
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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class TaskList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private DrawerLayout drawerLayout;
    FloatingActionButton fab = null;
    TaskAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        //sets up toolbar
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        setupFab();
        setupRecyclerView();
        setupDrawer(savedInstanceState, toolbar);
        setupStreaks();

    }

    //sets up the fab in the calendar activity
    public void setupFab() {
        fab = findViewById(R.id.fabToNewTask);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskList.this, NewTask.class);
                startActivity(intent);
            }
        });
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

        mAdapter = new TaskAdapter(tasksAL);

        recyclerView.setLayoutManager(layoutManager);
        mAdapter.setViewAndContext(drawerLayout, getApplicationContext());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    // Scroll Down
                    if (fab.isShown()) {
                        fab.hide();
                    }
                } else if (dy < 0) {
                    // Scroll Up
                    if (!fab.isShown()) {
                        fab.show();
                    }
                }
            }
        });

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        ItemTouchHelper.SimpleCallback editItem = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(editItem).attachToRecyclerView(recyclerView);
    }

    //sets up the navigation drawer (thing you pull in from the left)
    public void setupDrawer(Bundle savedInstanceState, Toolbar toolbar) {
        drawerLayout = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        ProgressBar healthBar = headerView.findViewById(R.id.healthBar);
        ProgressBar expBar = headerView.findViewById(R.id.expBar);
        TextView levelText = headerView.findViewById(R.id.playerLevel);
        TextView healthText = headerView.findViewById(R.id.healthText);
        TextView expText = headerView.findViewById(R.id.experienceText);
        TextView streak = headerView.findViewById(R.id.streak);

        healthBar.setProgress((int) (Player.playerInfo.get(0) + 0));
        expBar.setMax((int) (Player.playerInfo.get(2) + 0));
        expBar.setProgress((int) (Player.playerInfo.get(1) + 0));
        levelText.setText("Level " + Player.playerInfo.get(3));
        healthText.setText("" + Player.playerInfo.get(0) + "/50");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        expText.setText("" + numberFormat.format(Player.playerInfo.get(1)) + "/" + numberFormat.format(Player.playerInfo.get(2)));
        streak.setText("Streak: " + Player.playerInfo.get(4));

        Menu menu = navigationView.getMenu();
        MenuItem mlevel10 = menu.findItem(R.id.level10);
        MenuItem mlevel25 = menu.findItem(R.id.level25);
        MenuItem mlevel50 = menu.findItem(R.id.level50);
        MenuItem mlevel100 = menu.findItem(R.id.level100);

        ProgressBar level10 = mlevel10.getActionView().findViewById(R.id.progressBar);
        ProgressBar level25 = mlevel25.getActionView().findViewById(R.id.progressBar);
        ProgressBar level50 = mlevel50.getActionView().findViewById(R.id.progressBar);
        ProgressBar level100 = mlevel100.getActionView().findViewById(R.id.progressBar);

        level10.setMax(10);
        level10.setProgress((int) (Player.playerInfo.get(3) + 0));
        level25.setMax(25);
        level25.setProgress((int) (Player.playerInfo.get(3) + 0));
        level50.setMax(50);
        level50.setProgress((int) (Player.playerInfo.get(3) + 0));
        level100.setMax(100);
        level100.setProgress((int) (Player.playerInfo.get(3) + 0));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                View headerView = navigationView.getHeaderView(0);
                ProgressBar healthBar = headerView.findViewById(R.id.healthBar);
                ProgressBar expBar = headerView.findViewById(R.id.expBar);
                TextView levelText = headerView.findViewById(R.id.playerLevel);
                TextView healthText = headerView.findViewById(R.id.healthText);
                TextView expText = headerView.findViewById(R.id.experienceText);
                TextView streak = headerView.findViewById(R.id.streak);

                healthBar.setProgress((int) (Player.playerInfo.get(0) + 0));
                expBar.setMax((int) (Player.playerInfo.get(2) + 0));
                expBar.setProgress((int) (Player.playerInfo.get(1) + 0));
                levelText.setText("Level " + Player.playerInfo.get(3));
                healthText.setText("" + Player.playerInfo.get(0) + "/50");
                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
                expText.setText("" + numberFormat.format(Player.playerInfo.get(1)) + "/" + numberFormat.format(Player.playerInfo.get(2)));
                streak.setText("Streak: " + Player.playerInfo.get(4));

                Menu menu = navigationView.getMenu();
                MenuItem mlevel10 = menu.findItem(R.id.level10);
                MenuItem mlevel25 = menu.findItem(R.id.level25);
                MenuItem mlevel50 = menu.findItem(R.id.level50);
                MenuItem mlevel100 = menu.findItem(R.id.level100);

                ProgressBar level10 = mlevel10.getActionView().findViewById(R.id.progressBar);
                ProgressBar level25 = mlevel25.getActionView().findViewById(R.id.progressBar);
                ProgressBar level50 = mlevel50.getActionView().findViewById(R.id.progressBar);
                ProgressBar level100 = mlevel100.getActionView().findViewById(R.id.progressBar);

                level10.setMax(10);
                level10.setProgress((int) (Player.playerInfo.get(3) + 0));
                level25.setMax(25);
                level25.setProgress((int) (Player.playerInfo.get(3) + 0));
                level50.setMax(50);
                level50.setProgress((int) (Player.playerInfo.get(3) + 0));
                level100.setMax(100);
                level100.setProgress((int) (Player.playerInfo.get(3) + 0));
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_task_list);
        }
    }

    //does some logic to see if today is a different day than the last time the app opened
    public void setupStreaks() {
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/ddd/yyyy");
        String todayString = formatter.format(today);
        Date today2 = null;
        try {
            today2 = formatter.parse(todayString);
        } catch (Exception e) {
            Log.d("ME TESTING", "Parse failure in Calendar in setupStreaks");
        }

        //if today's time is the same as the last time a person logged in
        if (Player.playerInfo.get(5) != today2.getTime()) {
            long diff = today2.getTime() - Player.playerInfo.get(5);
            long diffDays = diff / (24 * 60 * 60 * 1000);
            while (diffDays != 0) {
                if (diffDays < 0) {
                    Toast.makeText(getApplicationContext(), "You cheated by playing around with changing time. Resetting streaks", Toast.LENGTH_LONG).show();
                    Player.setStreakToZero();
                    Player.setLastDayIn(today2.getTime());
                    Log.d("ME TESTING", "Player Cheated on time");
                    diffDays = 0;
                    return;
                }
                AllTasks.streak(getApplicationContext());
                Log.d("ME TESTING", "Changed streak");
                diffDays--;
            }
        } else
            Log.d("ME TESTING", "Didn't change streak");

        Player.setLastDayIn(today2.getTime());

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
            case R.id.level10: {
                long value = 10 - Player.playerInfo.get(3);
                if (value < 1) {
                    Toast.makeText(getApplicationContext(), "You already got this achievement", Toast.LENGTH_SHORT).show();
                    break;
                }
                Toast.makeText(getApplicationContext(), "You're " + value + " levels away from Level 10", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.level25: {
                long value = 25 - Player.playerInfo.get(3);
                if (value < 1) {
                    Toast.makeText(getApplicationContext(), "You already got this achievement", Toast.LENGTH_SHORT).show();
                    break;
                }
                Toast.makeText(getApplicationContext(), "You're " + value + " levels away from Level 25", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.level50: {
                long value = 50 - Player.playerInfo.get(3);
                if (value < 1) {
                    Toast.makeText(getApplicationContext(), "You already got this achievement", Toast.LENGTH_SHORT).show();
                    break;
                }
                Toast.makeText(getApplicationContext(), "You're " + value + " levels away from Level 50", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.level100: {
                long value = 100 - Player.playerInfo.get(3);
                if (value < 1) {
                    Toast.makeText(getApplicationContext(), "You already got this achievement", Toast.LENGTH_SHORT).show();
                    break;
                }
                Toast.makeText(getApplicationContext(), "You're " + value + " levels away from Level 100", Toast.LENGTH_SHORT).show();
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

    //sets up the little search icon and launches TaskList when a search is found
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

                while (allTasks.hasNext()) {
                    Task temp = allTasks.next();
                    if (temp.getTitle().contains(s)) {
                        foundTasks.add(temp);
                    } else {
                        ArrayList<String> tags = temp.getTags();
                        for (String tag : tags) {
                            if (tag.contains(s)) {
                                foundTasks.add(temp);
                                break;
                            }
                        }
                    }
                }

                if (foundTasks.isEmpty())
                    Snackbar.make(drawerLayout, "Couldn't find any tasks with that name or tasks with that tag", Snackbar.LENGTH_LONG).show();
                else {
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

    //needed to get search working
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

    //run when a user swipes a task and tries to delete it
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (direction == ItemTouchHelper.RIGHT) {
            List<Task> taskList = AllTasks.getList();
            Task task = taskList.get(viewHolder.getAdapterPosition());
            NewTask.setEditAppBarTitle("Edit Task");
            NewTask.setEditDifficulty(task.getDifficulty());
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            NewTask.setEditDueDate(formatter.format(task.getDate()));
            ArrayList<String> listTags = task.getTags();
            String tags = "";
            if (!listTags.isEmpty())
                for (String x : listTags)
                    tags = tags + x + ", ";
            NewTask.setEditTags(tags);
            NewTask.setEditTimeToComplete(task.getTimeToComplete());
            NewTask.setEditTitle(task.getTitle());
            NewTask.setEditPreviousTask(task);

            Intent intent = new Intent(TaskList.this, NewTask.class);
            startActivity(intent);
        } else {


            // get the removed item name to display it in snack bar
            List<Task> taskList = AllTasks.getList();
            String name = AllTasks.getTask(viewHolder.getAdapterPosition()).getTitle();

            // backup of removed item for undo purpose
            final Task deletedItem = taskList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            mAdapter.removeAt(deletedIndex);

            // shows snackbar with Undo option
            Snackbar snackbar = Snackbar.make(drawerLayout, name + " removed from Task List", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    mAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }
    }
}
