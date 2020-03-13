package com.android.vocab_note;

import android.app.Application;

import com.android.vocab_note.Model.DataRepository;

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
