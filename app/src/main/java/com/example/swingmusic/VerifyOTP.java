package com.example.swingmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.chaos.view.PinView;
import com.example.swingmusic.databinding.ActivityVerifyOtpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {
    private Button button;
    ProgressBar progressBar;
    ImageButton close;
    TextView tv, tv_down;
    LottieAnimationView lottieAnimationView;
    PinView pinFromUser;
    String codeBySystem;
    private ActivityVerifyOtpBinding binding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyOtpBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_verify_otp);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(VerifyOTP.this,R.color.home_scr));
        tv = findViewById(R.id.pno);
        pinFromUser = findViewById(R.id.pin_view);
        button = findViewById(R.id.verifyButt);
        close = findViewById(R.id.close);
        progressBar = findViewById(R.id.progressBar);
        lottieAnimationView = findViewById(R.id.anime_down);
        tv_down = findViewById(R.id.auto);

        button.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String code = pinFromUser.getText().toString();
                if(code != null){
                    verifyCode(code);
                }
            }
        });
        String _phoneNo = getIntent().getStringExtra("phoneNo");
        tv.setText(_phoneNo);
        sendVerificationCodeToUser(_phoneNo);
    }

    public void closeButt(View view){
        Toast.makeText(this, "Operation Prohibited!", Toast.LENGTH_SHORT).show();
    }

    private void sendVerificationCodeToUser(String phoneNo) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNo)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

        private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                String code = credential.getSmsCode();
                if(code != null){
                    pinFromUser.setText(code);
                    verifyCode(code);
                    progressBar.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                codeBySystem = verificationId;
            }
        };


    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,code);
        signInUsingCredential(credential);
    }

        private void signInUsingCredential(PhoneAuthCredential credential) {
            progressBar.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                lottieAnimationView.setVisibility(View.GONE);
                                tv_down.setVisibility(View.GONE);
                                Toast.makeText(VerifyOTP.this, "Verification Completed!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(VerifyOTP.this, activity_email_otp.class);
                                String _email = getIntent().getStringExtra("email");
                                intent.putExtra("email",_email);
                                startActivity(intent);
                            } else {
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(VerifyOTP.this, "Verification Not Completed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
}