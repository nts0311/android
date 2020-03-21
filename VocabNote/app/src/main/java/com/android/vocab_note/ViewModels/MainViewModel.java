package com.android.vocab_note.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.vocab_note.DataRepository;
import com.android.vocab_note.Model.Entity.Category;
import com.android.vocab_note.Model.Entity.Word;

import java.util.List;

public class MainViewModel extends ViewModel
{
    private DataRepository repository;
    private LiveData<List<Word>> wordList;
    private LiveData<List<Category>> categoryList;

    public MainViewModel(DataRepository repository)
    {
        this.repository = repository;
        wordList = repository.getWordListLD();
        categoryList=repository.getCategoryList();
    }

    public LiveData<List<Word>> getWordList()
    {
        return wordList;
    }

    public LiveData<List<Category>> getCategoryList()
    {
        return categoryList;
    }
}
