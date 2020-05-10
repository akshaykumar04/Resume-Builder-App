package com.sstechcanada.resumeapp.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.sstechcanada.resumeapp.R;
import com.sstechcanada.resumeapp.activities.JobSearchResultActivity;


public class JobSearchHome extends Fragment {
    private EditText jobsEditText;
    private Button searchJob;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.child_fragment_jobs_home, container, false);
        jobsEditText = rootView.findViewById(R.id.etJob);
        searchJob = rootView.findViewById(R.id.button);

        searchJob.setOnClickListener(view -> {
            showJobs();
        });

        return rootView;
    }

    private void showJobs() {
        String JobQuery = jobsEditText.getText().toString().trim();
        if (!JobQuery.isEmpty()) {
            Intent jobResults = new Intent(getActivity(), JobSearchResultActivity.class);
            jobResults.putExtra("keyword", JobQuery);
            startActivity(jobResults);
        } else {
            Toast.makeText(getContext(), "Please enter a job keyword to continue", Toast.LENGTH_LONG).show();
        }
    }
}
