package com.williampembleton.milestone;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.view.View;
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

public class NewTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        EditText dueDate = findViewById(R.id.DueDate);
        Calendar currentDate = GregorianCalendar.getInstance();

        //sets the current date into the due box
        String dueDateString = "" + (1 + currentDate.get(Calendar.MONTH)) + "/";
        dueDateString += (currentDate.get(Calendar.DAY_OF_MONTH)) + "/";
        dueDateString += currentDate.get(Calendar.YEAR);
        dueDate.setText(dueDateString);

        //sets up the spinner
        Spinner spinner = findViewById(R.id.DifficultySelector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.difficultyList,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void saveTask(View view)
    {
        //Title
            TextView titleView = findViewById(R.id.TaskName);
            String title = (String) titleView.getText();

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
            String tags = (String) tagsView.getText();
            String[] tagArray = tags.split(",");
            ArrayList<String> tagList = new ArrayList<>();
            Collections.addAll(tagList,tagArray);


        //Difficulty



        //Task new = new Task(title,convertedDate,tags,difficulty);
    }
}
