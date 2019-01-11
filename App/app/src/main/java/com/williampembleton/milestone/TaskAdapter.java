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


        TextView Title = view.findViewById(R.id.Title);
        TextView Tag1 = view.findViewById(R.id.Tag1);
        TextView Tag2 = view.findViewById(R.id.Tag2);
        TextView Tag3 = view.findViewById(R.id.Tag3);
        TextView Tag4 = view.findViewById(R.id.Tag4);
        TextView TTC = view.findViewById(R.id.TimeToComplete);
        TextView Difficulty = view.findViewById(R.id.Difficulty);
        TextView Exp = view.findViewById(R.id.Experience);

        String task = getItem(position);


        return view;
    }
}
