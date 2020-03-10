package com.android.todolist.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.todolist.Model.Entity.Category;
import com.android.todolist.Model.DataRepository;
import com.android.todolist.Model.Entity.Word;
import com.android.todolist.MyApplication;

import java.util.List;

public class WordListFragmentViewModel extends AndroidViewModel
{
    private static final String LOG_TAG = WordListFragmentViewModel.class.getSimpleName();

    private DataRepository repository;
    private LiveData<List<Word>> wordList;
    private String currentCategory;

    public WordListFragmentViewModel(@NonNull Application application)
    {
        super(application);

        Log.d(LOG_TAG, "Creating Fragment's view model");

        repository = ((MyApplication) application).getRepository();
    }

    public void setCategory(String category)
    {
        currentCategory = category;
        wordList = repository.getWordListByCategory(currentCategory);
    }

    public LiveData<List<Word>> getWordList()
    {
        return wordList;
    }
}
