package com.android.todolist.Views;

import android.content.Intent;
import android.os.Bundle;

import com.android.todolist.Model.DataRepository;
import com.android.todolist.Model.Entity.Category;
import com.android.todolist.Model.Entity.Word;
import com.android.todolist.MyApplication;
import com.android.todolist.R;
import com.android.todolist.Views.Adapters.WordStatePagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private DataRepository repo;
    private TabLayout categoryTabs;
    private ViewPager mainVP;
    private WordStatePagerAdapter wordStatePagerAdapter;

    private String currentCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        repo = ((MyApplication) getApplication()).getRepository();

        generateData();

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(view ->
        {
            Intent addWordIntent = new Intent(MainActivity.this, AddWordActivity.class);
            addWordIntent.putExtra(AddWordActivity.EXTRA_CATEGORY, currentCategory);
            startActivity(addWordIntent);
        });

        setUpViewPagerAndTabLayout();

        LiveData<List<Category>> categoryList = repo.getCategoryList();
        categoryList.observe(this, new Observer<List<Category>>()
        {
            @Override
            public void onChanged(List<Category> categories)
            {
                currentCategory = categories.get(0).getCategory();
                categoryList.removeObserver(this);
            }
        });
    }

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
                currentCategory = wordStatePagerAdapter.getPageTitle(position).toString();
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

        categoryTabs.setupWithViewPager(mainVP);
        categoryTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void generateData()
    {
        String cate = "Finance";

        repo.insertCategory(new Category(cate));
        repo.insertWordItem(new Word(cate, "debt", "borrow someone money"));

        cate = "Finance1";

        repo.insertCategory(new Category(cate));
        repo.insertWordItem(new Word(cate, "debt1", "borrow someone money1"));
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
