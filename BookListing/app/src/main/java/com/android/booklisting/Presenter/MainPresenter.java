package com.android.booklisting.Presenter;

import android.util.Log;

import com.android.booklisting.AppContract;
import com.android.booklisting.Model.Book;
import com.android.booklisting.Model.DataRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainPresenter implements AppContract.MainPresenter
{
    public enum SortType
    {
        BY_PUBLISHED_DATE,
        BY_PAGE_COUNT,
        NONE
    }

    private AppContract.MainView mainView;
    private DataRepository dataRepository = DataRepository.getInstance();

    private int resultLimit = 10;

    private SortType sortType;

    public MainPresenter(AppContract.MainView mainView)
    {
        this.mainView = mainView;
        dataRepository.setMainPresenter(this);
    }

    @Override
    public void querySearch(String key)
    {
        String queryKey = getQueryString(key);
        dataRepository.fetchData(getRequestURL(queryKey));
    }

    @Override
    public void updateUI(List<Book> result)
    {
        if (sortType == SortType.BY_PUBLISHED_DATE)
            Collections.sort(result, new Comparator<Book>()
            {
                @Override
                public int compare(Book o1, Book o2)
                {
                    int year1 = getYear(o1);
                    int year2 = getYear(o2);

                    if (year1 > year2)
                        return 1;
                    else if (year1 == year2)
                        return 0;
                    else
                        return -1;
                }

                private int getYear(Book b)
                {
                    if (b.getPublishDate().contentEquals(""))
                        return 0;

                    return Integer.parseInt(b.getPublishDate().split("-")[0]);

                }
            });
        else if (sortType == SortType.BY_PAGE_COUNT)
            Collections.sort(result, new Comparator<Book>()
            {
                @Override
                public int compare(Book o1, Book o2)
                {
                    int page1 = o1.getPageCount();
                    int page2 = o2.getPageCount();

                    if (page1 > page2)
                        return 1;
                    else if (page1 == page2)
                        return 0;
                    else
                        return -1;
                }
            });

        mainView.displayBookList(result);
    }

    private String getRequestURL(String queryKey)
    {
        String urlHead = "https://www.googleapis.com/books/v1/volumes?q=";
        String urlTail = "&maxResults=";

        return urlHead + queryKey + urlTail + resultLimit;
    }

    private String getQueryString(String key)
    {
        String[] words = key.split(" ");

        StringBuilder resultBuilder = new StringBuilder();

        for (int i = 0; i < words.length; i++)
            resultBuilder.append(words[i].toLowerCase());

        return resultBuilder.toString();
    }

    @Override
    public void setView(AppContract.MainView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void restoreData()
    {
        List<Book> data = dataRepository.getData();

        if (data.size() > 0)
            updateUI(data);
    }

    @Override
    public void beforeMakeNetworkcall()
    {
        mainView.clearList();
        mainView.showProgress();
    }

    @Override
    public void setResultLimit(int limit)
    {
        if (limit > 0 && limit <= 40)
            this.resultLimit = limit;

        Log.e("r_limit", limit + "");
    }

    @Override
    public void setSortType(SortType sortType)
    {
        this.sortType = sortType;

        Log.e("s_type", sortType.toString());
    }
}
