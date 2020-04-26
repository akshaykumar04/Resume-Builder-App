package com.sstechcanada.resumeapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.sstechcanada.resumeapp.R;
import com.sstechcanada.resumeapp.activities.JefBookingActivity;
import com.sstechcanada.resumeapp.activities.JobSearchActivity;


public class HomeFragment extends Fragment {
    private CardView jobs, resume, jef;

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, viewGroup, false);

        jobs = view.findViewById(R.id.JobsCard);
        jef = view.findViewById(R.id.cardView2);

        jobs.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), JobSearchActivity.class));
        });

        jef.setOnClickListener(view2 -> {
            startActivity(new Intent(getActivity(), JefBookingActivity.class));
        });

        return view;
    }


}
