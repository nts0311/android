package com.android.vocab_note.Model.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "words")
public class Word
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String category;
    private String word;
    private String meaning;

    public Word(int id, String category, String word, String meaning)
    {
        this.id = id;
        this.category = category;
        this.word = word;
        this.meaning = meaning;
    }

    @Ignore
    public Word(String category, String word, String meaning)
    {
        this.category = category;
        this.word = word;
        this.meaning = meaning;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getMeaning()
    {
        return meaning;
    }

    public void setMeaning(String meaning)
    {
        this.meaning = meaning;
    }

    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }
}
