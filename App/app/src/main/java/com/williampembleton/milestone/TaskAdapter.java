package com.williampembleton.milestone;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private OnItemClickListener mListener;
    private Context context = null;

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

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder viewHolder, int i) {
        Task currentItem = tasks.get(i);

        String taskStr = currentItem.toString();

        String[] task = taskStr.split("∟");

        viewHolder.title.setText(task[0]);

        String dateString = task[1];

        //used to make a past due system
        SimpleDateFormat formatter = null;
        Date currentDate = new Date();
        Date convertedDate = null;
        try {
            formatter = new SimpleDateFormat("MM/dd/yy");
            formatter.setLenient(false);
            convertedDate = formatter.parse(dateString);
        } catch (Exception e) {
            Toast.makeText(context, "Failure formatting the date in TaskAdapter.onBindViewHolder", Toast.LENGTH_LONG).show();
        }
        if (currentDate.after(convertedDate)) {
            viewHolder.date.setTextColor(Color.RED);
            viewHolder.date.setText("Past Due " + task[1]);
        } else {
            viewHolder.date.setText("Due: " + task[1]);
        }


        viewHolder.ttc.setText(task[2] + " hours");
        viewHolder.difficulty.setText(task[3]);
        viewHolder.exp.setText(task[4] + " xp");

        try {viewHolder.tag1.setText(task[5]); } catch (Exception e) {viewHolder.tag1.setText("Tags go here"); }
        try {viewHolder.tag2.setText(task[6]); } catch (Exception e) {viewHolder.tag2.setText(""); }
        try {viewHolder.tag3.setText(task[7]); } catch (Exception e) {viewHolder.tag3.setText(""); }
        try {viewHolder.tag4.setText(task[8]); } catch (Exception e) {viewHolder.tag4.setText(""); }
    }

    public TaskAdapter(ArrayList<Task> taskArrayList) {
        tasks = taskArrayList;
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


}
