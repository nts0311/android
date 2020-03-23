package com.android.vocab_note.Utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.android.vocab_note.Model.Entity.Word;
import com.android.vocab_note.R;
import com.android.vocab_note.Views.MainActivity;

public class NotificationUtil
{
    private static final String NOTIFICATION_CHANNEL_ID = "vocab_note_noti_channel";
    private static final int MAIN_ACTIVITY_INTENT_REQUEST_CODE = 69;//noice
    private static final int REMINDER_NOTIFICATION_ID = 420;

    public static void showReminderNotification(Context context, Word word)
    {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        createNotificationChannel(context, notificationManager);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(word.getWord())
                .setContentText(word.getMeaning())
                .setSmallIcon(R.drawable.ic_word_add_black_24dp)
                .setContentIntent(getContentIntent(context))
                .setAutoCancel(true);

        notificationManager.notify(REMINDER_NOTIFICATION_ID, builder.build());

    }

    public static void createNotificationChannel(Context context, NotificationManager notificationManager)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.noti_channel_name), NotificationManager.IMPORTANCE_HIGH);

            channel.enableLights(true);

            notificationManager.createNotificationChannel(channel);
        }
    }

    private static PendingIntent getContentIntent(Context context)
    {
        Intent openMainActivity = new Intent(context, MainActivity.class);

        return PendingIntent.getActivity(context, MAIN_ACTIVITY_INTENT_REQUEST_CODE,
                openMainActivity, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
