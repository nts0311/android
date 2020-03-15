package com.android.vocab_note.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.vocab_note.DataRepository;
import com.android.vocab_note.Model.Entity.Word;


import java.util.ArrayList;
import java.util.List;

public class WordListFragmentViewModel extends ViewModel
{
    private static final String LOG_TAG = WordListFragmentViewModel.class.getSimpleName();

    private DataRepository repository;
    private LiveData<List<Word>> wordList = null;
    private int currentCategoryId;

    public WordListFragmentViewModel(DataRepository repository)
    {
        Log.d(LOG_TAG, "Creating Fragment's view model");

        this.repository = repository;
    }

    public LiveData<List<Word>> getWordList()
    {
        return wordList;
    }

    /**
     * Set the category for the fragment's view model and load all words in that category to view model
     *
     * @param categoryId the Id of the category to be set
     */
    public void setCategory(int categoryId)
    {
        if (currentCategoryId == categoryId)
            return;

        currentCategoryId = categoryId;

        if (wordList == null)
            wordList = repository.getWordListByCategory(currentCategoryId);
    }

    /**
     * Delete a list of words in a category
     *
     * @param selectedIndexes the list of selected word's index to be delete
     */
    public void deleteWordList(List<Integer> selectedIndexes)
    {
        List<Word> words = getWordList().getValue();
        List<Word> wordsToDelete = new ArrayList<>();

        for (Integer index : selectedIndexes)
            wordsToDelete.add(words.get(index));

        repository.deleteWordList(wordsToDelete);
    }
}
