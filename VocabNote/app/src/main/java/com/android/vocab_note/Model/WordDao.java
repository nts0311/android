package com.android.vocab_note.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.vocab_note.Model.Entity.Word;

import java.util.List;

@Dao
public interface WordDao
{
    @Query("SELECT * FROM words")
    LiveData<List<Word>> getWordList();

    @Query("SELECT * FROM words WHERE category=:category")
    LiveData<List<Word>> getWordListByCategory(String category);

    @Query("SELECT * FROM words WHERE id=:id")
    LiveData<Word> getWordById(int id);

    @Update
    void updateWord(Word word);

    @Insert
    void insertWord(Word word);

    @Delete
    void deleteWord(Word word);

    @Delete
    void deleteWordList(List<Word> words);
}
