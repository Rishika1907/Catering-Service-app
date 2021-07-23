package com.example.madproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class userorderadapter extends RecyclerView.Adapter<userorderadapter.ViewHolder>{

    ArrayList<userorderhelper> featuredLocation;

    public userorderadapter(ArrayList<userorderhelper> featuredLocation) {
        this.featuredLocation = featuredLocation;
    }

    //creates a new view holder when there are no existing view holders
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userorderadapter,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //Main function that binds design and code
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        userorderhelper adapter = featuredLocation.get(position);

        holder.event.setText(adapter.getEventname());
//        holder.email.setText(adapter.getEmail());
        holder.location.setText(adapter.getLocation());
        holder.count.setText(adapter.getCount());
        holder.date.setText(adapter.getDate());
        holder.status.setText(adapter.getStatus());
        holder.service.setText(adapter.getServicename());
    }

    @Override
    public int getItemCount() {
        return featuredLocation.size();
    }

    //each element to be displayed
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView event,location,date,count,status,service;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            event = itemView.findViewById(R.id.value_event_name);
//            email = itemView.findViewById(R.id.value_email_name);
            date = itemView.findViewById(R.id.value_date_name);
            location = itemView.findViewById(R.id.value_location_name);
            count = itemView.findViewById(R.id.value_number_name);
            status = itemView.findViewById(R.id.value_status_name);
            service = itemView.findViewById(R.id.value_service_name);
        }
    }
}
