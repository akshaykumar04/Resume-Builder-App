package com.sstechcanada.resumeapp.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.sstechcanada.resumeapp.R;
import com.sstechcanada.resumeapp.activities.JefBookingActivity;
import com.sstechcanada.resumeapp.activities.ResumeBuilderActivity;


public class ResumeHome extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.child_fragment_resume_home, container, false);
        Button buildResume = rootView.findViewById(R.id.button);
        buildResume.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), ResumeBuilderActivity.class));
        });

        return rootView;
    }



}
