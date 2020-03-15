package com.android.vocab_note.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.vocab_note.DataRepository;
import com.android.vocab_note.Model.Entity.Word;

public class AddWordViewModel extends ViewModel
{
    private DataRepository repository;
    private LiveData<Word> extraWord;

    public AddWordViewModel(DataRepository repo, int wordId)
    {
        repository=repo;
        extraWord = repo.getWordById(wordId);
    }

    public LiveData<Word> getExtraWord(int wordId)
    {
        extraWord = repository.getWordById(wordId);
        return extraWord;
    }

    public void addWord(Word newWord)
    {
        repository.insertWordItem(newWord);
    }

    public void updateWord(Word word)
    {
        repository.updateWordItem(word);
    }
}
