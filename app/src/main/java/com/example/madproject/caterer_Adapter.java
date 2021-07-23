package com.example.madproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class caterer_Adapter extends RecyclerView.Adapter<caterer_Adapter.ViewHolder>{

    private Context context;
    private List<String> titles;
    private List<Integer> images;

    private caterer_Adapter.OnRecyclerViewClickListener listener;
    private ArrayList<caterer_Adapter> featuredLocation;

    public interface OnRecyclerViewClickListener{
        void onItemClick(int position);
    }

    public void OnRecyclerViewClickListener(caterer_Adapter.OnRecyclerViewClickListener listener){
        this.listener=listener;
    }

    public caterer_Adapter(ArrayList<caterer_Adapter> featuredLocation) {
        this.featuredLocation = featuredLocation;

    }

    public caterer_Adapter(Context context, List<String> titles, List<Integer> images) {
        this.context = context;
        this.titles = titles;
        this.images = images;
    }
    //where to get the single card as viewholder object
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customgrid,parent,false);

        return new ViewHolder(view);

    }
    //what will happpen after creation of viewholder object
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String t = titles.get(position);
        holder.title.setText(titles.get(position));
        holder.image.setImageResource(images.get(position));
    }



    //how many items
    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);

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
