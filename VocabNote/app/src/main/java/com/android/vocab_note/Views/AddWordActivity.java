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

public class AddWordActivity extends AppCompatActivity
{
    public static final String EXTRA_WORD_ID = "extra_word_id";
    public static final String EXTRA_CATEGORY_ID = "extra_category_id";
    public static final String INSTANCE_WORD_ID = "instance_word_id";
    public static final String INSTANCE_CATEGORY_ID = "instance_category_id";
    public static final int DEFAULT_WORD_ID = -1;

    private EditText etWord;
    private EditText etMeaning;

    private int categoryId;

    private int extraWordId = DEFAULT_WORD_ID;

    private AddWordViewModel viewModel;

    private DataRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        repository = ((MyApplication) getApplication()).getRepository();

        etWord = findViewById(R.id.et_word);
        etMeaning = findViewById(R.id.et_meaning);

        if (savedInstanceState != null)
        {
            if (savedInstanceState.containsKey(INSTANCE_WORD_ID))
                extraWordId = savedInstanceState.getInt(INSTANCE_WORD_ID, DEFAULT_WORD_ID);

            if (savedInstanceState.containsKey(INSTANCE_CATEGORY_ID))
                categoryId = savedInstanceState.getInt(INSTANCE_CATEGORY_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_CATEGORY_ID))
            categoryId = intent.getIntExtra(EXTRA_CATEGORY_ID, 1);

        getExtraWord();


    }

    private void getExtraWord()
    {
        Intent intent = getIntent();

        if (extraWordId != DEFAULT_WORD_ID)
            return;

        if (intent == null)
            return;

        if (intent.hasExtra(EXTRA_WORD_ID))
        {
            extraWordId = intent.getIntExtra(EXTRA_WORD_ID, DEFAULT_WORD_ID);

            viewModel = new ViewModelProvider(
                    this, new AddWordViewModelFactory(repository, extraWordId))
                    .get(AddWordViewModel.class);

            final LiveData<Word> extraWord = viewModel.getExtraWord();

            extraWord.observe(this, word -> populateUI(word));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putInt(INSTANCE_WORD_ID, extraWordId);
        outState.putInt(INSTANCE_CATEGORY_ID, categoryId);
    }

    public void populateUI(Word word)
    {
        etWord.setText(word.getWord());
        etMeaning.setText(word.getMeaning());
    }

    private void saveWord()
    {
        Word word = new Word(categoryId, etWord.getText().toString(), etMeaning.getText().toString());

        if (extraWordId == DEFAULT_WORD_ID)
        {
            repository.insertWordItem(word);
        }
        else
        {
            word.setId(extraWordId);
            repository.updateWordItem(word);
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
