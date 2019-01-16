package com.williampembleton.milestone;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Date;

public class NewTask extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener{

    String difficulty = "";
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


        EditText dueDate = findViewById(R.id.DueDate);
        java.util.Calendar currentDate = GregorianCalendar.getInstance();

        //sets the current date into the due box
        String dueDateString = "" + (1 + currentDate.get(java.util.Calendar.MONTH)) + "/";
        dueDateString += (1 + currentDate.get(java.util.Calendar.DAY_OF_MONTH)) + "/";
        dueDateString += currentDate.get(java.util.Calendar.YEAR);
        dueDate.setText(dueDateString);

        //sets up the spinner
        Spinner spinner = findViewById(R.id.DifficultySelector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.difficultyList,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_new_task);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_calendar: {
                Intent intent = new Intent(NewTask.this, Calendar.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_new_task: {
                break;
            }
            case R.id.nav_task_list: {
                Intent intent = new Intent(NewTask.this, TaskList.class);
                startActivity(intent);
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

    public void saveTask(View view)
    {
        //Title
            TextView titleView = findViewById(R.id.TaskName);
            String title =  titleView.getText().toString();
            if(TextUtils.isEmpty(title))
            {
                Toast.makeText(getApplicationContext(), "Title empty", Toast.LENGTH_SHORT).show();
                return;
            }

        //Date work and checking for reasonable dates
            EditText dueDate = findViewById(R.id.DueDate);
            String stringDateFormat = dueDate.getText().toString();
            SimpleDateFormat formatter = null;
            Date convertedDate = null;
            try {
                formatter = new SimpleDateFormat("MM/ddd/yyyy");
                formatter.setLenient(false);
                convertedDate = formatter.parse(stringDateFormat);
            } catch (Exception e) {
                Context context = getApplicationContext();
                CharSequence text = "Insert a valid date";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return;
            }

        //Tags

            TextView tagsView = findViewById(R.id.Tags);
            String tags =  tagsView.getText().toString();
            String[] tagArray = tags.split(",");

            for(int i = 0; i < tagArray.length; i++)
            {
                if(!tagArray[i].isEmpty()) {
                    if (tagArray[i].charAt(0) == ' ') {
                        tagArray[i] = tagArray[i].substring(1, tagArray[i].length());
                    }
                    if (tagArray[i].charAt(tagArray[i].length() - 1) == ' ') {
                        tagArray[i] = tagArray[i].substring(0, tagArray[i].length() - 1);
                    }
                }
            }
            ArrayList<String> tagList = new ArrayList<>();
            Collections.addAll(tagList,tagArray);


        //Difficulty
            //done in onItemSelected



        //Time To Complete

            TextView TTCView = findViewById(R.id.TimeToComplete);
            String timeString = TTCView.getText().toString();
            //if non-numerical exit and return toast failure message
            if(TextUtils.isEmpty(timeString))
            {
                Toast.makeText(getApplicationContext(), "Time to Complete is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            //checks to see if time is in hours (includes things like 2.5 hours
            String regExp = "[\\x00-\\x20]*[+-]?(((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*";
            boolean matches = timeString.matches(regExp);
            if(!matches)
            {
                Toast.makeText(getApplicationContext(), "Time to Complete not a number or negative", Toast.LENGTH_SHORT).show();
                return;
            }
            double TTC = 0.0;
            try {
                TTC = Double.parseDouble(timeString);
                if(TTC > 3) {
                    Toast.makeText(getApplicationContext(), "Try separating this task into separate tasks", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(), "Insert a valid Time To Complete", Toast.LENGTH_SHORT).show();
            }


        Task newTask = new Task(title,convertedDate,tagList,difficulty,TTC);

        //Experience calculation

            double experience = 100 * (newTask.getIntDifficulty( getApplicationContext()) ) * (Math.pow(TTC,.5) + (.2*TTC) );

            newTask.setExperience( (int) experience);

        AllTasks.addTask(newTask,getSharedPreferences("shared preferences", MODE_PRIVATE));



        Toast.makeText(getApplicationContext(), "Task is worth " + (int) experience + " XP", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(NewTask.this, Calendar.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        difficulty = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
