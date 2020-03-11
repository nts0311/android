package com.android.todolist.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.todolist.Model.DataRepository;
import com.android.todolist.Model.Entity.Category;

import java.util.List;

public class CategoryManagerViewModel extends ViewModel
{
    private LiveData<List<Category>> categoryList;

    public CategoryManagerViewModel(DataRepository repository)
    {
        categoryList=repository.getCategoryList();
    }

    public LiveData<List<Category>> getCategoryList()
    {
        return categoryList;
    }
}
