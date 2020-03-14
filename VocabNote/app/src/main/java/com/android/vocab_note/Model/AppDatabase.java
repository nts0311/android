package com.android.vocab_note.Model;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.android.vocab_note.AppExecutors;
import com.android.vocab_note.Constants;
import com.android.vocab_note.Model.Dao.CategoryDao;
import com.android.vocab_note.Model.Dao.WordDao;
import com.android.vocab_note.Model.Entity.Category;
import com.android.vocab_note.Model.Entity.Word;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {Category.class, Word.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase
{
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final String DB_NAME = "word_db";

    private static AppDatabase instance = null;

    public static AppDatabase getInstance(Context appContext)
    {
        if (instance == null)
        {
            synchronized (AppDatabase.class)
            {
                Log.d(LOG_TAG, "Building db...");
                instance = Room.inMemoryDatabaseBuilder(appContext, AppDatabase.class)
                        .addCallback(new Callback()
                        {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db)
                            {
                                super.onCreate(db);

                                //Add the default category to the database, "Common"
                                Executor executor = AppExecutors.getInstance().getDiskIO();

                                executor.execute(() ->
                                {
                                    AppDatabase appDatabase = AppDatabase.getInstance(appContext);
                                    appDatabase.getCategoryDao()
                                            .insertCategory(new Category(Constants.CATEGORY_COMMON));
                                });
                            }
                        })
                        .build();
            }
        }

        Log.d(LOG_TAG, "Returning AppDB instance");
        return instance;
    }

    public abstract CategoryDao getCategoryDao();

    public abstract WordDao getWordDao();
}
