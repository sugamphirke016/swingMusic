package com.example.swingmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SwingAboutUsPage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swing_about_us_page);

        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(SwingAboutUsPage.this,R.color.home_scr));

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id._about_us);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id._home:
                        startActivity(new Intent(getApplicationContext(), SwingHomePage.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id._library:
                        startActivity(new Intent(getApplicationContext(),SwingLibraryPage.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id._account:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id._about_us:
                        return true;
                }
                return false;
            }
        });

    }
}