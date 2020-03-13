package com.android.vocab_note.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.vocab_note.Model.DataRepository;
import com.android.vocab_note.Model.Entity.Category;

import java.util.List;

public class CategoryManagerViewModel extends ViewModel
{
    private LiveData<List<Category>> categoryList;
    private DataRepository repository;

    public CategoryManagerViewModel(DataRepository repository)
    {
        this.repository = repository;
        categoryList = repository.getCategoryList();
    }

    public LiveData<List<Category>> getCategoryList()
    {
        return categoryList;
    }

    public Category getCategory(int position)
    {
        List<Category> categories = repository.getCategoryList().getValue();
        return categories.get(position);
    }

    public void removeCategory(int position)
    {
        Category removedCategory = getCategory(position);
        repository.deleteCategory(removedCategory);
    }
}
