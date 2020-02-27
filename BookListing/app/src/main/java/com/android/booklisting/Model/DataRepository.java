package com.android.booklisting.Model;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.android.booklisting.AppContract;
import com.android.booklisting.Utils.JSONUtils;
import com.android.booklisting.Utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class DataRepository
{
    //SINGLETON
    private static DataRepository instance = null;

    private DataRepository()
    {

    }

    public static DataRepository getInstance()
    {
        if (instance == null)
            instance = new DataRepository();

        return instance;
    }


    private List<Book> data = new ArrayList<>();
    private AppContract.MainPresenter mainPresenter;

    public void setMainPresenter(AppContract.MainPresenter mainPresenter)
    {
        this.mainPresenter = mainPresenter;
    }

    public List<Book> fetchData(String requestUrl)
    {
        new FetchDataTask().execute(requestUrl);

        return data;
    }

    public Book getBook(int index)
    {
        if (index >= 0 && index < data.size())
            return data.get(index);

        return null;
    }

    public List<Book> getData()
    {
        return data;
    }

    private class FetchDataTask extends AsyncTask<String, Void, List<Book>>
    {
        @Override
        protected void onPreExecute()
        {
            mainPresenter.beforeMakeNetworkcall();
        }

        @Override
        protected void onPostExecute(List<Book> bookList)
        {
            mainPresenter.updateUI(bookList);
            data = bookList;
        }

        @Override
        protected List<Book> doInBackground(String... strings)
        {
            List<Book> result;
            //get JSON response
            String responseJson = NetworkUtils.readFromStream(NetworkUtils.getResponseStream(strings[0]));
            result = JSONUtils.parseBookList(responseJson);

            //get book thumbnails
            for (Book b : result)
            {
                String thumbURL = b.getThumbnailURL();

                if (thumbURL != "" || thumbURL == null)
                {
                    Bitmap thumb = NetworkUtils.getBitmapFromStream(NetworkUtils.getResponseStream(thumbURL));
                    b.setThumbnail(thumb);
                }
            }

            return result;
        }
    }
}











