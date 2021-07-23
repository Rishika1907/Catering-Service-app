package com.example.madproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class featuredAdapter extends RecyclerView.Adapter<featuredAdapter.featuredViewHolder> {
    ArrayList<homeadapter> featuredLocation;
    private OnRecyclerViewClickListener listener;

    public interface OnRecyclerViewClickListener{
        void onItemClick(int position);
    }

    public void OnRecyclerViewClickListener(OnRecyclerViewClickListener listener){
        this.listener=listener;
    }

  /*public void setOnItemClickListener(OnItemClickListener listener){

       mListener =listener;
   }*/

    public featuredAdapter(ArrayList<homeadapter> featuredLocation) {
        this.featuredLocation = featuredLocation;

    }

    @NonNull
    @Override
    public featuredViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_design,parent,false);
        featuredViewHolder holder = new featuredViewHolder(view,listener);
        return holder;
    }

    //Main function that binds design and code
    @Override
    public void onBindViewHolder(@NonNull featuredViewHolder holder, int position) {

        homeadapter adapter = featuredLocation.get(position);

        holder.image.setImageResource(adapter.getImage());
        holder.title.setText(adapter.getTitle());

    }

    @Override
    public int getItemCount() {
        return featuredLocation.size();
    }

    public static class featuredViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public featuredViewHolder(@NonNull View itemView,OnRecyclerViewClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.featured_title);
            image = itemView.findViewById(R.id.featured_image);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}

