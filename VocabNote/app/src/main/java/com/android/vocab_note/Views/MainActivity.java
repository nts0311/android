package com.android.vocab_note.Views;

import android.content.Intent;
import android.os.Bundle;

import com.android.vocab_note.DataRepository;
import com.android.vocab_note.Model.Entity.Category;
import com.android.vocab_note.Model.Entity.Word;
import com.android.vocab_note.MyApplication;
import com.android.vocab_note.R;
import com.android.vocab_note.Views.Adapters.WordStatePagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private DataRepository repo;
    private TabLayout categoryTabs;
    private ViewPager mainVP;
    private WordStatePagerAdapter wordStatePagerAdapter;

    private int currentCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        repo = ((MyApplication) getApplication()).getRepository();

        //set up the add word button
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(view ->
        {
            Intent addWordIntent = new Intent(MainActivity.this, AddWordActivity.class);
            addWordIntent.putExtra(AddWordActivity.EXTRA_CATEGORY_ID, currentCategoryId);
            startActivity(addWordIntent);
        });

        setUpViewPagerAndTabLayout();

        //TODO change LiveData to SingleLiveEvent
        //set the current category to the first category
        LiveData<List<Category>> categoryList = repo.getCategoryList();
        categoryList.observe(this, new Observer<List<Category>>()
        {
            @Override
            public void onChanged(List<Category> categories)
            {
                if (categories.size() != 0)
                    currentCategoryId = categories.get(0).getId();
                categoryList.removeObserver(this);
            }
        });
    }

    /**
     * Set up the main View Pager with the Tab layout of this activity
     */
    private void setUpViewPagerAndTabLayout()
    {
        mainVP = findViewById(R.id.main_viewpager);
        categoryTabs = findViewById(R.id.tab_viewpager);

        wordStatePagerAdapter = new WordStatePagerAdapter(getSupportFragmentManager());

        repo.getCategoryList().observe(this, categories ->
        {
            wordStatePagerAdapter.setCategories(categories);
        });

        mainVP.setAdapter(wordStatePagerAdapter);
        mainVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                //set the currentCategoryId to match the one on the View Pager
                currentCategoryId = wordStatePagerAdapter.getCategories().get(position).getId();
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

        categoryTabs.setupWithViewPager(mainVP);
        categoryTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }
        else if (id == R.id.item_category_manager)
        {
            startActivity(new Intent(this, CategoryManagerActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
