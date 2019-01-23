package com.williampembleton.milestone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Calendar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    ArrayList<Task> allTasksList = null;
    CompactCalendarView compactCalendarView;
    static SharedPreferences sharedPreferences = null;

    //run when the app starts
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        //sets up toolbar
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Calendar");

        setupFab();
        loadData();
        setupDrawer(savedInstanceState, toolbar);
        setupCalendar();
        setupTodaySetter();
        setupArrows();
        setupStreaks();
    }

    //sets up the fab in the calendar activity
    public void setupFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Calendar.this, NewTask.class);
                startActivity(intent);
            }
        });
    }

    //loads tasks from the last time the app has been loaded
    public void loadData() {
        sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Task>>() {
        }.getType();
        allTasksList = gson.fromJson(json, type);
        if (allTasksList == null)
            allTasksList = new ArrayList<>();
        AllTasks.setList(allTasksList);

        Player.loadPlayerInfo();
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
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_calendar);
        }
    }

    //sets up the calendar and does the events
    public void setupCalendar() {
        compactCalendarView = findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        //sets up month textview
        TextView monthTextView = findViewById(R.id.month);
        Date currentDate = new Date();
        SimpleDateFormat monthFormatter = new SimpleDateFormat("MMMM YYYY");
        monthTextView.setText(monthFormatter.format(currentDate));

        //setup events
        for (Task x : allTasksList) {
            String[] task = x.toString().split("∟");

            String dateString = task[1];

            SimpleDateFormat formatter = null;
            Date convertedDate = null;
            try {
                formatter = new SimpleDateFormat("MM/dd/yy");
                formatter.setLenient(false);
                convertedDate = formatter.parse(dateString);
            } catch (Exception e) {
                Snackbar.make(drawerLayout, "Failure formatting the date in Calendar.setupCalendar", Snackbar.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), "Failure formatting the date in Calendar.setupCalendar", Toast.LENGTH_LONG).show();
            }

            Event event = new Event(Color.RED, convertedDate.getTime(), task[0]);
            compactCalendarView.addEvent(event);
        }

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                compactCalendarView.setCurrentSelectedDayBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccentLight));
                //sets current day to normal color to stop it appearing that it's selected
                compactCalendarView.setCurrentDayBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                if (!events.isEmpty()) {
                    ArrayList<Task> finalTasks = new ArrayList<>();
                    Iterator<Task> tempTasks = AllTasks.iterator();
                    for (Event event : events) {
                        while (tempTasks.hasNext()) {
                            Task temp = tempTasks.next();
                            if (temp.getDate().getTime() == event.getTimeInMillis()) {
                                finalTasks.add(temp);
                            }
                        }
                    }
                    AllTasks.setSearchableTasks(finalTasks);
                    Intent intent = new Intent(Calendar.this, TaskListSearched.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                compactCalendarView.setCurrentSelectedDayBackgroundColor(Color.WHITE);
                //sets current day to bright color so it seems like it is selected
                compactCalendarView.setCurrentDayBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccentLight));
                TextView monthTextView = findViewById(R.id.month);
                SimpleDateFormat monthFormatter = new SimpleDateFormat("MMMM YYYY");
                monthTextView.setText(monthFormatter.format(firstDayOfNewMonth));
            }
        });
    }

    //sets up the little icon that moves the calendar to today when you're on a weird month
    public void setupTodaySetter() {
        ImageView today = findViewById(R.id.today);
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compactCalendarView.setCurrentDate(new Date());
                compactCalendarView.scrollLeft();
                compactCalendarView.scrollRight();
                compactCalendarView.setCurrentSelectedDayBackgroundColor(Color.WHITE);
                //sets current day to bright color so it seems like it is selected
                compactCalendarView.setCurrentDayBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccentLight));
            }
        });
    }

    //sets up the little arrows that move the calendar's month
    public void setupArrows() {
        ImageView left = findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compactCalendarView.scrollLeft();
            }
        });
        ImageView right = findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compactCalendarView.scrollRight();
            }
        });
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
                break;
            }
            case R.id.nav_new_task: {
                Intent intent = new Intent(Calendar.this, NewTask.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_task_list: {
                Intent intent = new Intent(Calendar.this, TaskList.class);
                startActivity(intent);
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
                    Intent intent = new Intent(Calendar.this, TaskListSearched.class);
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

}
