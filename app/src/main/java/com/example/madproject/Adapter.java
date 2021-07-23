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

import java.io.Serializable;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Serializable {

    private Context context;
    private List<String> titles;
    private List<Integer> images;
    public Adapter(Context context, List<String> titles, List<Integer> images) {
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
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,ViewCaterer.class);
                intent.putExtra("event",t);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
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

        }

    }
}
