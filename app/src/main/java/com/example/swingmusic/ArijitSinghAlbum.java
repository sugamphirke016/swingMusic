package com.example.swingmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ArijitSinghAlbum extends AppCompatActivity {
    private ImageButton imagebutt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arijit_singh_album);

        getSupportActionBar().hide();

        imagebutt = findViewById(R.id.back);

        imagebutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArijitSinghAlbum.this, SwingHomePage.class);
                startActivity(intent);
            }
        });
    }
}