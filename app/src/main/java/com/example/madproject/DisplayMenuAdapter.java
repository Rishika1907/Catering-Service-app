package com.example.madproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DisplayMenuAdapter extends RecyclerView.Adapter<DisplayMenuAdapter.MyViewHolder> {
    Context ctx;
    List<String> items;
    List<String> cost;
    //List<String>key;
    //String occassion;
    //private featuredAdapter.OnRecyclerViewClickListener listener;



    public DisplayMenuAdapter(Context ctx,List<String> items,List<String>cost) {
        this.ctx = ctx;
        this.items = items;
        this.cost=cost;
        //this.occassion =occassion;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.item_details, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String itemname = items.get(position);
        String price = cost.get(position);

        holder.item.setText(itemname);
        holder.prices.setText(price);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item;
        TextView prices;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item= itemView.findViewById(R.id.itemName);
            prices = itemView.findViewById(R.id.cost);

        }
    }

}