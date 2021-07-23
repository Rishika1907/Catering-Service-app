package com.example.madproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.ViewHolder> {
    ArrayList<Pendingadapterhelper> featuredLocation;
    private Context context;
  /*public void setOnItemClickListener(OnItemClickListener listener){

       mListener =listener;
   }*/

    public PendingAdapter(Context context,ArrayList<Pendingadapterhelper> featuredLocation) {
        this.context=context;
        this.featuredLocation = featuredLocation;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pendinfadapterdesign,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //Main function that binds design and code
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Pendingadapterhelper adapter = featuredLocation.get(position);

        holder.image.setText(adapter.getLocation());
        holder.title.setText(adapter.getEmail());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,Acceptorreject.class);
                intent.putExtra("Key",adapter.getKey());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return featuredLocation.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.phone);
        }
    }
}


