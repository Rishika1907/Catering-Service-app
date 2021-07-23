package com.example.madproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class vieworderadapter extends RecyclerView.Adapter<vieworderadapter.ViewHolder>{

    ArrayList<vieworderhelper> featuredLocation;

    public vieworderadapter(ArrayList<vieworderhelper> featuredLocation) {
        this.featuredLocation = featuredLocation;
    }

    //creates a new view holder when there are no existing view holders
    @NonNull
    @Override
    public vieworderadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderadapter,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //Main function that binds design and code
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        vieworderhelper adapter = featuredLocation.get(position);

        holder.event.setText(adapter.getEventname());
        holder.email.setText(adapter.getEmail());
        holder.location.setText(adapter.getLocation());
        holder.count.setText(adapter.getCount());
        holder.date.setText(adapter.getDate());

    }

    @Override
    public int getItemCount() {
        return featuredLocation.size();
    }

    //each element to be displayed
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView event,email,location,date,count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            event = itemView.findViewById(R.id.value_event_name);
            email = itemView.findViewById(R.id.value_email_name);
            date = itemView.findViewById(R.id.value_date_name);
            location = itemView.findViewById(R.id.value_location_name);
            count = itemView.findViewById(R.id.value_number_name);
        }
    }
}
