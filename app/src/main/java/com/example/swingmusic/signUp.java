package com.example.swingmusic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.swingmusic.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class signUp extends AppCompatActivity {

    private Button button1, button2;
    private ImageView img;
    private TextView tv_text1, tvDesc;
    TextInputLayout mat1, mat2, mat3, mat4, mat5;

    int code;

    FirebaseDatabase database;
    ProgressDialog progressDialog;

    ActivitySignUpBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(signUp.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account. Please Wait!");

        button2 = findViewById(R.id.butt);
        img = findViewById(R.id.logo_signUp);
        tv_text1 = findViewById(R.id.tv_text1);
        tvDesc = findViewById(R.id.desc);

        mat1 = findViewById(R.id.mat1);
        mat2 = findViewById(R.id.mat2);
        mat3 = findViewById(R.id.mat3);
        mat4 = findViewById(R.id.mat4);
        mat5 = findViewById(R.id.mat5);
        button1 = findViewById(R.id.signupbutt);

        String mat1_txt = mat1.getEditText().getText().toString();
        String mat2_txt = mat2.getEditText().getText().toString();
        String mat3_txt = mat3.getEditText().getText().toString();
        String mat4_txt = mat4.getEditText().getText().toString();
        String mat5_txt = mat5.getEditText().getText().toString();

        binding.signupbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToDatabase();
                if(!validateName() | !validatePassword() | !validateEmail() | !validatePhoneNo() | !validateUsername()){
                    return;
                }
                else{
                    progressDialog.show();
                    auth.createUserWithEmailAndPassword(binding.regEmail.getText().toString(), binding.regPassword.getText().toString()).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if(task.isSuccessful()){
                                        userHelperClass helperClass = new userHelperClass(binding.regUsername.getText().toString(), binding.regEmail.getText().toString(),
                                                binding.regPhoneNo.getText().toString(), binding.regPassword.getText().toString());
                                        String id = task.getResult().getUser().getUid();
                                        database.getReference().child("Users").child(id).setValue(helperClass);

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                callVerifyOTPScreen();
                                            }
                                        },0000);
                                    }
                                    else{
                                        Toast.makeText(signUp.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivitySignIn();
            }
        });
    }

    public void sendDataToDatabase(){
        String mat1_txt = mat1.getEditText().getText().toString().trim();
        String mat2_txt = mat2.getEditText().getText().toString().trim();
        String mat3_txt = mat3.getEditText().getText().toString().trim();
        String mat4_txt = mat4.getEditText().getText().toString().trim();
        String mat5_txt = mat5.getEditText().getText().toString().trim();
        String url = "https://swingmusic.000webhostapp.com/dbconnect.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(signUp.this, response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(signUp.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",mat1_txt);
                params.put("username",mat2_txt);
                params.put("email",mat3_txt);
                params.put("phoneno",mat4_txt);
                params.put("password",mat5_txt);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void openActivitySignIn(){
        Intent intent = new Intent(signUp.this,signIn.class);
        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View,String>(img,"logo_imag");
        pairs[1] = new Pair<View,String>(tv_text1,"logo_name");


        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(signUp.this,pairs);
            startActivity(intent,options.toBundle());
        }
    }


    private Boolean validateName(){
        String val = mat1.getEditText().getText().toString();
        if(val.isEmpty()){
            mat1.setError("Field cannot be empty");
            return false;
        }
        else{
            mat1.setError(null);
            mat1.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername(){
        String val = mat2.getEditText().getText().toString();
        if(val.isEmpty()){
            mat2.setError("Field cannot be empty");
            return false;
        }
        else if(val.length()>=15){
            mat2.setError("Username too long");
            return false;
        }
        else{
            mat2.setError(null);
            mat2.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail(){
        String val = mat3.getEditText().getText().toString();
        if(val.isEmpty()){
            mat3.setError("Field cannot be empty");
            return false;
        }
        else{
            mat3.setError(null);
            mat3.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo(){
        String val = mat4.getEditText().getText().toString();
        if(val.isEmpty()){
            mat4.setError("Field cannot be empty");
            return false;
        }
        else if(val.length() != 10){
            mat4.setError("Enter valid Phone no");
            return false;
        }
        else{
            mat4.setError(null);
            mat4.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val = mat5.getEditText().getText().toString();
        if(val.isEmpty()){
            mat5.setError("Field cannot be empty");
            return false;
        }
        else if(val.length()<6){
            mat2.setError("Password too short");
            return false;
        }
        else{
            mat5.setError(null);
            mat5.setErrorEnabled(false);
            return true;
        }
    }

    public void callVerifyOTPScreen(){
        String _email = mat3.getEditText().getText().toString();
        if(!validatePhoneNo())
            return;

        String _getUserEnteredPhoneNumber = mat4.getEditText().getText().toString().trim();
        String _phoneNo = "+91"+ _getUserEnteredPhoneNumber;

        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
        intent.putExtra("phoneNo", _phoneNo);
        intent.putExtra("email",_email);
        startActivity(intent);
    }


}