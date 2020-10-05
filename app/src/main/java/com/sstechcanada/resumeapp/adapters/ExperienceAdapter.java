package com.sstechcanada.resumeapp.adapters;
import androidx.annotation.NonNull;

import com.sstechcanada.resumeapp.models.Experience;

import java.util.List;


public class ExperienceAdapter extends ResumeEventAdapter<Experience> {

    public ExperienceAdapter(@NonNull List<Experience> list,
                             ResumeEventOnClickListener resumeEventOnClickListener) {
        super(list, resumeEventOnClickListener);
    }
}
