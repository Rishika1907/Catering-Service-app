package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //3 seconds is the display time of splash screen
    private static final int SPLASH_SCREEN_TIME_OUT = 3000;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent is used to switch from one activity to another.
                Intent i = new Intent(MainActivity.this,Login_Page.class);
                //invoke the SecondActivity.
                startActivity(i);
                //the current activity will get finished.
                finish();

            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}