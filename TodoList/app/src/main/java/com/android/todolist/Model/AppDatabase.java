package com.android.todolist.Model;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Category.class, TodoItem.class}, version = 1, exportSchema = false)
@TypeConverters(value = {DateConverter.class})
public abstract class AppDatabase extends RoomDatabase
{
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final String DB_NAME = "todo_db";

    private static AppDatabase instance = null;

    public static AppDatabase getInstance(Context appContext)
    {
        if (instance == null)
        {
            synchronized (AppDatabase.class)
            {
                Log.d(LOG_TAG, "Building db...");
                instance = Room.databaseBuilder(appContext, AppDatabase.class, DB_NAME)
                        .build();
            }
        }

        Log.d(LOG_TAG, "Returning AppDB instance");
        return instance;
    }

    public abstract CategoryDao getCategoryDao();
    public abstract TodoItemDao getTodoItemDao();
}
