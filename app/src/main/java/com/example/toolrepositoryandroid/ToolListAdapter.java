package com.example.toolrepositoryandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ToolListAdapter extends ArrayAdapter<Tool> {
    private static final String TA = "ToolListAdapter";
    private Context mContext;
    int mResource;

    public ToolListAdapter(@NonNull Context context, int resource, @NonNull List<Tool> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String toolName = getItem(position).getToolName();
        String toolType = getItem(position).getToolType();
        String toolLocation = getItem(position).getToolLocation();

        Tool tool = new Tool();
        tool.setToolLocation(toolLocation);
        tool.setToolName(toolName);
        tool.setToolType(toolType);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView toolNameTextView = (TextView) convertView.findViewById(R.id.toolNameTextView);
        TextView toolTypeTextView = (TextView) convertView.findViewById(R.id.toolTypeTextView);
        TextView toolLocationTextView = (TextView) convertView.findViewById(R.id.toolLocationTextView);

        toolNameTextView.setText(toolName);
        toolTypeTextView.setText(toolType);
        toolLocationTextView.setText(toolLocation);

        return convertView;


    }

}
