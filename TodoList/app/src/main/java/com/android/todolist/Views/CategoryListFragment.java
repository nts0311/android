package com.android.todolist.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.android.todolist.ViewModels.CategoryManagerViewModel;
import com.android.todolist.ViewModels.CategoryManagerViewModelFactory;
import com.android.todolist.Views.Adapters.CategoryAdapter;

public class CategoryListFragment extends SimpleRecycleViewFragment
{
    private CategoryManagerViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootLayout = super.onCreateView(inflater, container, savedInstanceState);

        CategoryAdapter categoryAdapter = new CategoryAdapter();

        setAdapter(categoryAdapter);

        viewModel = new ViewModelProvider(requireActivity(),
                new CategoryManagerViewModelFactory(requireActivity().getApplication()))
                .get(CategoryManagerViewModel.class);

        viewModel.getCategoryList().observe(getViewLifecycleOwner(), categories ->
                categoryAdapter.setCategories(categories));

        return rootLayout;
    }
}
