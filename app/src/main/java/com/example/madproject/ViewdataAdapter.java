package com.example.madproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewdataAdapter extends RecyclerView.Adapter<ViewdataAdapter.ViewHolder> {
    ArrayList<viewhelperadapter> featuredLocation;

    public ViewdataAdapter(ArrayList<viewhelperadapter> featuredLocation) {
        this.featuredLocation = featuredLocation;
    }

    //creates a new view holder when there are no existing view holders
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_details_design,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //Main function that binds design and code
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        viewhelperadapter adapter = featuredLocation.get(position);

        holder.info.setText(adapter.getItem());
        holder.value.setText(adapter.getValue());

    }

    @Override
    public int getItemCount() {
        return featuredLocation.size();
    }

    //each element to be displayed
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView info,value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.view_details);
            value = itemView.findViewById(R.id.value_details);
        }
    }
}

