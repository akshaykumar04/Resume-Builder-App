package com.sstechcanada.resumeapp.helper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sstechcanada.resumeapp.R;
import com.sstechcanada.resumeapp.adapters.ResumeEventAdapter;
import com.sstechcanada.resumeapp.models.ResumeEvent;


abstract public class ResumeEventFragment<T extends ResumeEvent> extends ResumeFragment
        implements ResumeEventAdapter.ResumeEventOnClickListener {
    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;

    RecyclerView recyclerView;

    protected abstract void addClicked();

    protected abstract ResumeEventAdapter<T> getAdapter(View emptyView);

    protected abstract void delete(int pos);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        View emptyView = view.findViewById(android.R.id.empty);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(getAdapter(emptyView));
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                delete((int) viewHolder.itemView.getTag());
                notifyDataChanged();
            }
        }).attachToRecyclerView(recyclerView);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void notifyDataChanged() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            addClicked();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
