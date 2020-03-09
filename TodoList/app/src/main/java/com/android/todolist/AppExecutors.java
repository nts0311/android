package com.android.todolist;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors
{
    private Executor diskIO;

    public AppExecutors()
    {
        diskIO = Executors.newSingleThreadExecutor();
    }

    public Executor getDiskIO()
    {
        return diskIO;
    }
}
