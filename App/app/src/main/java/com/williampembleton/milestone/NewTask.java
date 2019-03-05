package com.williampembleton.milestone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.Locale;

public class NewTask extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    String difficulty = "";
    private DrawerLayout drawerLayout;
    //used for editing a task
    static String editDifficulty = "";
    static String editAppBarTitle = "New Task";
    static String editTitle = "";
    static String editDueDate = "";
    static String editTags = "";
    static double editTimeToComplete = 0.0;
    static Task editPreviousTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        //sets up toolBar
        Toolbar toolbar = findViewById(R.id.app_bar);
        toolbar.setTitle(editAppBarTitle);
        setSupportActionBar(toolbar);

        //used to see if we're editing a task right now
        if (editPreviousTask == null) {
            setupDate();
            setupSpinner(0);
            setupDrawer(savedInstanceState, toolbar);
            setupStreaks();
        } else {
            String[] difficulty = getResources().getStringArray(R.array.difficultyList);
            for (int i = 0; i < difficulty.length; i++)
                if (difficulty[i].equals(editDifficulty))
                    setupSpinner(i);
            setupDrawer(savedInstanceState, toolbar);
            setupStreaks();
            setupRest();
        }
    }

    //sets up the date text to tomorrow
    public void setupDate() {
        EditText dueDateBox = findViewById(R.id.DueDate);
        java.util.Calendar currentDate = GregorianCalendar.getInstance();
        String dueDateString = "" + (1 + currentDate.get(java.util.Calendar.MONTH)) + "/";
        dueDateString += (1 + currentDate.get(java.util.Calendar.DAY_OF_MONTH)) + "/";
        dueDateString += currentDate.get(java.util.Calendar.YEAR);
        if (editDueDate.equals(""))
            dueDateBox.setText(dueDateString);
        else
            dueDateBox.setText(editDueDate);
    }

    //sets up the spinner (difficulty)
    public void setupSpinner(int selection) {
        //sets up the spinner
        Spinner spinner = findViewById(R.id.DifficultySelector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.difficultyList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(selection);
    }

    //sets up the other boxes with information from the task we're editing
    public void setupRest() {
        EditText titleBox = findViewById(R.id.TaskName);
        titleBox.setText(editTitle);
        EditText dueBox = findViewById(R.id.DueDate);
        dueBox.setText(editDueDate);
        EditText tagsBox = findViewById(R.id.Tags);
        tagsBox.setText(editTags);
        EditText timeToCompleteBox = findViewById(R.id.TimeToComplete);
        timeToCompleteBox.setText("" + editTimeToComplete);
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
            navigationView.setCheckedItem(R.id.nav_new_task);
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

    //this is what happens when someone clicks the save button (does a bunch of checks to make sure the task is valid
    public void saveTask(View view) {
        //Title
        TextView titleView = findViewById(R.id.TaskName);
        String title = titleView.getText().toString();
        if (TextUtils.isEmpty(title)) {
            Snackbar.make(drawerLayout, "Title empty", Snackbar.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), "Title empty", Toast.LENGTH_LONG).show();
            return;
        }

        //Date work and checking for reasonable dates
        EditText dueDate = findViewById(R.id.DueDate);
        String stringDateFormat = dueDate.getText().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/ddd/yyyy");
        formatter.setLenient(false);
        Date convertedDate = null;
        try {
            convertedDate = formatter.parse(stringDateFormat);
        } catch (Exception e) {
            Snackbar.make(drawerLayout, "Insert a valid date", Snackbar.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), "Insert a valid date", Toast.LENGTH_LONG).show();
            return;
        }

        //Tags
        TextView tagsView = findViewById(R.id.Tags);
        String tags = tagsView.getText().toString();
        String[] tagArray = tags.split(",");
        ArrayList<String> tagList = new ArrayList<>();
        if (!tags.equals("")) {
            for (int i = 0; i < tagArray.length; i++) {
                if (!tagArray[i].isEmpty()) {
                    if (tagArray[i].charAt(0) == ' ') {
                        tagArray[i] = tagArray[i].substring(1);
                    }
                    if (!tagArray[i].equals("")) {
                        if (tagArray[i].charAt(tagArray[i].length() - 1) == ' ') {
                            tagArray[i] = tagArray[i].substring(0, tagArray[i].length() - 1);
                        }
                    } else {  //used for people adding extra commas
                        tagArray[i] = "∟";
                    }
                }
            }
            for (String x : tagArray) {
                if (!x.equals("∟"))
                    tagList.add(x);
            }
            //Collections.addAll(tagList, tagArray);
        }

        //Difficulty
        //done in onItemSelected


        //Time To Complete
        TextView TTCView = findViewById(R.id.TimeToComplete);
        String timeString = TTCView.getText().toString();

        //if non-numerical exit and return toast failure message
        if (TextUtils.isEmpty(timeString)) {
            Snackbar.make(drawerLayout, "Time to Complete is empty", Snackbar.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), "Time to Complete is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        //checks to see if time is in hours (allows things like 2.5 hours)
        String regExp = "[\\x00-\\x20]*[+-]?(((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*";
        boolean matches = timeString.matches(regExp);
        if (!matches) {
            Snackbar.make(drawerLayout, "Time to Complete not a number or negative", Snackbar.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), "Time to Complete not a number or negative", Toast.LENGTH_LONG).show();
            return;
        }
        double TTC = 0.0;
        try {
            TTC = Double.parseDouble(timeString);
            if (TTC > 8) {
                Snackbar.make(drawerLayout, "Task is too long, either try separating this task into smaller chunks or using a tag", Snackbar.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), "Try separating this task into separate tasks", Toast.LENGTH_LONG).show();
                return;
            }
            //Time can't be equal to 0
            if (TTC == 0.0) {
                Snackbar.make(drawerLayout, "Time to complete cannot be 0", Snackbar.LENGTH_LONG).show();
                return;
            }
        } catch (Exception e) {
            Snackbar.make(drawerLayout, "Insert a valid Time To Complete", Snackbar.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), "Insert a valid Time To Complete", Toast.LENGTH_LONG).show();
        }

        //Repeating task
        int repeat;
        try {
            TextView repeatBox = findViewById(R.id.repeat);
            if (TextUtils.isEmpty(repeatBox.getText().toString()))
                repeat = 0;
            else
                repeat = Integer.parseInt(repeatBox.getText().toString());

            if (repeat < 0) {
                Snackbar.make(drawerLayout, "Repeating tasks should be a positive number", Snackbar.LENGTH_LONG).show();
                return;
            }
        } catch (Exception e) {
            Snackbar.make(drawerLayout, "Repeating task couldn't be parsed", Snackbar.LENGTH_LONG).show();
            return;
        }


        //Experience calculation
        Task newTask = new Task(title, convertedDate, tagList, difficulty, TTC, repeat);
        double experience = 100 * (newTask.getIntDifficulty(getApplicationContext())) * (Math.pow(TTC, .5) + (.2 * TTC));
        newTask.setExperience((int) experience);

        AllTasks.addTask(newTask);

        //clears out the the task from list of tasks so it can be replaced
        if (editPreviousTask != null) {
            AllTasks.removeTask(editPreviousTask);
        }
        //used for editing tasks
        clearVariables();
        //Says task made successfully and launches into Calendar
        //Snackbar.make(drawerLayout, "Task is worth " + (int) experience + " XP", Snackbar.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "Task is worth " + (int) experience + " XP", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(NewTask.this, Calendar.class);
        startActivity(intent);
    }

    //sets difficulty
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        difficulty = adapterView.getItemAtPosition(i).toString();
    }

    //needs to be overridden but not changed
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    //used for navigation menu so when something is clicked I can do stuff with it
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_calendar: {
                clearVariables();
                Intent intent = new Intent(NewTask.this, Calendar.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_new_task: {
                break;
            }
            case R.id.nav_task_list: {
                clearVariables();
                Intent intent = new Intent(NewTask.this, TaskList.class);
                startActivity(intent);
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

    public static void setEditDifficulty(String meditDifficulty) {
        editDifficulty = meditDifficulty;
    }

    public static void setEditAppBarTitle(String meditAppBarTitle) {
        editAppBarTitle = meditAppBarTitle;
    }

    public static void setEditTitle(String meditTitle) {
        editTitle = meditTitle;
    }

    public static void setEditDueDate(String meditDueDate) {
        editDueDate = meditDueDate;
    }

    public static void setEditTags(String meditTags) {
        editTags = meditTags;
    }

    public static void setEditTimeToComplete(double meditTimeToComplete) {
        editTimeToComplete = meditTimeToComplete;
    }

    public static void setEditPreviousTask(Task meditPreviousTask) {
        editPreviousTask = meditPreviousTask;
    }

    //clears variables that are used for editing tasks
    public void clearVariables() {
        editTitle = "";
        editDueDate = "";
        editAppBarTitle = "New Task";
        editDifficulty = "";
        editTags = "";
        editTimeToComplete = 0.0;
        editPreviousTask = null;
    }
}
