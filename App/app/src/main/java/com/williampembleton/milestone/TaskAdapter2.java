package com.williampembleton.milestone;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter2 extends RecyclerView.Adapter<TaskAdapter2.ViewHolder> {

    ArrayList<Task> tasks = null;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklistrow2, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public TaskAdapter2(ArrayList<Task> taskArrayList) {
        tasks = taskArrayList;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Task currentItem = tasks.get(i);

        String taskStr = currentItem.toString();

        String[] task = taskStr.split("∟");

        viewHolder.title.setText(task[0]);
        viewHolder.date.setText("Due: " + task[1]);
        viewHolder.ttc.setText(task[2] + " hours");
        viewHolder.difficulty.setText(task[3]);
        viewHolder.exp.setText(task[4] + " xp");

        try {viewHolder.tag1.setText(task[5]); }catch (Exception e) {viewHolder.tag1.setText("Tags go here");}
        try {viewHolder.tag2.setText(task[6]); }catch (Exception e) {viewHolder.tag2.setText("");}
        try {viewHolder.tag3.setText(task[7]); }catch (Exception e) {viewHolder.tag3.setText("");}
        try {viewHolder.tag4.setText(task[8]); }catch (Exception e) {viewHolder.tag4.setText("");}
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        TextView ttc;
        TextView difficulty;
        TextView exp;
        TextView tag1;
        TextView tag2;
        TextView tag3;
        TextView tag4;

        public ViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.Title);
            date = view.findViewById(R.id.Date);
            ttc = view.findViewById(R.id.TimeToComplete);
            difficulty = view.findViewById(R.id.Difficulty);
            exp = view.findViewById(R.id.Experience);
            tag1 = view.findViewById(R.id.Tag1);
            tag2 = view.findViewById(R.id.Tag2);
            tag3 = view.findViewById(R.id.Tag3);
            tag4 = view.findViewById(R.id.Tag4);
        }
    }

}
