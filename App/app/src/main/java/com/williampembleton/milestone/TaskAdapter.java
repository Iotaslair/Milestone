package com.williampembleton.milestone;

import android.content.Context;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class TaskAdapter extends ArrayAdapter<String>
{
    public TaskAdapter(Context context, String[] tasks) {
        super(context,R.layout.tasklistrow, tasks);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.tasklistrow,parent,false);


        TextView title = view.findViewById(R.id.Title);
        TextView date = view.findViewById(R.id.Date);
        TextView ttc = view.findViewById(R.id.TimeToComplete);
        TextView difficulty = view.findViewById(R.id.Difficulty);
        TextView exp = view.findViewById(R.id.Experience);
        TextView tag1 = view.findViewById(R.id.Tag1);
        TextView tag2 = view.findViewById(R.id.Tag2);
        TextView tag3 = view.findViewById(R.id.Tag3);
        TextView tag4 = view.findViewById(R.id.Tag4);

        String taskStr = getItem(position);

        String[] task = taskStr.split("∟");

        title.setText(task[0]);
        date.setText("Due: " + task[1]);
        ttc.setText(task[2] + " hours");
        difficulty.setText(task[3]);
        exp.setText(task[4] + " xp");

        try {tag1.setText(task[5]); }catch (Exception e) {tag1.setText("Tags go here");}
        try {tag2.setText(task[6]); }catch (Exception e) {tag2.setText("");}
        try {tag3.setText(task[7]); }catch (Exception e) {tag3.setText("");}
        try {tag4.setText(task[8]); }catch (Exception e) {tag4.setText("");}





        return view;
    }
}
