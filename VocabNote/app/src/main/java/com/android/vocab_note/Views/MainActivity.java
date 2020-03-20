package com.android.vocab_note.Views;

import android.app.SearchManager;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private DataRepository repo;
    private TabLayout categoryTabs;
    private ViewPager mainVP;
    private WordStatePagerAdapter wordStatePagerAdapter;
    private SearchView searchView;
    private SearchView.SearchAutoComplete searchAutoComplete;

    private int currentCategoryId;

    private List<Category> categories;

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

        repo.getWordList().observe(this , words ->
        {

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
            this.categories = categories;
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

        searchView= (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchAutoComplete= searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        List<String> suggestion=new ArrayList<>();
        suggestion.add("This");
        suggestion.add("is");
        suggestion.add("for");
        suggestion.add("testing");

        SearchManager searchManager=(SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.search_item, suggestion);
        searchAutoComplete.setAdapter(adapter);

        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(getApplicationContext(),""+id,Toast.LENGTH_SHORT).show();

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
            return true;
        }
        else if (id == R.id.item_category_manager)
        {
            startActivity(new Intent(this, CategoryManagerActivity.class));
        }
        else if (id == R.id.item_go_to_category)
        {
            showSelectCategoryDialog();
        }
        else if(id==R.id.item_word_test)
        {
            startActivity(new Intent(this, WordTestActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void showSelectCategoryDialog()
    {
        String[] cateStrArr = new String[categories.size()];

        for (int i = 0; i < categories.size(); i++)
        {
            cateStrArr[i] = categories.get(i).getCategory();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Go to category")
                .setItems(cateStrArr, (dialog, which) -> mainVP.setCurrentItem(which));

        builder.create().show();
    }
}
