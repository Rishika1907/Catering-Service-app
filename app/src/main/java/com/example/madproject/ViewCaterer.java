package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import java.util.List;

public class ViewCaterer extends AppCompatActivity{

    List<String> fetchData;
    List<String> phone;
    List<String>keys;

    RecyclerView recyclerView;
    catererAdapter catererAdapter;
    DatabaseReference databaseReference;
    ImageView imageView;

    TextView eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Occasion from previous activity
        Intent intent = getIntent();
        String occasion = intent.getStringExtra("event").toLowerCase();




        fetchData = new ArrayList<String>();
        phone = new ArrayList<String>();
        keys = new ArrayList<String>();



        databaseReference = FirebaseDatabase.getInstance().getReference("caterer");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean present=false;
                int count=0;
                String k;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    count++;
                    String key = dataSnapshot.getKey();
                    String x= key;

                    //Long count = snapshot.child(key).getChildrenCount();

                    if (snapshot.child(key).child(occasion).exists()) {

                        present = true;
                        String name = snapshot.child(key).child("ServiceName").getValue(String.class);
                        String phoneNo = snapshot.child(key).child("Address").getValue(String.class);
                        //Iterable<DataSnapshot> x = snapshot.child(key).child(occasion).getChildren();


                        //Toast.makeText(getApplicationContext(), "" + name, Toast.LENGTH_LONG).show();
                        keys.add(key);

                        fetchData.add(name);
                        phone.add(phoneNo);
                    }
                }
                if(present) {
                    setContentView(R.layout.activity_view_caterer);

                    /*back button*/
                    imageView = (ImageView) findViewById(R.id.imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });

                    //recycler View to display the details if the catering service is available
                    recyclerView = findViewById(R.id.catererlist);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ViewCaterer.this));

                    /* occasion name from previous activity */
                    catererAdapter = new catererAdapter(ViewCaterer.this, keys,fetchData,phone,occasion);
                    recyclerView.setAdapter(catererAdapter);
                    eventName = findViewById(R.id.eventName);
                    eventName.setText(occasion);
                    catererAdapter.notifyDataSetChanged();
                }else{
                    Intent i = new Intent(ViewCaterer.this,OcassionUnavailable.class);
                    i.putExtra("not","Currently there are no services available for this event.");
                    startActivity(i);
                    //Toast.makeText(getApplicationContext(), "CURRENTLY THERE ARE NO CATERING SERVICES AVAILABLE FOR THIS EVENT", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



}