package com.williampembleton.milestone;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Date;

public class NewTask extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String difficulty = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        EditText dueDate = findViewById(R.id.DueDate);
        Calendar currentDate = GregorianCalendar.getInstance();

        //sets the current date into the due box
        String dueDateString = "" + (1 + currentDate.get(Calendar.MONTH)) + "/";
        dueDateString += (1 + currentDate.get(Calendar.DAY_OF_MONTH)) + "/";
        dueDateString += currentDate.get(Calendar.YEAR);
        dueDate.setText(dueDateString);

        //sets up the spinner
        Spinner spinner = findViewById(R.id.DifficultySelector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.difficultyList,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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
                convertedDate = (Date) formatter.parse(stringDateFormat);
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
            if(!android.text.TextUtils.isDigitsOnly(timeString))
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

        AllTasks.addTask(newTask);

        Toast.makeText(getApplicationContext(), "Task is worth " + (int) experience + " XP", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(NewTask.this, TaskList.class);
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
