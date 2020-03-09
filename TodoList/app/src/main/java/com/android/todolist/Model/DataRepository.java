package com.android.todolist.Model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DataRepository
{
    private static DataRepository instance = null;

    private AppDatabase appDatabase;

    private DataRepository(Context appContext)
    {
        appDatabase = AppDatabase.getInstance(appContext);
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
        return appDatabase.getCategoryDao().getCategoryList();
    }


}
