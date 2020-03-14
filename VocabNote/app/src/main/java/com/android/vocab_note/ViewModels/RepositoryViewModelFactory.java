package com.android.vocab_note.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.vocab_note.DataRepository;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * Class for creating ViewModel that has a DataRepository
 * using Java Reflection to reduce ViewModel's Factory class
 */
public class RepositoryViewModelFactory implements ViewModelProvider.Factory
{
    private static final String LOG_TAG = RepositoryViewModelFactory.class.getSimpleName();


    private DataRepository repository;

    public RepositoryViewModelFactory(Application application)
    {
        repository = DataRepository.getInstance(application.getApplicationContext());
    }


    /**
     * Factory method to create ViewModels that has a constructor with a DataRepository param
     * @param modelClass The ViewModel Class type
     * @param <T> the ViewModel class
     * @return the ViewModel
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass)
    {
        try
        {
            //get the default constructor that accept a DataRepository
            Constructor<T> constructor = modelClass.getDeclaredConstructor(DataRepository.class);

            return constructor.newInstance(repository);
        } catch (NoSuchMethodException e)
        {
            Log.e(LOG_TAG, "Cannot find constructor with " +
                    "DataRepository parameter of " + modelClass.getSimpleName());
        } catch (InstantiationException x)
        {
            Log.e(LOG_TAG, "Cannot instantiate class " + modelClass.getSimpleName());
        } catch (IllegalAccessException x)
        {
            x.printStackTrace();
        } catch (InvocationTargetException x)
        {
            x.printStackTrace();
        }

        return null;
    }
}

