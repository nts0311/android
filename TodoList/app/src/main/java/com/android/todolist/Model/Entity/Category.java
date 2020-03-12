package com.android.todolist.Model.Entity;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String category;

    public Category(int id, String category)
    {
        this.id = id;
        this.category = category;
    }

    @Ignore
    public Category(String category)
    {
        this.category = category;
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

    @Override
    public boolean equals(@Nullable Object obj)
    {
        if (!(obj instanceof Category))
            return false;

        return ((Category) obj).category.equals(this.category);
    }
}
