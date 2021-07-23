package com.example.madproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class OrderSummary extends AppCompatActivity {

    TextView catererName,date,amt,expectedNumber,location,returnText;
    Button button;
    DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        SharedPreferences getShared = getSharedPreferences("demo", Context.MODE_PRIVATE);

        /*username and event from shared preference*/
        String username = getShared.getString("Username", "get");
        String event = getShared.getString("Event","null");

        //caterer Name
        catererName = (TextView) findViewById(R.id.catererName);
        String name = getShared.getString("caterer name", "null");
        catererName.setText(name);

        //Expected number of people
        expectedNumber = (TextView) findViewById(R.id.expect);
        String expected = getShared.getString("expected", "null");
        expectedNumber.setText(expected);

        //Expected total  amount
        amt = (TextView) findViewById(R.id.amt);
        String price = getShared.getString("sum", "null");
        amt.setText(price);

        //intent
        Intent i=getIntent();
        //date
        date = (TextView) findViewById(R.id.date);
        String dateOfEvent = i.getStringExtra("date");
        date.setText(dateOfEvent);

        //Location
        location = (TextView) findViewById(R.id.location);
        String place = i.getStringExtra("location");
        location.setText(place);


        /*clicking on return text it leads to home page i.e., occasion.java*/
        returnText = (TextView) findViewById(R.id.returnText);
        returnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OrderSummary.this, occasion.class);
                i.putExtra("Uniqid","From_Order");
                startActivity(i);

            }
        });


        /* on clicking the button the details of the order are stored onto the database*/
        button =(Button)findViewById(R.id.order);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setEnabled(false);

                dbRef= FirebaseDatabase.getInstance().getReference("Orders");
                String key = dbRef.push().getKey();

                HashMap<String , String> order  = new HashMap<>();
                order.put("Key",key);
                order.put("Expected_No_OfPeople",expected);
                order.put("ServiceName",name);
                order.put("CustomerEmail",username);
                order.put("EventName",event);
                order.put("LocationOfEvent",place);
                order.put("Date_of_the_event",dateOfEvent);
                order.put("Status","Pending");

                dbRef.child(key).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Ordered Successfully", Toast.LENGTH_SHORT).show();
                            //openMain();
                        }else{
                            Toast.makeText(getApplicationContext(), "Ordering unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Ordering unsuccessful",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



    }
}
