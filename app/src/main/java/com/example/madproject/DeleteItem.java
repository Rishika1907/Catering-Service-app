package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteItem extends AppCompatActivity {

    TextView heading;

    EditText item;
    Button btn;

    FirebaseDatabase rootNode;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);

        heading = (TextView)findViewById(R.id.textView);

        item = (EditText)findViewById(R.id.delete_item);
        btn = (Button)findViewById(R.id.btn_delete);

        Intent intent = getIntent();

        String keys = intent.getExtras().getString("Key");
        String occasi = intent.getExtras().getString("Occasion");

        heading.setText(occasi);

        String occasion = occasi.toLowerCase().trim();
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String ite = item.getText().toString().toLowerCase().trim();

                dbRef = rootNode.getInstance().getReference("caterer/"+keys);

                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Boolean a = snapshot.child(occasion).hasChildren();
                        if(a == true){
                            int n = (int) snapshot.child(occasion).getChildrenCount();
                            if(n == 1){
                                snapshot.child(occasion).getRef().removeValue();
                            }else{
                                snapshot.child(occasion).child(ite).getRef().removeValue();
                            }
                            Toast.makeText(DeleteItem.this," "+ite+" is deleted Successfully",Toast.LENGTH_SHORT).show();
                        }else{
                            alert(ite);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void alert(String ite) {

        AlertDialog alert = new AlertDialog.Builder(DeleteItem.this).setTitle("Message").setMessage(" "+ite.toUpperCase()+" is not present for this event").create();
        alert.show();
    }
}