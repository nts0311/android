package com.android.todolist.Model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.android.todolist.AppExecutors;
import com.android.todolist.Model.Entity.Category;
import com.android.todolist.Model.Entity.Word;

import java.util.List;

public class DataRepository
{
    private static DataRepository instance = null;

    private AppDatabase appDatabase;
    private AppExecutors appExecutors;

    private LiveData<List<Category>> categoryList;
    private LiveData<List<Word>> todoList;

    private DataRepository(Context appContext)
    {
        appDatabase = AppDatabase.getInstance(appContext);
        appExecutors = AppExecutors.getInstance();

        categoryList = appDatabase.getCategoryDao().getCategoryList();
        todoList = appDatabase.getTodoItemDao().getWordList();
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

    public LiveData<Category> getCategoryByName(String name)
    {
        return appDatabase.getCategoryDao().getCategoryByName(name);
    }

    public void insertCategory(Category category)
    {
        appExecutors.getDiskIO().execute(() ->
                appDatabase.getCategoryDao().insertCategory(category));
    }

    public void updateCategory(Category category)
    {
        appExecutors.getDiskIO().execute(() ->
                appDatabase.getCategoryDao().updateCategory(category));
    }

    public void deleteCategory(Category category)
    {
        appExecutors.getDiskIO().execute(() ->
                appDatabase.getCategoryDao().deleteCategory(category));
    }

    public LiveData<List<Word>> getWordList()
    {
        return todoList;
    }

    public LiveData<List<Word>> getWordListByCategory(String category)
    {
        Log.d("Repo","fetching data from database...");

        return appDatabase.getTodoItemDao().getWordListByCategory(category);
    }

    public LiveData<Word> getWordById(int id)
    {
        return appDatabase.getTodoItemDao().getWordById(id);
    }

    public void insertWordItem(Word word)
    {
        appExecutors.getDiskIO().execute(() ->
                appDatabase.getTodoItemDao().insertWord(word));
    }

    public void updateWordItem(Word word)
    {
        appExecutors.getDiskIO().execute(() ->
                appDatabase.getTodoItemDao().updateWord(word));
    }

    public void deleteWordItem(Word word)
    {
        appExecutors.getDiskIO().execute(() ->
                appDatabase.getTodoItemDao().deleteWord(word));
    }

    public void deleteWordList(List<Word> wordsToDelete)
    {
        appExecutors.getDiskIO().execute(()->
                appDatabase.getTodoItemDao().deleteWordList(wordsToDelete));
    }
}
