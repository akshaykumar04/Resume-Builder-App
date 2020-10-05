package com.sstechcanada.resumeapp.adapters;
import android.view.View;
import androidx.annotation.NonNull;
import com.sstechcanada.resumeapp.models.Project;

import java.util.List;


public class ProjectsAdapter extends ResumeEventAdapter<Project> {

    public ProjectsAdapter(@NonNull List<Project> list,
                           ResumeEventOnClickListener resumeEventOnClickListener) {
        super(list, resumeEventOnClickListener);
    }

    @Override
    protected void updateViewHolder(ResumeEventAdapterViewHolder viewHolder) {
        viewHolder.subtitle.setVisibility(View.GONE);
    }
}