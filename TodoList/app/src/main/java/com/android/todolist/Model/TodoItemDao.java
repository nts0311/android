package com.android.todolist.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoItemDao
{
    @Query("SELECT * FROM todo_items ORDER BY priority DESC")
    LiveData<List<TodoItem>> getTodoList();

    @Query("SELECT * FROM todo_items WHERE category=:category ORDER BY priority DESC")
    LiveData<List<TodoItem>> getTodoListByCategory(String category);

    @Update
    void updateTodoItem(TodoItem todoItem);

    @Insert
    void insertTodoItem(TodoItem todoItem);

    @Delete
    void deleteTodoItem(TodoItem todoItem);
}
