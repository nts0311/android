package com.android.vocab_note.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.vocab_note.DataRepository;
import com.android.vocab_note.Model.Entity.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordTestViewModel extends ViewModel
{
    private DataRepository repository;
    private MutableLiveData<Boolean> isCorrect;
    private MutableLiveData<Word> wordToAsk;
    private MutableLiveData<List<Word>> otherWords;
    private Random random;


    public WordTestViewModel(DataRepository repository)
    {
        this.repository = repository;
        random = new Random();
        isCorrect = new MutableLiveData<>(false);
        wordToAsk = new MutableLiveData<>();
        otherWords = new MutableLiveData<>();
        createQuestion();
    }

    public MutableLiveData<Boolean> getIsCorrect()
    {
        return isCorrect;
    }

    public MutableLiveData<Word> getWordToAsk()
    {
        return wordToAsk;
    }

    public MutableLiveData<List<Word>> getOtherWords()
    {
        return otherWords;
    }

    public void createQuestion()
    {
        //safe to call getValue because we observed it in MainActivity
        List<Word> wordList = repository.getWordListLD().getValue();

        if (wordList.size() < 4)
            return;

        int wordToAskIndex = random.nextInt(wordList.size());

        List<Word> randomWords = new ArrayList<>();
        for (int i = 0; i < 3; i++)
        {
            //make sure random index does not equal word to ask index or any added word
            int randomIndex;
            Word word;
            do
            {
                randomIndex = random.nextInt(wordList.size());
                word = wordList.get(randomIndex);
            } while (randomIndex == wordToAskIndex || randomWords.contains(word));

            randomWords.add(word);
        }
        otherWords.setValue(randomWords);

        wordToAsk.setValue(wordList.get(wordToAskIndex));
    }

    public void submitResult(String answer)
    {
        if (answer.equals(wordToAsk.getValue().getMeaning()))
        {
            isCorrect.setValue(true);
        }
        else
        {
            isCorrect.setValue(false);
        }
    }
}
