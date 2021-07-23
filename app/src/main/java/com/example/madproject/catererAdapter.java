package com.example.madproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class catererAdapter extends RecyclerView.Adapter<catererAdapter.MyViewHolder>implements Serializable {
    Context ctx;
    List<String> list;
    List<String> phone;
    String occassion;
    List<String>key;
    //private featuredAdapter.OnRecyclerViewClickListener listener;



    public catererAdapter(Context ctx, List<String>key, List<String> list, List<String>phone, String occassion) {
        this.ctx = ctx;
        this.key=key;
        this.list = list;
        this.phone=phone;
        this.occassion =occassion;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.caterer, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String fetchData = list.get(position);
        String phoneNo = phone.get(position);
        String keys =key.get(position);

        holder.name.setText(fetchData);
        holder.phone.setText(phoneNo);

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ctx,DisplayMenu.class);
                intent.putExtra("service name",fetchData);
                intent.putExtra("occasion",occassion);
                intent.putExtra("key",keys);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView phone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);

        }
    }

}
