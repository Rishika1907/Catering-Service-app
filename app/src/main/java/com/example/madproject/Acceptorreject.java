package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Acceptorreject extends AppCompatActivity {

    TextView eventname,location,date,useremail,count;
    Button accept,reject;

    FirebaseDatabase ref;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptorreject);

        eventname = (TextView)findViewById(R.id.pname);
        location = (TextView)findViewById(R.id.elocation);
        date = (TextView)findViewById(R.id.edate);
        useremail = (TextView)findViewById(R.id.pemail);
        count = (TextView)findViewById(R.id.no_orders);

        accept = (Button) findViewById(R.id.accept);
        reject = (Button)findViewById(R.id.reject);

        Intent intent =getIntent();
        String key = intent.getExtras().getString("Key");

        dbRef = ref.getInstance().getReference("Orders/"+key);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String emai = snapshot.child("CustomerEmail").getValue(String.class);
                String locati = snapshot.child("LocationOfEvent").getValue(String.class);
                String event = snapshot.child("EventName").getValue(String.class);
                String dat = snapshot.child("Date_of_the_event").getValue(String.class);
                String coun = snapshot.child("Expected_No_OfPeople").getValue(String.class);

                useremail.setText(emai);
                eventname.setText(event);
                location.setText(locati);
                date.setText(dat);
                count.setText(coun);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept.setEnabled(false);
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("Status").setValue("Rejected");
                        Toast.makeText(Acceptorreject.this,"Order Rejected",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                accept.setEnabled(false);
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("Status").setValue("Accepted");
                        Toast.makeText(Acceptorreject.this,"Order Accepted",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}
