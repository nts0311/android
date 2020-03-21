package com.android.vocab_note.Model.Entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "words")
public class Word
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int categoryId;
    private String word;
    private String meaning;

    public Word(int id, int categoryId, String word, String meaning)
    {
        this.id = id;
        this.categoryId = categoryId;
        this.word = word;
        this.meaning = meaning;
    }

    @Ignore
    public Word(int categoryId, String word, String meaning)
    {
        this.categoryId = categoryId;
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

    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
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

    @Override
    public boolean equals(@Nullable Object obj)
    {
        if (!(obj instanceof Word))
            return false;

        return (getId() == ((Word) obj).getId());
    }

    @NonNull
    @Override
    public String toString()
    {
        return word + " - " + meaning;
    }
}
