package com.android.vocab_note;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.vocab_note.Model.Entity.Word;
import com.android.vocab_note.Utilities.NotificationUtil;

public class NotificationWorker extends Worker
{
    private DataRepository repository;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);

        repository = DataRepository.getInstance(getApplicationContext());
    }


    @NonNull
    @Override
    public Result doWork()
    {
        Word randomWord = repository.getRandomWord();

        if (randomWord != null)
        {
            NotificationUtil.showReminderNotification(getApplicationContext(), randomWord);
        }

        return Result.success();
    }
}
