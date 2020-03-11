package com.android.todolist.Views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.todolist.ViewModels.WordListFragViewModelFactory;
import com.android.todolist.ViewModels.WordListFragmentViewModel;
import com.android.todolist.Views.Adapters.WordAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WordListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
/*public class WordListFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CATEGORY = "category";
    private static final String INSTANCE_CATEGORY = "category";

    // TODO: Rename and change types of parameters
    private String category;

    private WordAdapter wordAdapter;
    private RecyclerView rvWordList;
    private WordListFragmentViewModel viewModel;

    public WordListFragment()
    {
        // Required empty public constructor
    }

    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param //category Parameter 1.
     * @return A new instance of fragment TodoListFragment.
     *//*
    // TODO: Rename and change types and number of parameters
    public static WordListFragment newInstance(String category)
    {
        WordListFragment fragment = new WordListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
        {
            if (savedInstanceState.containsKey(INSTANCE_CATEGORY))
                category = savedInstanceState.getString(INSTANCE_CATEGORY);
        }

        if (getArguments() != null)
        {
            category = getArguments().getString(ARG_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootLayout = inflater.inflate(R.layout.fragment_word_list, container, false);

        //use getActivity() instead of this because when the fragment is destroyed not by conf changes,
        //all stored view models get remove.
        viewModel = new ViewModelProvider(requireActivity(),
                new WordListFragViewModelFactory(requireActivity().getApplication()))
                .get(WordListFragmentViewModel.class);

        wordAdapter = new WordAdapter();
        wordAdapter.setOnWordClickListener(word ->
        {
            Intent viewWordIntent = new Intent(requireActivity(), AddWordActivity.class);
            viewWordIntent.putExtra(AddWordActivity.EXTRA_CATEGORY, category);
            viewWordIntent.putExtra(AddWordActivity.EXTRA_WORD_ID, word.getId());
            startActivity(viewWordIntent);
        });

        rvWordList = rootLayout.findViewById(R.id.rv_word_list);
        rvWordList.setAdapter(wordAdapter);
        rvWordList.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.setCategory(category);

        viewModel.getWordList().observe(getViewLifecycleOwner(), words -> wordAdapter.setWords(words));

        return rootLayout;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(INSTANCE_CATEGORY, category);
    }
}*/

public class WordListFragment extends SimpleRecycleViewFragment
{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CATEGORY = "category";
    private static final String INSTANCE_CATEGORY = "category";

    private String category;

    private WordListFragmentViewModel viewModel;

    /**
            * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
             * @param category Parameter 1.
        * @return A new instance of fragment TodoListFragment.
        */
    // TODO: Rename and change types and number of parameters
    public static WordListFragment newInstance(String category)
    {
        WordListFragment fragment = new WordListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
        {
            if (savedInstanceState.containsKey(INSTANCE_CATEGORY))
                category = savedInstanceState.getString(INSTANCE_CATEGORY);
        }

        if (getArguments() != null)
        {
            category = getArguments().getString(ARG_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootlayout = super.onCreateView(inflater, container, savedInstanceState);

        //use getActivity() instead of this because when the fragment is destroyed not by conf changes,
        //all stored view models get remove.

        setAdapter(new WordAdapter());

        WordAdapter wordAdapter=(WordAdapter) getAdapter();

        wordAdapter.setOnWordClickListener(word ->
        {
            Intent viewWordIntent = new Intent(requireActivity(), AddWordActivity.class);
            viewWordIntent.putExtra(AddWordActivity.EXTRA_CATEGORY, category);
            viewWordIntent.putExtra(AddWordActivity.EXTRA_WORD_ID, word.getId());
            startActivity(viewWordIntent);
        });

        viewModel = new ViewModelProvider(requireActivity(),
                new WordListFragViewModelFactory(requireActivity().getApplication()))
                .get(WordListFragmentViewModel.class);
        viewModel.setCategory(category);
        viewModel.getWordList().observe(getViewLifecycleOwner(), words -> wordAdapter.setWords(words));

        return rootlayout;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(INSTANCE_CATEGORY, category);
    }
}
