package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OcassionUnavailable extends AppCompatActivity {
    TextView t;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocassion_unavailable);
        Intent i = getIntent();
        String text=i.getStringExtra("not");
        t =(TextView)findViewById(R.id.textView);
        t.setText(text);

        /*back button*/
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(OcassionUnavailable.this,occasion.class);
                i.putExtra("Uniqid","From not avaliable");
                startActivity(i);
            }
        });

    }
}