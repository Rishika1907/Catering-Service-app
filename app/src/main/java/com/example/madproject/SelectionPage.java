package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SelectionPage extends AppCompatActivity {

    Button btn_caterer,btn_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_page);

        // buttons for choice between caterer and user
        btn_caterer =(Button)findViewById(R.id.cRegistered);
        btn_user = (Button)findViewById(R.id.cRegisterP);

        // on clicking caterer - leads to caterer registration
        btn_caterer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectionPage.this,Caterer_Register.class);
                startActivity(i);
            }
        });

        // on clicking customer - leads to user registration
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectionPage.this,User_Register.class);
                startActivity(i);
            }
        });
    }
}