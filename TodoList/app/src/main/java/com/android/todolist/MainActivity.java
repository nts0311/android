package com.android.todolist;

import android.content.Intent;
import android.os.Bundle;

import com.android.todolist.Model.DataRepository;
import com.android.todolist.Model.Entity.Category;
import com.android.todolist.Model.Entity.Word;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
{

    private DataRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String cate="Finance";

        repo=((MyApplication) getApplication()).getRepository();

        repo.insertCategory(new Category(cate));
        repo.insertWordItem(new Word(cate,"debt","borrow someone money"));

        WordListFragment wordListFragment=WordListFragment.newInstance(cate);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.test_frag, wordListFragment)
                .commit();

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent addWordIntent=new Intent(MainActivity.this, AddWordActivity.class);
                addWordIntent.putExtra(AddWordActivity.EXTRA_CATEGORY, cate);
                startActivity(addWordIntent);
            }
        });
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

        return super.onOptionsItemSelected(item);
    }
}
