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
import com.android.vocab_note.ViewModels.WordListFragViewModelFactory;
import com.android.vocab_note.ViewModels.WordListFragmentViewModel;
import com.android.vocab_note.Views.Adapters.WordAdapter2;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;


public class WordListFragment extends SimpleRecycleViewFragment
{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CATEGORY = "category";
    private static final String INSTANCE_CATEGORY = "category";

    private String category;

    private WordListFragmentViewModel viewModel;
    private ActionMode actionMode;
    private WordLongClickActionMode wordLongClickActionMode = new WordLongClickActionMode();
    private boolean isInActionMode = false;

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

        setAdapter(new WordAdapter2());
        WordAdapter2 wordAdapter = (WordAdapter2) getAdapter();

        wordAdapter.setOnWordClickListener((word, position) ->
        {
            if (!isInActionMode)
            {
                Intent viewWordIntent = new Intent(requireActivity(), AddWordActivity.class);
                viewWordIntent.putExtra(AddWordActivity.EXTRA_CATEGORY, category);
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
            if (actionMode == null)
            {
                actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(wordLongClickActionMode);
            }

            toggleSelection(position);
        });


        viewModel = new ViewModelProvider(requireActivity(),
                new WordListFragViewModelFactory(requireActivity().getApplication()))
                .get(WordListFragmentViewModel.class);
        viewModel.setCategory(category);
        viewModel.getWordList().observe(getViewLifecycleOwner(), words ->
        {
            wordAdapter.setWords(words);
        });

        DividerItemDecoration decoration = new DividerItemDecoration(
                requireActivity().getApplicationContext(), VERTICAL);
        getRecyclerView().addItemDecoration(decoration);

        return rootlayout;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(INSTANCE_CATEGORY, category);
    }

    private void toggleSelection(int position)
    {
        WordAdapter2 wordAdapter = (WordAdapter2) getAdapter();

        wordAdapter.toggleSelection(position);

        int count = wordAdapter.getSelectedItemCount();

        if (count == 0)
        {
            if (actionMode != null)
                actionMode.finish();
            isInActionMode = false;
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
                    WordAdapter2 wordAdapter = ((WordAdapter2) getAdapter());

                    List<Word> words = wordAdapter.getWords();
                    List<Integer> selectedIndexes = wordAdapter.getSelectedItems();
                    List<Word> wordsToDelete = new ArrayList<>();

                    for (Integer index : selectedIndexes)
                        wordsToDelete.add(words.get(index));
                    viewModel.deleteWordList(wordsToDelete);

                    if (actionMode != null)
                        actionMode.finish();
                    isInActionMode = false;

                    return true;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode)
        {
            ((WordAdapter2) getAdapter()).clearSelection();
            isInActionMode = false;
            actionMode = null;
        }
    }
}
