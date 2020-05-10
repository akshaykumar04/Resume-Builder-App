package com.sstechcanada.resumeapp.activities;

import android.app.ProgressDialog;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sstechcanada.resumeapp.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private ProgressDialog pDialog;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView signup, resetPass;
    private ProgressBar progressBar;
    private EditText inputEmail, inputPass;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SignInButton googleSignInButton = findViewById(R.id.sign_in_button);
        Button signOutButton = findViewById(R.id.sign_out_button);
        progressBar = findViewById(R.id.progressBar2);
        inputEmail = findViewById(R.id.etEmail);
        inputPass = findViewById(R.id.etPass);
        signInButton = findViewById(R.id.buttonSignIn);
        resetPass = findViewById(R.id.textView3);


        pDialog = new ProgressDialog(LoginActivity.this);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

        googleSignInButton.setOnClickListener(v -> signIn());
        signOutButton.setOnClickListener(v -> signOut());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String data = bundle.getString("key");
            if (data == null) {
                signOut();
            }
            if (data.equals("Google")){
                signIn();
            }
        }

        //signup page intent
        signup = findViewById(R.id.signupText);
        signup.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));

        signInButton.setOnClickListener(view -> loginUser());

        resetPass.setOnClickListener(view -> forgetPass());
        checkUserStatus();
    }

    public void loginUser() {
        final String email = inputEmail.getText().toString();
        final String password = inputPass.getText().toString();

        if (password.isEmpty()) {
            inputPass.setError(getString(R.string.input_error_password));
            inputPass.requestFocus();

        } else {
            if (!email.isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, task -> {
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            progressBar.setVisibility(View.GONE);
                            if (!task.isSuccessful()) {
                                // there was an error

                                if (password.length() < 6) {
                                    inputPass.setError(getString(R.string.input_error_password_length));
                                    inputPass.requestFocus();

                                } else {
                                    Toast.makeText(this, "Authentication Failed, Please check your Id & Pass",
                                            Toast.LENGTH_LONG).show();

                                }
                            } else {

                                final FirebaseUser user = Objects.requireNonNull(task.getResult()).getUser();
                                if (user != null) {
                                    if (user.isEmailVerified()) {
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finishAffinity();
                                    } else {
                                        startActivity(new Intent(LoginActivity.this, VerifyUser.class));
                                    }
                                }
                            }
                        });

            } else

                inputEmail.setError(getString(R.string.input_error_email));
            inputEmail.requestFocus();

        }


    }

    /**
     * Display Progress bar while Logging in through Google
     */

    private void displayProgressDialog() {
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        displayProgressDialog();
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            hideProgressDialog();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Login Failed: ", Toast.LENGTH_SHORT).show();
                        }

                        hideProgressDialog();
                    }

                });
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
    }

    private void hideProgressDialog() {
        pDialog.dismiss();
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void checkUserStatus() {
        FirebaseUser User = mAuth.getCurrentUser();
        if (User != null) {
            Intent i = new Intent(LoginActivity.this, VerifyUser.class);
            startActivity(i);
            finish();
        }

    }


    //reset password
    public void forgetPass() {
        String email2 = inputEmail.getText().toString().trim();

        if ((email2.isEmpty())) {
            inputEmail.setError(getString(R.string.input_error_forget_pass_email_empty));
            inputEmail.requestFocus();
            return;
        } else
            progressBar.setVisibility(View.VISIBLE);
            mAuth.sendPasswordResetEmail(email2)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "We have sent you instructions to reset your password!",
                                Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(this, "Failed to send reset email!",
                                Toast.LENGTH_LONG).show();
                    }

                    progressBar.setVisibility(View.GONE);
                });
    }

}