package com.android.todolist.Model;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter
{
    @TypeConverter
    public static Date toDate(long timeStamp)
    {
        return new Date(timeStamp);
    }

    @TypeConverter
    public static long toTimeStamp(Date date)
    {
        return date.getTime();
    }
}
