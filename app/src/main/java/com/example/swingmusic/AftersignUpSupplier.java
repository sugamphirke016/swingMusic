package com.example.swingmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class AftersignUpSupplier extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftersign_up_supplier);

        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(AftersignUpSupplier.this,R.color.black));

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent(AftersignUpSupplier.this,SecondSupplier.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}