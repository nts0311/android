package com.android.booklisting.Utils;

import android.util.Log;

import com.android.booklisting.Model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONUtils
{
    private static final String BOOK_ARRAY = "items";
    private static final String BOOK_INFO = "volumeInfo";
    private static final String TITLE = "title";
    private static final String SUB_TITLE = "subtitle";
    private static final String AUTHORS_ARRAY = "authors";
    private static final String PUBLISHER = "publisher";
    private static final String PUBLISH_DATE = "publishedDate";
    private static final String DESCRIPTION = "description";
    private static final String PAGE_COUNT = "pageCount";
    private static final String CATEGORIES_ARRAY = "categories";
    private static final String THUMBNAIL_ARRAY = "imageLinks";
    private static final String THUMBNAIL = "smallThumbnail";
    private static final String PREVIEW_LINK = "previewLink";

    public static List<Book> parseBookList(String json)
    {
        ArrayList<Book> result = new ArrayList<>();

        try
        {
            JSONObject root = new JSONObject(json);
            JSONArray booksArray = root.getJSONArray(BOOK_ARRAY);

            for (int i = 0; i < booksArray.length(); i++)
            {
                JSONObject bookInfo = booksArray.getJSONObject(i).getJSONObject(BOOK_INFO);

                //get all book information
                //get all book information
                String title = bookInfo.getString(TITLE);

                String subTitle = "";

                if (bookInfo.has(SUB_TITLE))
                    subTitle = bookInfo.getString(SUB_TITLE);

                String authors = "";
                if (bookInfo.has(AUTHORS_ARRAY))
                    authors = jsonArrayToString(bookInfo.getJSONArray(AUTHORS_ARRAY));

                String publisher = "";
                if (bookInfo.has(PUBLISHER))
                    publisher = bookInfo.getString(PUBLISHER);

                String publishDate = "";
                if (bookInfo.has(PUBLISH_DATE))
                    publishDate = bookInfo.getString(PUBLISH_DATE);

                int pageCount = 0;
                if (bookInfo.has(PAGE_COUNT))
                    pageCount = bookInfo.getInt(PAGE_COUNT);

                String categories = "";
                if (bookInfo.has(CATEGORIES_ARRAY))
                    categories = jsonArrayToString(bookInfo.getJSONArray(CATEGORIES_ARRAY));

                String thumbnailURL = "";
                if (bookInfo.has(THUMBNAIL_ARRAY))
                    thumbnailURL = bookInfo.getJSONObject(THUMBNAIL_ARRAY).getString(THUMBNAIL);

                String previewURL = "";
                if (bookInfo.has(PREVIEW_LINK))
                    previewURL = bookInfo.getString(PREVIEW_LINK);

                String description = "";
                if (bookInfo.has(DESCRIPTION))
                    description = bookInfo.getString(DESCRIPTION);


                Book b = new Book(title, subTitle, authors,
                        publisher, publishDate, pageCount, categories, thumbnailURL, previewURL, description);

                result.add(b);
            }


        } catch (JSONException e)
        {
            Log.e("json_parse", "Error parsing JSON.");
            e.printStackTrace();
        }

        return result;
    }

    private static String jsonArrayToString(JSONArray jsonArray)
    {
        try
        {
            StringBuilder result = new StringBuilder();

            for (int j = 0; j < jsonArray.length(); j++)
            {
                result.append(jsonArray.getString(j));

                if (j != jsonArray.length() - 1)
                    result.append(", ");
            }

            return result.toString();
        } catch (JSONException e)
        {
            Log.e("json_parse", "Cannot convert JSONArray to String.");
        }

        return "";
    }


}













