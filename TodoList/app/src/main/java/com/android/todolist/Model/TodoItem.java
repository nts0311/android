package com.android.todolist.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "todo_items")
public class TodoItem
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String category;
    private String detail;
    private Date createDate;
    private int priority;

    public TodoItem(int id, String category, String detail, Date createDate, int priority)
    {
        this.id = id;
        this.category = category;
        this.detail = detail;
        this.createDate = createDate;
        this.priority = priority;
    }

    @Ignore
    public TodoItem(String category, String detail, Date createDate, int priority)
    {
        this.category = category;
        this.detail = detail;
        this.createDate = createDate;
        this.priority = priority;
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

    public String getDetail()
    {
        return detail;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }
}
