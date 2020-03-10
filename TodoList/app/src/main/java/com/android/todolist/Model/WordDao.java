package com.android.todolist.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.todolist.Model.Entity.Word;

import java.util.List;

@Dao
public interface WordDao
{
    @Query("SELECT * FROM words")
    LiveData<List<Word>> getWordList();

    @Query("SELECT * FROM words WHERE category=:category")
    LiveData<List<Word>> getWordListByCategory(String category);

    @Update
    void updateWord(Word word);

    @Insert
    void insertWord(Word word);

    @Delete
    void deleteWord(Word word);
}
