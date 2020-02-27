package com.android.booklisting.Presenter;

import com.android.booklisting.AppContract;
import com.android.booklisting.Presenter.MainPresenter;

import java.util.HashMap;
import java.util.Map;

public class PresenterManager
{
    private static Map<Integer, AppContract.MainPresenter> presenterMap = new HashMap<>();

    private PresenterManager()
    {

    }

    public static AppContract.MainPresenter getPresenter(AppContract.MainView mainView, int presenterID)
    {
        AppContract.MainPresenter presenter;

        if (!presenterMap.containsKey(presenterID))
        {
            presenter = new MainPresenter(mainView);
            presenterMap.put(presenterID, presenter);
        }
        else
        {
            presenter = presenterMap.get(presenterID);
            presenter.setView(mainView);
        }
        return presenter;
    }
}
