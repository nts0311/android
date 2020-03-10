package com.android.todolist.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.android.todolist.Model.Category;
import com.android.todolist.Model.DataRepository;
import com.android.todolist.Model.TodoItem;
import com.android.todolist.MyApplication;

import java.util.List;

public class TodoListFragmentViewModel extends AndroidViewModel
{
    private static final String LOG_TAG = TodoListFragmentViewModel.class.getSimpleName();

    private DataRepository repository;
    private LiveData<List<TodoItem>> todoList;
    private LiveData<Category> currentCategory;

    public TodoListFragmentViewModel(@NonNull Application application)
    {
        super(application);

        Log.d(LOG_TAG, "Creating Fragment's view model");

        repository = ((MyApplication) application).getRepository();

        todoList = Transformations.switchMap(currentCategory, new Function<Category, LiveData<List<TodoItem>>>()
        {
            @Override
            public LiveData<List<TodoItem>> apply(Category input)
            {
                return repository.getTodoListByCategory(input.getCategory());
            }
        });
    }

    public void setCategory(String category)
    {
        currentCategory = repository.getCategoryByName(category);
    }

    public LiveData<List<TodoItem>> getTodoList()
    {
        return todoList;
    }
}
