package com.android.vocab_note.Model.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.vocab_note.Model.Entity.Category;

import java.util.List;

@Dao
public interface CategoryDao
{
    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getCategoryList();

    @Query("SELECT * FROM categories WHERE category=:category")
    Category getCategoryByName(String category);

    @Query("SELECT * FROM categories WHERE id=:id")
    Category getCategoryBiId(int id);

    @Update
    void updateCategory(Category category);

    @Insert
    void insertCategory(Category category);

    @Insert
    void insertCategory(List<Category> categoryList);

    @Delete
    void deleteCategory(Category category);
}
