package com.example.swingmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class accSupplierScr extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 9000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_supplier_scr);

        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent(accSupplierScr.this,SwingHomePage.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}