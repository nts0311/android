package com.android.todolist.Views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.todolist.Model.Entity.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>
{
    private List<Category> categories;

    public void setCategories(List<Category> categories)
    {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View rootLayout= LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);

        return new CategoryViewHolder(rootLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position)
    {
        holder.bind(categories.get(position));
    }

    @Override
    public int getItemCount()
    {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textView;

        public CategoryViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textView = itemView.findViewById(android.R.id.text1);
        }

        public void bind(Category category)
        {
            textView.setText(category.getCategory());
        }

        public String getCategory()
        {
            return textView.getText().toString();
        }
    }
}
