package com.sstechcanada.resumeapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sstechcanada.resumeapp.R;

public class JobSearchActivity extends AppCompatActivity {

    private Button jobsearch;
    public EditText jobquery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_search);

        jobquery = findViewById(R.id.et_jobquery);
        jobsearch = findViewById(R.id.btn_jobsearch);

        jobsearch.setOnClickListener(view -> {
            String JobQuery = jobquery.getText().toString().trim();
            Intent jobResults = new Intent(JobSearchActivity.this, JobSearchResultActivity.class);
            jobResults.putExtra("keyword", JobQuery);
            startActivity(jobResults);
        });




    }
}
