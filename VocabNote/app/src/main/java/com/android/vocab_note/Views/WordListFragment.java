package com.android.vocab_note.Views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.vocab_note.Model.Entity.Word;
import com.android.vocab_note.R;
import com.android.vocab_note.ViewModels.RepositoryViewModelFactory;
import com.android.vocab_note.ViewModels.WordListFragmentViewModel;
import com.android.vocab_note.Views.Adapters.WordAdapter2;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;


public class WordListFragment extends SimpleRecycleViewFragment
{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CATEGORY = "category_id";
    private static final String INSTANCE_CATEGORY = "category_id";

    private int categoryId;

    private WordListFragmentViewModel viewModel;
    private ActionMode actionMode;
    private WordLongClickActionMode wordLongClickActionMode = new WordLongClickActionMode();
    private boolean isInActionMode = false;
    private WordAdapter2 wordAdapter = new WordAdapter2();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param categoryId The category of the list of words that this fragment display
     * @return A new instance of fragment TodoListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WordListFragment newInstance(int categoryId)
    {
        WordListFragment fragment = new WordListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY, categoryId);
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
                categoryId = savedInstanceState.getInt(INSTANCE_CATEGORY);
        }

        if (getArguments() != null)
        {
            categoryId = getArguments().getInt(ARG_CATEGORY);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootlayout = super.onCreateView(inflater, container, savedInstanceState);

        setAdapter(wordAdapter);

        wordAdapter.setOnWordClickListener((word, position) ->
        {
            if (!isInActionMode)
            {
                //show detail of a word
                Intent viewWordIntent = new Intent(requireActivity(), AddWordActivity.class);
                viewWordIntent.putExtra(AddWordActivity.EXTRA_CATEGORY_ID, categoryId);
                viewWordIntent.putExtra(AddWordActivity.EXTRA_WORD_ID, word.getId());
                startActivity(viewWordIntent);
            }
            else
            {
                if (actionMode != null)
                    toggleSelection(position);
            }
        });

        wordAdapter.setOnnWordLongClickListener((word, position) ->
        {
            //delete this == undefined action of the app. This may be null when first set
            //but better not touch it
            if (actionMode == null)
            {
                actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(wordLongClickActionMode);
            }

            toggleSelection(position);
        });


        viewModel = new ViewModelProvider(this,
                new RepositoryViewModelFactory(requireActivity().getApplication()))
                .get(WordListFragmentViewModel.class);
        viewModel.setCategory(categoryId);

        viewModel.getWordList().observe(getViewLifecycleOwner(), words -> wordAdapter.setWords(words));


        DividerItemDecoration decoration = new DividerItemDecoration(
                requireActivity().getApplicationContext(), VERTICAL);
        getRecyclerView().addItemDecoration(decoration);

        return rootlayout;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt(INSTANCE_CATEGORY, categoryId);
    }

    private void toggleSelection(int position)
    {
        wordAdapter.toggleSelection(position);

        int count = wordAdapter.getSelectedItemCount();

        if (count == 0)
        {
            finishActionMode();
        }
        else
        {
            if (actionMode != null)
            {
                actionMode.setTitle(count + " items selected");
                actionMode.invalidate();
            }

            isInActionMode = true;
        }
    }

    //Action mode for the long-click to select multiple item of the SelectableAdapter
    private class WordLongClickActionMode implements ActionMode.Callback
    {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu)
        {
            mode.getMenuInflater().inflate(R.menu.select_action_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu)
        {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.action_delete:
                    viewModel.deleteWordList(wordAdapter.getSelectedItems());
                    finishActionMode();
                    return true;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode)
        {
            wordAdapter.clearSelection();
            isInActionMode = false;
            actionMode = null;
        }
    }

    private void finishActionMode()
    {
        if (actionMode != null)
            actionMode.finish();
        isInActionMode = false;
    }
}
