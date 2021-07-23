package com.example.madproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayMenu extends AppCompatActivity {
    ImageView imageView;
    DatabaseReference databaseReference;
    EditText expected;
    Button order;
    DisplayMenuAdapter displayMenuAdapter;
    RecyclerView menu;
    TextView bill;
    int sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_menu);

        bill = (TextView) findViewById(R.id.total);
        expected = (EditText) findViewById(R.id.expected);

        /*back button*/
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /* get the caterer name,occasion,key from the previous activity*/
        Intent intent = getIntent();
        String name = intent.getStringExtra("service name");
        String event = intent.getStringExtra("occasion");
        String key = intent.getStringExtra("key");

        /*To store the cost of all the items*/
        ArrayList<String> costList = new ArrayList <String>();
        /*To store the items*/
        ArrayList<String> items = new ArrayList <String>();

        /*Setting the recycler view to display the menu*/
        menu = findViewById(R.id.menu);
        menu.setLayoutManager(new LinearLayoutManager(this));

        /*adapter setting*/
        displayMenuAdapter = new DisplayMenuAdapter(this, items, costList);
        menu.setAdapter(displayMenuAdapter);

        /* getting the item name and cost from the database*/
        databaseReference = FirebaseDatabase.getInstance().getReference("caterer");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.child(key).child(event).getChildren()) {

                    String keys = dataSnapshot.getKey();
                    String cost = dataSnapshot.getValue(String.class);

                    /*setting the cost per plate*/
                    sum = Integer.parseInt(cost) + sum;
                    bill.setText(String.valueOf(sum));

                    /*Adding items and cost to the arraylist*/
                    items.add(keys);
                    costList.add(cost);
                }

                /*Storing sum from shared preference*/
                SharedPreferences shrd = getSharedPreferences("demo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("sum", String.valueOf(sum));
                editor.apply();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        /*getting the sum stored in shared preference*/
        SharedPreferences getShared=getSharedPreferences("demo", Context.MODE_PRIVATE);
        String plus=getShared.getString("sum","1");
        int s = Integer.parseInt(plus);

        /* on clicking order button lead to a page to enter date and location*/
        order = (Button) findViewById(R.id.button);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*expected total amount*/
                int tot = s * Integer.parseInt(String.valueOf(expected.getText()));

                /*
                using shared preference to store the
                event
                expected number of people,
                caterer name
                Total
                */
                SharedPreferences shrd = getSharedPreferences("demo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("Event",event);
                editor.putString("expected",String.valueOf(expected.getText()));
                editor.putString("Total", String.valueOf(tot));
                editor.putString("caterer name", name);
                editor.apply();

                /*Intent to start the activity*/
                Intent i = new Intent(DisplayMenu.this, NewActivity.class);
                startActivity(i);
            }
        });


    }


}