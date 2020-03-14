package com.android.vocab_note.Model.Dao;

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

    @Query("SELECT * FROM words WHERE categoryId=:categoryId")
    LiveData<List<Word>> getWordListByCategory(int categoryId);

    @Query("SELECT * FROM words WHERE id=:id")
    LiveData<Word> getWordById(int id);

    @Query("UPDATE words SET categoryId=:newCategoryId WHERE categoryId=:oldCategoryId")
    void updateWordsCategory(int oldCategoryId, int newCategoryId);

    @Update
    void updateWord(Word word);

    @Insert
    void insertWord(Word word);

    @Delete
    void deleteWord(Word word);

    @Delete
    void deleteWordList(List<Word> words);
}
