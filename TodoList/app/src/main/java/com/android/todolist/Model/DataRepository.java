package com.android.todolist.Model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.android.todolist.AppExecutors;
import com.android.todolist.Constants;

import java.util.List;

public class DataRepository
{
    private static DataRepository instance = null;

    private AppDatabase appDatabase;
    private AppExecutors appExecutors;

    private LiveData<List<Category>> categoryList;
    private LiveData<List<TodoItem>> todoList;

    private DataRepository(Context appContext)
    {
        appDatabase = AppDatabase.getInstance(appContext);
        appExecutors = AppExecutors.getInstance();

        categoryList = appDatabase.getCategoryDao().getCategoryList();
        todoList = appDatabase.getTodoItemDao().getTodoList();
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

    public LiveData<List<TodoItem>> getTodoList()
    {
        return todoList;
    }

    public LiveData<List<TodoItem>> getTodoListByCategory(String category)
    {
        if(category.contentEquals(Constants.ALL_CATEGORIES))
            return todoList;
        else
            return appDatabase.getTodoItemDao().getTodoListByCategory(category);
    }

    public void insertTodoItem(TodoItem todoItem)
    {
        appExecutors.getDiskIO().execute(() ->
                appDatabase.getTodoItemDao().insertTodoItem(todoItem));
    }

    public void updateTodoItem(TodoItem todoItem)
    {
        appExecutors.getDiskIO().execute(() ->
                appDatabase.getTodoItemDao().updateTodoItem(todoItem));
    }

    public void deleteTodoItem(TodoItem todoItem)
    {
        appExecutors.getDiskIO().execute(() ->
                appDatabase.getTodoItemDao().deleteTodoItem(todoItem));
    }

}
