package com.android.vocab_note.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.vocab_note.DataRepository;
import com.android.vocab_note.Model.Entity.Word;


import java.util.List;

public class WordListFragmentViewModel extends ViewModel
{
    private static final String LOG_TAG = WordListFragmentViewModel.class.getSimpleName();

    private DataRepository repository;
    private LiveData<List<Word>> wordList;
    private int currentCategoryId;

    public WordListFragmentViewModel(DataRepository repository)
    {
        Log.d(LOG_TAG, "Creating Fragment's view model");

        this.repository = repository;
    }

    public void setCategory(int categoryId)
    {
        currentCategoryId = categoryId;
        wordList = repository.getWordListByCategory(currentCategoryId);
    }

    public LiveData<List<Word>> getWordList()
    {
        return wordList;
    }

    public void deleteWordList(List<Word> wordsToDelete)
    {
        repository.deleteWordList(wordsToDelete);
    }
}
