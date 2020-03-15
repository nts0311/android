package com.android.vocab_note.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.vocab_note.Constants;
import com.android.vocab_note.DataRepository;
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


    /** Remove a category, change all the words's category int it to Common
     * @param position the position of the category to remove
     */
    public void removeCategory(int position)
    {
        Category removedCategory = getCategory(position);

        //Cannot remove the default category
        if(removedCategory.getCategory().equals(Constants.CATEGORY_COMMON))
            return;

        repository.deleteCategory(removedCategory);

        //change the words in a deleted category to COMMON
        repository.changeWordsToDefaultCategory(removedCategory.getId());
    }


    /** Rename a category
     * @param oldCategory the old category
     * @param newCategoryStr the new category
     */
    public void renameCategory(Category oldCategory, String newCategoryStr)
    {
        Category newCategory = new Category(oldCategory.getId(), newCategoryStr);
        repository.updateWordCategory(newCategory);
    }
}
