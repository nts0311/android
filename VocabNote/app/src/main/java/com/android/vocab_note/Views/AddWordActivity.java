package com.android.vocab_note.Views;

import android.content.Intent;
import android.os.Bundle;

import com.android.vocab_note.DataRepository;
import com.android.vocab_note.Model.Entity.Word;
import com.android.vocab_note.MyApplication;
import com.android.vocab_note.R;
import com.android.vocab_note.ViewModels.AddWordViewModel;
import com.android.vocab_note.ViewModels.AddWordViewModelFactory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.IOError;
import java.io.IOException;

public class AddWordActivity extends AppCompatActivity
{
    public static final String EXTRA_WORD_ID = "extra_word_id";
    public static final String EXTRA_CATEGORY_ID = "extra_category_id";
    public static final String INSTANCE_WORD_ID = "instance_word_id";
    public static final String INSTANCE_CATEGORY_ID = "instance_category_id";
    public static final int DEFAULT_WORD_ID = -1;

    private EditText etWord;
    private EditText etMeaning;

    //current category we are in when start this activity
    private int categoryId;

    private int extraWordId = DEFAULT_WORD_ID;

    private AddWordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add a word");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        etWord = findViewById(R.id.et_word);
        etMeaning = findViewById(R.id.et_meaning);

        //restore the extra word's id
        if (savedInstanceState != null)
        {
            if (savedInstanceState.containsKey(INSTANCE_WORD_ID))
                extraWordId = savedInstanceState.getInt(INSTANCE_WORD_ID, DEFAULT_WORD_ID);

            if (savedInstanceState.containsKey(INSTANCE_CATEGORY_ID))
                categoryId = savedInstanceState.getInt(INSTANCE_CATEGORY_ID);
        }

        viewModel = new ViewModelProvider(
                this, new AddWordViewModelFactory(((MyApplication) getApplication()).getRepository()
                , extraWordId))
                .get(AddWordViewModel.class);


        //get the current category's id
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_CATEGORY_ID))
            categoryId = intent.getIntExtra(EXTRA_CATEGORY_ID, 0);

        getExtraWord();


    }

    /**
     * get the extra word if user are viewing the word
     */
    private void getExtraWord()
    {
        Intent intent = getIntent();
        if (intent == null)
            return;

        //if previously restore the word's id and UI, then return
        if (extraWordId != DEFAULT_WORD_ID)
            return;

        if (intent.hasExtra(EXTRA_WORD_ID))
        {
            extraWordId = intent.getIntExtra(EXTRA_WORD_ID, DEFAULT_WORD_ID);
            final LiveData<Word> extraWord = viewModel.getExtraWord(extraWordId);

            extraWord.observe(this, this::populateUI);

            getSupportActionBar().setTitle("Edit word");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putInt(INSTANCE_WORD_ID, extraWordId);
        outState.putInt(INSTANCE_CATEGORY_ID, categoryId);
    }

    /**
     * populate the UI with the given word
     *
     * @param word the word
     */
    public void populateUI(Word word)
    {
        if (word == null)
            return;

        etWord.setText(word.getWord());
        etMeaning.setText(word.getMeaning());
    }

    /**
     * Add a word or save modified word to the database
     */
    private void saveWord()
    {
        Word word = new Word(categoryId, etWord.getText().toString(), etMeaning.getText().toString());

        if (extraWordId == DEFAULT_WORD_ID)
        {
            viewModel.addWord(word);
        }
        else
        {
            word.setId(extraWordId);
            viewModel.updateWord(word);
        }

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_add_word, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.item_save_word)
            saveWord();

        return super.onOptionsItemSelected(item);
    }
}
