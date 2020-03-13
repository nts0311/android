package com.android.vocab_note.ViewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.vocab_note.DataRepository;

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
