package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Viewdatafromdb extends AppCompatActivity {
    RecyclerView recyclerview;
    ViewdataAdapter viewdata;
    TextView view;

    FirebaseDatabase dbRef;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdatafromdb);

        recyclerview = (RecyclerView)findViewById(R.id.recycler_view_details);
        view = (TextView)findViewById(R.id.view_heading);

        recyclerview.setHasFixedSize(true);

        recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        ArrayList<viewhelperadapter> data = new ArrayList<>();

        Intent intent = getIntent();
        String key = intent.getExtras().getString("Key");
        String occasi = intent.getExtras().getString("Occasion");

        view.setText(occasi);

        String occasion = occasi.toLowerCase().trim();

        ref = dbRef.getInstance().getReference("caterer");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Check whether entered occasion is there in database if yes retrieve ut
                if(snapshot.child(key).child(occasion).hasChildren()){
                    for(DataSnapshot ds : snapshot.child(key).child(occasion).getChildren()){
                        String keys = ds.getKey();
                        String value = snapshot.child(key).child(occasion).child(keys).getValue(String.class);

                        data.add(new viewhelperadapter(""+keys,"₹ "+value));
                    }
                    viewdata = new ViewdataAdapter(data);

                    recyclerview.setAdapter(viewdata);
                }
                //Otherwise display appropriate message
                else{
                    alert();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void alert() {
        AlertDialog alert = new AlertDialog.Builder(Viewdatafromdb.this).setTitle("OOPS!").setMessage("Currently there are no items added to this event").create();
        alert.show();
    }
}