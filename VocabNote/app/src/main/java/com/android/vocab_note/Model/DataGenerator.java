package com.android.vocab_note.Model;

import com.android.vocab_note.Model.Entity.Category;
import com.android.vocab_note.Model.Entity.Word;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator
{
    public static List<Category> generateCategories()
    {
        List<Category> result = new ArrayList<>();

        for (int i = 0; i < 5; i++)
        {
            result.add(new Category("Cate " + i));
        }

        return result;
    }

    public static List<Word> generateWords()
    {
        List<Word> result = new ArrayList<>();

        for (int i = 2; i <= 6; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                Word word = new Word(i, "word " + j + " cate " + (i-2), "meaning " + j + " cate " + (i-2));
                result.add(word);
            }
        }

        return result;
    }
}
