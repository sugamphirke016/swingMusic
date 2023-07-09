package com.example.swingmusic;

import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.media.Image;
        import android.os.Bundle;
        import android.os.Handler;
        import android.view.WindowManager;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.airbnb.lottie.LottieAnimationView;

public class supplier extends AppCompatActivity {
    ImageView splash_bg, logo;
    TextView tvLogo;
    LottieAnimationView lottieAnimationView;
    private static int SPLASH_TIME_OUT = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_supplier);
        getSupportActionBar().hide();
        logo = findViewById(R.id.logo);
        splash_bg = findViewById(R.id.splash_bg);
        tvLogo = findViewById(R.id.tvLogo);
        lottieAnimationView = findViewById(R.id.anime);

        splash_bg.animate().translationY(-5000).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(3000).setDuration(1000).setStartDelay(4000);
        tvLogo.animate().translationY(3000).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(3000).setDuration(1000).setStartDelay(4000);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent(supplier.this,SwingHomePage.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}