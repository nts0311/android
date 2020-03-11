package com.android.todolist.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.todolist.Model.DataRepository;
import com.android.todolist.MyApplication;

public class AddWordViewModelFactory implements ViewModelProvider.Factory
{
    private DataRepository repository;
    private int extraWordId;

    public AddWordViewModelFactory(DataRepository repository, int extraWordId)
    {
        this.repository = repository;
        this.extraWordId = extraWordId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass)
    {
        return (T) new AddWordViewModel(repository, extraWordId);
    }
}
