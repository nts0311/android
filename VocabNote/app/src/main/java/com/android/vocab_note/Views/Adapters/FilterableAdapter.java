package com.android.vocab_note.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.vocab_note.Model.Entity.Word;
import com.android.vocab_note.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import java.util.List;


/**
 * THREAD-UNSAFE
 * An adapter class with custom filter for search-autocomplete, since the default
 * ArrayAdapter cannot filter substring in the middle of the word
 * haven't check carefully but it seem kinda work for search auto-complete,
 * so don't use for any purpose except mention above
 */
public class FilterableAdapter<T> extends ArrayAdapter<T>
{
    private CustomFilter filter;
    private List<T> filteredResult = new ArrayList<>();
    private List<T> dataList = new ArrayList<>();

    public FilterableAdapter(@NonNull Context context, @NonNull List<T> objects)
    {
        super(context, R.layout.search_item, new ArrayList<>());
        dataList.addAll(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        ViewHolder viewHolder;

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.search_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(android.R.id.text1);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(filteredResult.get(position).toString());
        return convertView;

    }

    @Override
    public int getCount()
    {
        if (filteredResult == null)
            return 0;

        return filteredResult.size();
    }

    @Override
    public void add(@Nullable T object)
    {
        dataList.add(object);
    }

    @Override
    public void addAll(@NonNull Collection<? extends T> collection)
    {
        dataList.addAll(collection);
    }

    @Override
    public void addAll(T... items)
    {
        dataList.addAll(Arrays.asList(items));
    }

    @Override
    public void remove(@Nullable T object)
    {
        dataList.remove(object);
    }

    @Override
    public void clear()
    {
        dataList.clear();
    }

    @Nullable
    @Override
    public T getItem(int position)
    {
        return filteredResult.get(position);
    }

    @Override
    public int getPosition(@Nullable T item)
    {
        return filteredResult.indexOf(item);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public void resetData(@NonNull Collection<? extends T> collection)
    {
        dataList.clear();
        addAll(collection);
        filteredResult.clear();
    }

    @NonNull
    @Override
    public Filter getFilter()
    {
        if (filter == null)
            filter = new CustomFilter();

        return filter;
    }

    private static class ViewHolder
    {
        TextView textView;
    }

    private class CustomFilter extends Filter
    {
        @Override
        protected synchronized FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults filterResults = new FilterResults();
            filterResults.values = new ArrayList<>();
            filterResults.count = 0;

            if (constraint == null)
                return filterResults;


            String filterString = constraint.toString().toLowerCase();
            List<T> result = new ArrayList<>();

            if (filterString.length() > 0)
            {
                for (int i = 0; i < dataList.size(); i++)
                {
                    String item = dataList.get(i).toString();

                    if (item.toLowerCase().contains(filterString))
                    {
                        result.add(dataList.get(i));
                    }
                }

                filterResults.count = result.size();
                filterResults.values = result;
            }
            else
            {
                filterResults.values = 0;
                filterResults.values = new ArrayList<>();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            filteredResult = (List<T>) results.values;
            notifyDataSetChanged();
        }
    }
}
