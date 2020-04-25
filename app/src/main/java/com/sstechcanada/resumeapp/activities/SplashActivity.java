package com.sstechcanada.resumeapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sstechcanada.resumeapp.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(() -> {
            checkUserStatus();
        }, SPLASH_TIME_OUT);
    }

    private void checkUserStatus() {
        FirebaseUser User = mAuth.getCurrentUser();
        if (User != null) {
            Intent i = new Intent(SplashActivity.this, VerifyUser.class);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

}
