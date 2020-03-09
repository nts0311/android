package com.android.todolist.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao
{
    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getCategoryList();

    @Update
    void updateCategory(Category category);

    @Insert
    void insertCategory(Category category);

    @Delete
    void deleteCategory(Category category);
}