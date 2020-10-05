package com.sstechcanada.resumeapp.adapters;


import androidx.annotation.NonNull;

import com.sstechcanada.resumeapp.models.School;

import java.util.List;


public class SchoolsAdapter extends ResumeEventAdapter<School> {

    public SchoolsAdapter(@NonNull List<School> list,
                          ResumeEventOnClickListener resumeEventOnClickListener) {
        super(list, resumeEventOnClickListener);
    }
}