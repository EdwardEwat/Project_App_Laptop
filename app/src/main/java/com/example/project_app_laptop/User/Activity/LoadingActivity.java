package com.example.project_app_laptop.User.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_app_laptop.R;

public class LoadingActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent myit = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(myit);
                finish();
            }
        }, 1000);
    }
}