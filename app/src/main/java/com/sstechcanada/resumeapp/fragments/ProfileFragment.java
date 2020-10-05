package com.sstechcanada.resumeapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sstechcanada.resumeapp.R;
import com.sstechcanada.resumeapp.activities.LoginActivity;
import com.sstechcanada.resumeapp.activities.TestActivity;

import java.util.Objects;


public class ProfileFragment extends Fragment {

    TextView name, email;
    private ImageView profilePic;
    private FirebaseAuth mAuth;
    private String logoutKey = null;


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, viewGroup, false);
        mAuth = FirebaseAuth.getInstance();
        profilePic = view.findViewById(R.id.profilePic);
        name = view.findViewById(R.id.displayName);
        email = view.findViewById(R.id.displayEmail);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateProfile(currentUser);


        CardView Logout = view.findViewById(R.id.logout_button);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog();
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent test = new Intent(getActivity(), TestActivity.class);
                startActivity(test);
            }
        });


        return view;
    }

    private void updateProfile(FirebaseUser user) {

        if (user != null) {
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
            // Loading profile image
            Uri profilePicUrl = user.getPhotoUrl();
            if (profilePicUrl != null) {
                Glide.with(this).load(profilePicUrl)
                        .into(profilePic);
            }
        }

    }

    private void logoutDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Logout?")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    Intent logout = new Intent(getActivity(), LoginActivity.class);
                    logout.putExtra("key", logoutKey);
                    startActivity(logout);
                    getActivity().finish();
                })
                .setNegativeButton(android.R.string.no, (dialogInterface, i) -> {
                })
                .setIcon(R.drawable.log_out)
                .show();

    }
}
