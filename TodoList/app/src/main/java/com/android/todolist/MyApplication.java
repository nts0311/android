package com.android.todolist;

import android.app.Application;

import com.android.todolist.Model.DataRepository;

public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    public DataRepository getRepository()
    {
        return DataRepository.getInstance(this.getApplicationContext());
    }
}
