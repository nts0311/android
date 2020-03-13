package com.android.vocab_note.Views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.vocab_note.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleRecycleViewFragment extends Fragment
{
    //private RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;

    public SimpleRecycleViewFragment()
    {
        // Required empty public constructor
    }

    public void setAdapter(RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter)
    {
        recyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager)
    {
        this.layoutManager = layoutManager;
    }

    public void setRecyclerView(RecyclerView recyclerView)
    {
        this.recyclerView = recyclerView;
    }

    public RecyclerView.Adapter<? extends RecyclerView.ViewHolder> getAdapter()
    {
        return recyclerView.getAdapter();
    }

    public RecyclerView.LayoutManager getLayoutManager()
    {
        return layoutManager;
    }

    public RecyclerView getRecyclerView()
    {
        return recyclerView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootLayout = inflater.inflate(R.layout.fragment_simple_recycle_view, container, false);

        layoutManager = new LinearLayoutManager(requireActivity());
        recyclerView = rootLayout.findViewById(R.id.simple_rv_frag);
        recyclerView.setLayoutManager(layoutManager);

        return rootLayout;
    }
}
