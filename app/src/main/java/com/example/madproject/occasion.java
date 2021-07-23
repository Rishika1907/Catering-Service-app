package com.example.madproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class occasion extends AppCompatActivity  {
    TextView logout,order;
    RecyclerView dataList;
    ArrayList<String> titles;
    ArrayList<Integer> images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i=getIntent();
        String id=i.getStringExtra("Uniqid");
        if(id.equals("From_Login")) {
            /*getting email from login page*/
            String m = i.getExtras().getString("Email");
            SharedPreferences shrd = getSharedPreferences("demo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = shrd.edit();
            editor.putString("Username", m);
            editor.apply();
        }

        order = (TextView)findViewById(R.id.order_history);

        //RecyclerView
        dataList=findViewById(R.id.dataList);

        //Arraylist for titles and images

        titles = new ArrayList<>();
        images = new ArrayList<>();

        //adding images and titles to the arraylist
        titles.add("Birthday Parties");
        titles.add("Wedding");
        titles.add("Corporate events");
        titles.add("Puja");
        titles.add("House Warming");

        images.add(R.drawable.birthday);
        images.add(R.drawable.wedding);
        images.add(R.drawable.office);
        images.add(R.drawable.puja);
        images.add(R.drawable.house);


        //adapter
        Adapter adapter = new Adapter(occasion.this,titles,images);
        //dataList.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);

        /*log out*/
        logout=(TextView)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(occasion.this,Login_Page.class);
                startActivity(i);
                finish();
            }
        });
        /*TO VIEW ORDER HISTORY*/
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String key = intent.getExtras().getString("Key");
                String email = intent.getExtras().getString("Email");

                intent = new Intent(occasion.this,user_view_order.class);
                intent.putExtra("Key",key);
                intent.putExtra("Email",email);
                startActivity(intent);
            }
        });

    }


}
