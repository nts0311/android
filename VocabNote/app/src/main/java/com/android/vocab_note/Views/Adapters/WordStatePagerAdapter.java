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

/**
 * StatePagerAdapter for the main ViewPager in the MainActivity
 */
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

    @Override
    public int getItemPosition(@NonNull Object object)
    {
        WordListFragment wordListFragment = (WordListFragment) object;

        int categoryId = wordListFragment.getCategoryId();

        for (int i = 0; i < categories.size(); i++)
        {
            if (categories.get(i).getId() == categoryId)
                return i;
        }

        //if the category has been deleted from the list, return position_none
        // so the ViewPager call destroyItem and remove the Fragment
        return POSITION_NONE;
    }
}
