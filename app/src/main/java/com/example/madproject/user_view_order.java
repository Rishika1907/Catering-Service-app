package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_view_order extends AppCompatActivity {

    RecyclerView orderlist;
    userorderadapter viewdata;

    FirebaseDatabase dbRef;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_order);

        orderlist = (RecyclerView)findViewById(R.id.user_order_recyclerview);

        orderlist.setHasFixedSize(true);
        orderlist.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        ArrayList<userorderhelper> data = new ArrayList<>();

        Intent intent = getIntent();
        String keys = intent.getExtras().getString("Key");
        String email = intent.getExtras().getString("Email");

        //Toast.makeText(user_view_order.this,""+email,Toast.LENGTH_SHORT).show();
        ref = dbRef.getInstance().getReference("Orders");

        Query checkUser = ref.orderByChild("CustomerEmail").equalTo(email);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String key;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        key = ds.getKey();

                        String status = snapshot.child(key).child("Status").getValue(String.class);
//                        String email = snapshot.child(key).child("CustomerEmail").getValue(String.class);
                        String event = snapshot.child(key).child("EventName").getValue(String.class);
                        String date = snapshot.child(key).child("Date_of_the_event").getValue(String.class);
                        String location = snapshot.child(key).child("LocationOfEvent").getValue(String.class);
                        String count = snapshot.child(key).child("Expected_No_OfPeople").getValue(String.class);
                        String servicename = snapshot.child(key).child("ServiceName").getValue(String.class);

                        data.add(new userorderhelper("" + event,  "" + count, "" + date, "" + location, "" + status,""+servicename));
                    }
                    viewdata = new userorderadapter(data);
                    orderlist.setAdapter(viewdata);
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
        AlertDialog alert = new AlertDialog.Builder(user_view_order.this).setTitle("Message").setMessage("No orders have been done yet").create();
        alert.show();
    }
}
