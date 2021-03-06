package com.android.vocab_note;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.android.vocab_note.Model.AppDatabase;
import com.android.vocab_note.Model.Entity.Category;
import com.android.vocab_note.Model.Entity.Word;

import java.util.ArrayList;
import java.util.List;

public class DataRepository
{
    private static DataRepository instance = null;

    private AppDatabase appDatabase;
    private AppExecutors appExecutors;

    private LiveData<List<Category>> categoryList;
    private LiveData<List<Word>> wordList;

    private DataRepository(Context appContext)
    {
        appDatabase = AppDatabase.getInstance(appContext);
        appExecutors = AppExecutors.getInstance();

        categoryList = appDatabase.getCategoryDao().getCategoryList();
        wordList = appDatabase.getWordDao().getWordListLD();
    }

    public static DataRepository getInstance(Context appContext)
    {
        if (instance == null)
        {
            synchronized (DataRepository.class)
            {
                instance = new DataRepository(appContext);
            }
        }

        return instance;
    }

    public LiveData<List<Category>> getCategoryList()
    {
        return categoryList;
    }

    public Category getCategoryByName(String name)
    {
        return appDatabase.getCategoryDao().getCategoryByName(name);
    }

    public void insertCategory(Category category)
    {
        appExecutors.getDiskIO().execute(() ->
                appDatabase.getCategoryDao().insertCategory(category));
    }

    public void updateWordCategory(Category category)
    {
        appExecutors.getDiskIO().execute(() ->
                appDatabase.getCategoryDao().updateCategory(category));
    }

    public void deleteCategory(Category category)
    {
        appExecutors.getDiskIO().execute(() ->
                appDatabase.getCategoryDao().deleteCategory(category));
    }

    public LiveData<List<Word>> getWordListLD()
    {
        return wordList;
    }

    //DONT USE ON MAIN THREAD
    public Word getRandomWord()
    {
        return appDatabase.getWordDao().getRandomWord();
    }

    public LiveData<List<Word>> getWordListByCategory(int categoryId)
    {
        Log.d("Repo", "fetching data from database...");

        return appDatabase.getWordDao().getWordListByCategory(categoryId);
    }

    public LiveData<Word> getWordById(int id)
    {
        return appDatabase.getWordDao().getWordById(id);
    }

    public void insertWordItem(Word word)
    {
        appExecutors.getDiskIO().execute(() ->
                appDatabase.getWordDao().insertWord(word));
    }

    public void updateWordItem(Word word)
    {
        appExecutors.getDiskIO().execute(() ->
                appDatabase.getWordDao().updateWord(word));
    }

    public void deleteWordItem(Word word)
    {
        appExecutors.getDiskIO().execute(() ->
                appDatabase.getWordDao().deleteWord(word));
    }

    public void deleteWordList(List<Word> wordsToDelete)
    {
        appExecutors.getDiskIO().execute(() ->
                appDatabase.getWordDao().deleteWordList(wordsToDelete));
    }

    public void changeWordsToDefaultCategory(int oldCategoryId)
    {
        appExecutors.getDiskIO().execute(() ->
        {
            int commonCategoryId = appDatabase.getCategoryDao()
                    .getCategoryByName(Constants.CATEGORY_COMMON).getId();
            appDatabase.getWordDao().updateWordsCategory(oldCategoryId, commonCategoryId);
        });
    }
}
