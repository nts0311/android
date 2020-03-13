package com.android.vocab_note.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.vocab_note.Model.DataRepository;
import com.android.vocab_note.Model.Entity.Word;

public class AddWordViewModel extends ViewModel
{
    private LiveData<Word> extraWord;

    public AddWordViewModel(DataRepository repo, int wordId)
    {
        extraWord = repo.getWordById(wordId);
    }

    public LiveData<Word> getExtraWord()
    {
        return extraWord;
    }
}
