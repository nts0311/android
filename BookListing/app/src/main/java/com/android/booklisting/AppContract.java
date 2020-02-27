package com.android.booklisting;

import com.android.booklisting.Model.Book;

import java.util.List;

public interface AppContract
{
    interface MainView
    {
        void displayBookList(List<Book> bookList);
        void showProgress();
        void clearList();
    }

    interface MainPresenter
    {
        void querySearch(String key);
        void updateUI(List<Book> result);
        void beforeMakeNetworkcall();
        void setView(AppContract.MainView mainView);
        void restoreData();
        void setResultLimit(int limit);
        void setSortType(com.android.booklisting.Presenter.MainPresenter.SortType sortType);
    }
}
