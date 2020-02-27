package com.android.booklisting.View;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.android.booklisting.AppContract;
import com.android.booklisting.BookAdapter;
import com.android.booklisting.Model.Book;
import com.android.booklisting.Presenter.MainPresenter;
import com.android.booklisting.Presenter.PresenterManager;
import com.android.booklisting.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AppContract.MainView,
                                                    SharedPreferences.OnSharedPreferenceChangeListener
{
    private ListView bookListView;
    private TextView emptyView;
    private ProgressBar progressBar;

    private BookAdapter bookAdapter;

    private ConnectivityManager connectivityManager;
    private NetworkCapabilities networkCapabilities;

    private AppContract.MainPresenter mainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bookListView = findViewById(R.id.lst_book_list);

        bookAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(bookAdapter);
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent viewBookInfo = new Intent(getApplicationContext(), ViewBookInfoActivity.class);
                viewBookInfo.putExtra("book_index", position);
                startActivity(viewBookInfo);
            }
        });

        emptyView = findViewById(R.id.empty_view);
        progressBar = findViewById(R.id.progressBar);

        bookListView.setEmptyView(emptyView);

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        mainPresenter = PresenterManager.getPresenter(this, 1);
        mainPresenter.restoreData();

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //set up searchview

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        if (searchView != null)
        {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                emptyView.setVisibility(View.INVISIBLE);

                networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

                if (networkCapabilities != null
                        && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
                    mainPresenter.querySearch(query);
                else
                {
                    emptyView.setText("No internet connection!");
                    emptyView.setVisibility(View.VISIBLE);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            startActivity(new Intent(this, PreferenceActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void displayBookList(List<Book> bookList)
    {
        progressBar.setVisibility(View.INVISIBLE);
        bookAdapter.clear();
        bookAdapter.addAll(bookList);
    }

    @Override
    public void showProgress()
    {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        if(key.contentEquals(getString(R.string.pref_result_limit_key)))
        {
            int value=Integer.parseInt(sharedPreferences.getString(key,"10"));

            mainPresenter.setResultLimit(value);
        }
        else if(key.contentEquals(getString(R.string.pref_sort_type_key)))
        {
            String sortType=sharedPreferences.getString(key,getString(R.string.sort_type_none));

            if(sortType.contentEquals(getString(R.string.sort_type_pub_date)))
            {
                mainPresenter.setSortType(MainPresenter.SortType.BY_PUBLISHED_DATE);
            }
            else if(sortType.contentEquals(getString(R.string.sort_type_pages)))
            {
                mainPresenter.setSortType(MainPresenter.SortType.BY_PAGE_COUNT);
            }
            else
                mainPresenter.setSortType(MainPresenter.SortType.NONE);
        }
    }

    @Override
    public void clearList()
    {
        bookAdapter.clear();
    }
}











