package com.sstechcanada.resumeapp.fragments.resume;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.sstechcanada.resumeapp.R;
import com.sstechcanada.resumeapp.helper.ResumeFragment;
import com.sstechcanada.resumeapp.helper.TextChangeListener;
import com.sstechcanada.resumeapp.models.Resume;


public class EssentialsFragment extends ResumeFragment {

    public static ResumeFragment newInstance(Resume resume) {
        ResumeFragment fragment = new EssentialsFragment();
        fragment.setResume(resume);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =
                inflater.inflate(R.layout.fragment_essentials, container, false);

        final Resume resume = getResume();

        EditText skillsText = root.findViewById(R.id.input_skills);
        skillsText.setText(resume.skills);
        skillsText.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resume.skills = s.toString();
            }
        });
        EditText languagesText = root.findViewById(R.id.input_languages);
        languagesText.setText(resume.languages);
        languagesText.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resume.languages = s.toString();
            }
        });
        return root;
    }
}
