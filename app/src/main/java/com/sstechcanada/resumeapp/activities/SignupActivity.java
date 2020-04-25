package com.sstechcanada.resumeapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.sstechcanada.resumeapp.R;



public class SignupActivity extends AppCompatActivity {

    private TextView login, forgetpass;
    private Button signup;
    private EditText et_name, et_email, et_pass, et_pass2;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private String TAG = "Signup";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //intent to login screen
        login = findViewById(R.id.signinText);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

        signup = findViewById(R.id.buttonSignUp);
        forgetpass = findViewById(R.id.tvForgetPass);
        et_email = findViewById(R.id.editTextEmail);
        et_name = findViewById(R.id.editTextName);
        et_pass = findViewById(R.id.pass);
        et_pass2 = findViewById(R.id.pass2);
        progressBar = findViewById(R.id.progressBar);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        final String name = et_name.getText().toString().trim();
        final String email = et_email.getText().toString().trim();
        String password = et_pass.getText().toString().trim();
        String password2 = et_pass2.getText().toString().trim();


        if (name.isEmpty()) {
            et_name.setError(getString(R.string.input_error_name));
            et_name.requestFocus();
            return;
        }

        if (email.isEmpty()){
            et_email.setError(getString(R.string.input_error_email));
            et_email.requestFocus();
        }


        if (password.isEmpty() || password2.isEmpty() ) {
            et_pass.setError(getString(R.string.input_error_password));
            et_pass2.setError(getString(R.string.input_error_password));
            et_pass.requestFocus();
            et_pass2.requestFocus();
            return;
        }

        if (!password.equals(password2)) {
            et_pass.setError(getString(R.string.input_error_password_match));
            et_pass2.setError(getString(R.string.input_error_password_match));
            et_pass.requestFocus();
            et_pass2.requestFocus();
            return;
        }


        if (password.length() < 6) {
            et_pass.setError(getString(R.string.input_error_password_length));
            et_pass2.setError(getString(R.string.input_error_password_length));
            et_pass.requestFocus();
            et_pass2.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        FirebaseUser userReg = FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name).build();

                        userReg.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "User profile updated.");
                                        }
                                    }
                                });

                        if (userReg != null) {
                            userReg.sendEmailVerification()            //send verification email
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Log.d(TAG, "Verification email sent.");
                                            Toast.makeText(this, "Verification Email sent successful",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }



                        Intent verify = new Intent(SignupActivity.this, VerifyUser.class);
                        startActivity(verify);
                        finishAffinity();

                    } else {
                        Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }


}
