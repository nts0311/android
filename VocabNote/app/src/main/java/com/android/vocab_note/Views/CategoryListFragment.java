package com.android.vocab_note.Views;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.vocab_note.Model.Entity.Category;
import com.android.vocab_note.R;
import com.android.vocab_note.ViewModels.CategoryManagerViewModel;
import com.android.vocab_note.ViewModels.CategoryManagerViewModelFactory;
import com.android.vocab_note.Views.Adapters.CategoryAdapter;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

public class CategoryListFragment extends SimpleRecycleViewFragment
{
    private CategoryManagerViewModel viewModel;
    private CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootLayout = super.onCreateView(inflater, container, savedInstanceState);

        categoryAdapter = new CategoryAdapter();

        setAdapter(categoryAdapter);

        viewModel = new ViewModelProvider(requireActivity(),
                new CategoryManagerViewModelFactory(requireActivity().getApplication()))
                .get(CategoryManagerViewModel.class);

        viewModel.getCategoryList().observe(getViewLifecycleOwner(), categories ->
                categoryAdapter.setCategories(categories));


        setUpRecycleView();

        return rootLayout;
    }

    private void setUpRecycleView()
    {
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
                String category = categoryAdapter.getCategories().get(removeCategoryPos).getCategory();

                showDeleteConfirmDialog(category, removeCategoryPos);
            }
        }).attachToRecyclerView(getRecyclerView());

        //Divider for each item in the recycle view
        DividerItemDecoration decoration = new DividerItemDecoration(
                requireActivity().getApplicationContext(), VERTICAL);
        getRecyclerView().addItemDecoration(decoration);

        categoryAdapter.setCategoryClickListener(this::showRenameCategoryDialog);
    }

    private void showDeleteConfirmDialog(String category, int removeCategoryPos)
    {
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
            dialog.cancel();
        });

        builder.create().show();
    }

    private void showRenameCategoryDialog(Category oldCategory)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        View rootDialogLayout = layoutInflater.inflate(R.layout.add_category_dialog, null);
        EditText etCategory = rootDialogLayout.findViewById(R.id.et_add_category);
        etCategory.setText(oldCategory.getCategory());

        builder.setView(rootDialogLayout);
        builder.setTitle("Rename category");
        builder.setMessage("Rename " + oldCategory.getCategory() + " to: ");

        builder.setPositiveButton("OK", (dialog, which) ->
        {
            String newCategoryStr = etCategory.getText().toString();
            viewModel.renameCategory(oldCategory, newCategoryStr);
        });

        builder.setNegativeButton("Cancel", (dialog, which) ->
        {
            dialog.cancel();
        });

        builder.create().show();
    }
}
