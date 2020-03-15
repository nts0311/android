package com.android.vocab_note.Views;

import android.os.Bundle;

import com.android.vocab_note.DataRepository;
import com.android.vocab_note.Model.Entity.Category;
import com.android.vocab_note.MyApplication;
import com.android.vocab_note.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CategoryManagerActivity extends AppCompatActivity
{
    private AlertDialog addCategoryDialog = null;
    private DataRepository dataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_manager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataRepository = ((MyApplication) getApplication()).getRepository();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_category_manager, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.item_add_category)
        {
            if (addCategoryDialog == null)
                createAddCategoryDialog();

            addCategoryDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void createAddCategoryDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Add a category");

        LayoutInflater inflater = getLayoutInflater();

        View menuRoot = inflater.inflate(R.layout.add_category_dialog, null);
        EditText cateName = menuRoot.findViewById(R.id.et_add_category);

        builder.setView(menuRoot);

        builder.setPositiveButton(R.string.ok, (dialog, which) ->
        {
            Category newCategory = new Category(cateName.getText().toString());

            if (dataRepository.getCategoryList().getValue().contains(newCategory))
            {
                Toast.makeText(CategoryManagerActivity.this,
                        newCategory.getCategory() + " already existed!", Toast.LENGTH_SHORT).show();
                return;
            }

            dataRepository.insertCategory(newCategory);
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel());


        addCategoryDialog = builder.create();
    }
}
