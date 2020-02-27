package com.android.booklisting.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils
{
    public static URL getURL(String url)
    {
        try
        {
            URL thumbURL = new URL(url);
            return thumbURL;

        } catch (MalformedURLException e)
        {
            Log.e("thumb_download", "URL not valid!");
            e.printStackTrace();
        }

        return null;
    }

    public static InputStream getResponseStream(String urlString)
    {
        try
        {
            URL requestURL = getURL(urlString);

            HttpURLConnection urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(15000);

            urlConnection.connect();

            InputStream result = urlConnection.getInputStream();

            return result;
        } catch (IOException e)
        {
            Log.e("getResponseStream","getResponseStream openConnection failed.");
            e.printStackTrace();
        }

        return null;
    }

    public static String readFromStream(InputStream inputStream)
    {
        StringBuilder builder = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try
        {
            String line = reader.readLine();

            while (line != null)
            {
                builder.append(line);
                line = reader.readLine();
            }
        } catch (IOException e)
        {
            Log.e("inpstream_reader", "Error reading from input stream.");
        }


        return builder.toString();
    }

    public static Bitmap getBitmapFromStream(InputStream inputStream)
    {
        if (inputStream == null)
            return null;

        return BitmapFactory.decodeStream(inputStream);
    }
}









