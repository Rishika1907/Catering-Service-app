package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile_view extends AppCompatActivity {
    TextView name,address,phone,email,service;

    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        name = (TextView)findViewById(R.id.name);
        phone = (TextView)findViewById(R.id.phone);
        address = (TextView)findViewById(R.id.address);
        email = (TextView)findViewById(R.id.email);
        service = (TextView)findViewById(R.id.servicename);

        Intent intent = getIntent();
        String key = intent.getExtras().getString("Key");

        dbRef = FirebaseDatabase.getInstance().getReference("caterer/"+key);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nam = snapshot.child("Username").getValue(String.class);
                String emai = snapshot.child("Email").getValue(String.class);
                String phoneno = snapshot.child("Phone").getValue(String.class);
                String addre = snapshot.child("Address").getValue(String.class);
                String servicename = snapshot.child("ServiceName").getValue(String.class);

                name.setText(nam);
                phone.setText(phoneno);
                address.setText(addre);
                email.setText(emai);
                service.setText(servicename);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
