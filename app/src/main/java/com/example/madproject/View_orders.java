package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class View_orders extends AppCompatActivity {
    RecyclerView recyclerView;
    vieworderadapter viewdata;

    int a=0;
    FirebaseDatabase dbRef;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        recyclerView = (RecyclerView)findViewById(R.id.order_recyclerview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        ArrayList<vieworderhelper> data = new ArrayList<>();

        Intent intent = getIntent();
        String keys = intent.getExtras().getString("Key");
        String service = intent.getExtras().getString("Service");

        ref = dbRef.getInstance().getReference("Orders");

        Query checkUser = ref.orderByChild("ServiceName").equalTo(service);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String key;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        key = ds.getKey();
                        String status = snapshot.child(key).child("Status").getValue(String.class);
                        if (status.equals("Accepted")) {
                            a+=1;
                            String email = snapshot.child(key).child("CustomerEmail").getValue(String.class);
                            String event = snapshot.child(key).child("EventName").getValue(String.class);
                            String date = snapshot.child(key).child("Date_of_the_event").getValue(String.class);
                            String location = snapshot.child(key).child("LocationOfEvent").getValue(String.class);
                            String count = snapshot.child(key).child("Expected_No_OfPeople").getValue(String.class);

                            data.add(new vieworderhelper("" + event, "" + email, "" + count, "" + date, "" + location));
                        }
                        viewdata = new vieworderadapter(data);
                        recyclerView.setAdapter(viewdata);
                    }if(a == 0){
                        alert();
                    }
                }else{
                    alert();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void alert() {
        AlertDialog alert = new AlertDialog.Builder(View_orders.this).setTitle("Message").setMessage("No orders are accepted").create();
        alert.show();
    }
}
