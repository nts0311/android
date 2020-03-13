package com.android.vocab_note.Views;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.vocab_note.ViewModels.CategoryManagerViewModel;
import com.android.vocab_note.ViewModels.CategoryManagerViewModelFactory;
import com.android.vocab_note.Views.Adapters.CategoryAdapter;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

public class CategoryListFragment extends SimpleRecycleViewFragment
{
    private CategoryManagerViewModel viewModel;

    private AlertDialog deleteConfirmDialog;

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


        /*Swipe left to delete a category*/
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT)
        {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                final int removeCategoryPos = viewHolder.getAdapterPosition();
                String category = viewModel.getCategory(removeCategoryPos).getCategory();

                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

                builder.setTitle("Delete " + category);
                builder.setMessage("Are you sure want to delete " + category + "?");

                builder.setPositiveButton("OK", (dialog, which) ->
                {
                    viewModel.removeCategory(removeCategoryPos);
                });

                builder.setNegativeButton("Cancel", (dialog, which) ->
                {
                    getAdapter().notifyItemChanged(removeCategoryPos);
                    deleteConfirmDialog.cancel();
                });

                deleteConfirmDialog = builder.create();

                deleteConfirmDialog.show();
            }
        }).attachToRecyclerView(getRecyclerView());

        DividerItemDecoration decoration = new DividerItemDecoration(
                requireActivity().getApplicationContext(), VERTICAL);
        getRecyclerView().addItemDecoration(decoration);


        return rootLayout;
    }
}
