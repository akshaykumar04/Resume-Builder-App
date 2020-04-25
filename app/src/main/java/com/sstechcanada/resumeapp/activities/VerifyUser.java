package com.sstechcanada.resumeapp.activities;

/**
 * Created by Akshay Kumar on 26/04/2020.
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sstechcanada.resumeapp.R;

import java.util.Objects;


public class VerifyUser extends AppCompatActivity {

    final private String TAG = "VerifyUser";
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_user);

        //init Buttons
        ImageView btnEmail = findViewById(R.id.SendAgainBtn);
        ImageView btnLogout = findViewById(R.id.LogOutBtn);
        ImageView btnRefresh = findViewById(R.id.RefreshBtn);

        //yoyo
        YoYo.with(Techniques.Bounce)
                .duration(2000)
                .repeat(25)
                .playOn(findViewById(R.id.mailSent));

        auth = FirebaseAuth.getInstance();
        authListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                finishAffinity();
            }
        };

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            if (user.isEmailVerified()) {
                email_verified();
            } else {
                email_not_verified();
            }
        }

        btnLogout.setOnClickListener(v -> {
            signOut();
        });


        //to resend the email if email is not received due to any network issue
        btnEmail.setOnClickListener(view -> resendEmail());

        btnRefresh.setOnClickListener(view -> {
            refreshActivity();
        });
    }

    public void resendEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(this, "Email Sent", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void refreshActivity() {
        Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())
                .reload()
                .addOnCompleteListener(task -> {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()) {
                        email_verified();
                    } else {
                        email_not_verified();
                    }
                });
    }

    private void email_verified() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = new Intent(VerifyUser.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void email_not_verified() {                           //function to invoke if email is not verified
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        TextView textView = findViewById(R.id.VerificationText);
        textView.setText("A verification email has been successfully sent to " + user.getEmail());
    }

    //sign out method
    public void signOut() {
        auth.signOut();
        startActivity(new Intent(VerifyUser.this, LoginActivity.class));

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        refreshActivity();
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}