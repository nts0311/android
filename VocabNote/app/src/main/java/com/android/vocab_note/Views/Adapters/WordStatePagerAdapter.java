package com.android.vocab_note.Views.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.android.vocab_note.Model.Entity.Category;
import com.android.vocab_note.Views.WordListFragment;

import java.util.ArrayList;
import java.util.List;

public class WordStatePagerAdapter extends FragmentStatePagerAdapter
{
    private List<Category> categories = new ArrayList<>();

    public WordStatePagerAdapter(@NonNull FragmentManager fm)
    {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public List<Category> getCategories()
    {
        return categories;
    }

    public void setCategories(List<Category> categories)
    {
        this.categories = categories;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        int cate = categories.get(position).getId();
        return WordListFragment.newInstance(cate);
    }

    @Override
    public int getCount()
    {
        return categories.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return categories.get(position).getCategory();
    }
}
