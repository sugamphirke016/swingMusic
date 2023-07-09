package com.example.swingmusic;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class activity_email_otp extends AppCompatActivity {
    int code;
    private static int SPLASH_TIME_OUT = 2000;
    private TextView userEmail;
    private PinView pinView;
    private Button verifybutt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_otp);
        String _email = getIntent().getStringExtra("email");
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(activity_email_otp.this,R.color.home_scr));
        sendVerifyEmail();

        userEmail = findViewById(R.id.userEmail);
        userEmail.setText(_email.toString());
        pinView = findViewById(R.id.pinFromUser);
        verifybutt = findViewById(R.id.verifyButt);
        verifybutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Inputedcode = pinView.getText().toString();
                if(Inputedcode != null){
                    checkCode(Inputedcode);
                }
                else {
                    Toast.makeText(activity_email_otp.this, "Enter the OTP!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void closeButt(View view){
        Toast.makeText(this, "Action Prohibited!", Toast.LENGTH_SHORT).show();
    }

    public void sendVerifyEmail(){
        String _email = getIntent().getStringExtra("email");
        Random random = new Random();
        code = random.nextInt(8999)+1000;
        String url = "https://subacid-jumps.000webhostapp.com/sendEmail.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(activity_email_otp.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity_email_otp.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",_email.toString());
                params.put("code",String.valueOf(code));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void checkCode(String Inputedcode){
        if(Inputedcode.equals(String.valueOf(code))){
            Toast.makeText(this, "Verification Completed!", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){
                    Toast.makeText(activity_email_otp.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity_email_otp.this, AftersignUpSupplier.class);
                    startActivity(intent);
                    finish();
                }
            },SPLASH_TIME_OUT);

        }
        else{
            Toast.makeText(this, "OTP Mismatched!", Toast.LENGTH_SHORT).show();
        }
    }


}