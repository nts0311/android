package com.android.todolist;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors
{

    private static AppExecutors instance=null;

    public static AppExecutors getInstance()
    {
        if(instance==null)
        {
            synchronized (AppExecutors.class)
            {
                instance=new AppExecutors();
            }
        }

        return instance;
    }

    private Executor diskIO;

    private AppExecutors()
    {
        diskIO = Executors.newSingleThreadExecutor();
    }

    public Executor getDiskIO()
    {
        return diskIO;
    }
}
