package com.android.todolist;

import android.app.Application;

import com.android.todolist.Model.DataRepository;

public class MyApplication extends Application
{
    AppExecutors appExecutors;

    @Override
    public void onCreate()
    {
        super.onCreate();

        appExecutors=new AppExecutors();
    }

    public DataRepository getRepository()
    {
        return DataRepository.getInstance(this.getApplicationContext());
    }
}
