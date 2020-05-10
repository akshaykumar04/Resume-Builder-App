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


public class JefHome extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.child_fragment_jef_home, container, false);
        Button startJef = rootView.findViewById(R.id.button);
        startJef.setOnClickListener(view -> {
        startActivity(new Intent(getActivity(), JefBookingActivity.class));
        });

        return rootView;
    }

}
