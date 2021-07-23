package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class NewActivity extends AppCompatActivity  {
    EditText t1, t2;
    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        t1 = (EditText) findViewById(R.id.location);
        t2 = (EditText) findViewById(R.id.dateOfEvent);

        /*button on click leads to order summary*/
        Button b=(Button)findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(NewActivity.this,OrderSummary.class);
                /*getting location from edittext*/
                i.putExtra("location",t1.getText().toString());
                /*getting date from edittext*/
                i.putExtra("date",t2.getText().toString());

                startActivity(i);
            }
        });

        /*back button*/
        backButton=(ImageView)findViewById(R.id.imageView);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}