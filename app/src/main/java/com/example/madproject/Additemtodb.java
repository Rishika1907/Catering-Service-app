package com.example.madproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Additemtodb extends AppCompatActivity {

    Button add;
    EditText price,item;

    private FirebaseAuth myAuth;
    private ProgressDialog loading;
    private FirebaseDatabase rootNode;
    private DatabaseReference ref,dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additemtodb);

        price = (EditText)findViewById(R.id.add_price);
        item = (EditText)findViewById(R.id.add_item);

        add = (Button)findViewById(R.id.add_button);

        myAuth = FirebaseAuth.getInstance();
        loading = new ProgressDialog(Additemtodb.this);
        rootNode = FirebaseDatabase.getInstance();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String key = intent.getExtras().getString("Key");
                String occasi = intent.getExtras().getString("Occasion").toLowerCase().trim();

                String ite = item.getText().toString().toLowerCase().trim();
                String pric = price.getText().toString();
                Boolean err = false;

                if (ite.isEmpty()) {
                    showError(item, "Enter the Item");
                    err = true;
                }
                if (pric.isEmpty()) {
                    showError(price, "Enter the Price");
                    err = true;
                }
                if (err == false) {

                    ref = rootNode.getReference("caterer");

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Boolean a = snapshot.child(key).child(occasi).hasChildren();
                            //Toast.makeText(getApplicationContext()," "+a,Toast.LENGTH_SHORT).show();
                            if (a == false) {
                                ref.child(key).child(occasi).push();

                                HashMap<String, String> item = new HashMap<>();
                                item.put(ite, pric);

                                ref.child(key).child(occasi).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Additemtodb.this, "Item added Successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(Additemtodb.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Additemtodb.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                snapshot.getRef().child(key).child(occasi).child(ite).setValue(pric);
                                Toast.makeText(Additemtodb.this, "Item added Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
    private void showError(EditText input, String s) {
        Drawable error = getResources().getDrawable(R.drawable.ic_baseline_error_24);
        error.setBounds(0,0,error.getIntrinsicWidth(),error.getIntrinsicHeight());
        input.setError(s,error);
        input.requestFocus();
    }
}
