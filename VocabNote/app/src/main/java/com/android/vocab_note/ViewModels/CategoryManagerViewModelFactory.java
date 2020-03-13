package com.android.vocab_note.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.vocab_note.MyApplication;

public class CategoryManagerViewModelFactory implements ViewModelProvider.Factory
{
    private Application application;

    public CategoryManagerViewModelFactory(Application application)
    {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass)
    {
        return (T) new CategoryManagerViewModel(((MyApplication) application).getRepository());
    }
}
