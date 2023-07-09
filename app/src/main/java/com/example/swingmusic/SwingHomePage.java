package com.example.swingmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SwingHomePage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swing_home_page);

        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(SwingHomePage.this,R.color.black));

        //Vertical Navigation Bar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_lay);
        NavigationView navigationView = findViewById(R.id.ver_nav_view); // This is one is the side bar!
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id){
                    case R.id.nav_home:
                        Toast.makeText(SwingHomePage.this, "Home is aldready open!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_library:
                        Intent intent = new Intent(SwingHomePage.this, SwingLibraryPage.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_account:
                        Intent intent1 = new Intent(SwingHomePage.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_settings:
                        Toast.makeText(SwingHomePage.this, "Settings is Clicked!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_aboutus:
                        Intent intent2 = new Intent(SwingHomePage.this, MainActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.logout:
                        Toast.makeText(SwingHomePage.this, "Log Out is Clicked!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_share:
                        Toast.makeText(SwingHomePage.this, "Share is Clicked!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_rateus:
                        Toast.makeText(SwingHomePage.this, "Rate Us is Clicked!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_feedback:
                        Toast.makeText(SwingHomePage.this, "Feedback is Clicked!", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });


        //Bottom Navigation Bar
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id._home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id._home:
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
                        startActivity(new Intent(getApplicationContext(), SwingAboutUsPage.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        LinearLayout layout = findViewById(R.id.arijit_singh_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_arijit = new Intent(SwingHomePage.this,ArijitSinghAlbum.class);
                startActivity(intent_arijit);
            }
        });

        LinearLayout layout1 = findViewById(R.id.zyan_layout);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_zyan = new Intent(SwingHomePage.this,ZyanAlbum.class);
                startActivity(intent_zyan);
            }
        });
    }

}