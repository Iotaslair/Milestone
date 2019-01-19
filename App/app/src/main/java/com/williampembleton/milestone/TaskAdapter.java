package com.williampembleton.milestone;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{
    private OnItemClickListener mListener;
    private View view = null;
    private Context context = null;



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView date;
        TextView ttc;
        TextView difficulty;
        TextView exp;
        TextView tag1;
        TextView tag2;
        TextView tag3;
        TextView tag4;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.Title);
            date = itemView.findViewById(R.id.Date);
            ttc = itemView.findViewById(R.id.TimeToComplete);
            difficulty = itemView.findViewById(R.id.Difficulty);
            exp = itemView.findViewById(R.id.Experience);
            tag1 = itemView.findViewById(R.id.Tag1);
            tag2 = itemView.findViewById(R.id.Tag2);
            tag3 = itemView.findViewById(R.id.Tag3);
            tag4 = itemView.findViewById(R.id.Tag4);
            checkBox = itemView.findViewById(R.id.checkBox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeAt(getAdapterPosition());
                }
            });
        }

        public void removeAt(int position)
        {
            AllTasks.removeTask(AllTasks.getTask(position));
            notifyItemRemoved(position);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    ArrayList<Task> tasks = null;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklistrow, parent, false);
        ViewHolder vh = new ViewHolder(v, mListener);
        return vh;
    }

    public void setViewAndContext(View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder viewHolder, int i) {
        final Task currentItem = tasks.get(i);

        String taskStr = currentItem.toString();

        String[] task = taskStr.split("∟");

        viewHolder.title.setText(task[0]);

        //used to make a past due system
        Date currentDate = new Date();
        Date convertedDate = currentItem.getDate();
        //adds one to the day so that players aren't punished as soon the day starts
        long plusOneDay = convertedDate.getTime() + (24*60*60*1000);
        Date convertedDatePlusOne=new Date(plusOneDay);
        
        if (currentDate.after(convertedDatePlusOne)) {
            //past due
            viewHolder.date.setTextColor(Color.RED);
        }
        else {
            //on time
            viewHolder.date.setTextColor(Color.BLACK);
        }
        viewHolder.date.setText("Due: " + task[1]);

        viewHolder.ttc.setText(task[2] + " hours");
        viewHolder.difficulty.setText(task[3]);
        viewHolder.exp.setText(task[4] + " xp");

        try {viewHolder.tag1.setText(task[5]); } catch (Exception e) {viewHolder.tag1.setText("Tags go here"); }
        try {viewHolder.tag2.setText(task[6]); } catch (Exception e) {viewHolder.tag2.setText(""); }
        try {viewHolder.tag3.setText(task[7]); } catch (Exception e) {viewHolder.tag3.setText(""); }
        try {viewHolder.tag4.setText(task[8]); } catch (Exception e) {viewHolder.tag4.setText(""); }
    }



    public TaskAdapter(ArrayList<Task> tasks) { this.tasks = tasks;}

    @Override
    public int getItemCount() {return AllTasks.size();}


}
