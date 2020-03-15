package com.android.vocab_note.Views;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.vocab_note.Constants;
import com.android.vocab_note.Model.Entity.Category;
import com.android.vocab_note.R;
import com.android.vocab_note.ViewModels.CategoryManagerViewModel;
import com.android.vocab_note.ViewModels.RepositoryViewModelFactory;
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
                new RepositoryViewModelFactory(requireActivity().getApplication()))
                .get(CategoryManagerViewModel.class);

        viewModel.getCategoryList().observe(getViewLifecycleOwner(), categories ->
                categoryAdapter.setCategories(categories));


        setUpRecycleView();

        return rootLayout;
    }

    /**
     * Setup the RecycleView of the list of categories
     */
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
                Category category = categoryAdapter.getCategories().get(removeCategoryPos);

                showDeleteConfirmDialog(category, removeCategoryPos);
            }
        }).attachToRecyclerView(getRecyclerView());

        //Divider for each item in the recycle view
        DividerItemDecoration decoration = new DividerItemDecoration(
                requireActivity().getApplicationContext(), VERTICAL);
        getRecyclerView().addItemDecoration(decoration);

        //Show rename dialog when user click a category
        categoryAdapter.setCategoryClickListener(oldCategory ->
         {
             if(oldCategory.getCategory().equals(Constants.CATEGORY_COMMON))
             {
                 Toast.makeText(requireActivity(),
                         "Cannot rename default category", Toast.LENGTH_SHORT).show();
                 return;
             }

             showRenameCategoryDialog(oldCategory);
         });
    }


    /** show a dialog of confirmation to the user when they delete a category
     * @param categoryToRemove The category to be removed
     */
    private void showDeleteConfirmDialog(Category categoryToRemove, int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        builder.setTitle("Delete " + categoryToRemove.getCategory());
        builder.setMessage("Are you sure want to delete " + categoryToRemove.getCategory() + "?");

        builder.setPositiveButton("OK", (dialog, which) ->
            viewModel.removeCategory(position));

        builder.setNegativeButton("Cancel", (dialog, which) ->
        {
            getAdapter().notifyItemChanged(position);
            dialog.cancel();
        });

        builder.create().show();
    }


    /** show the rename dialog for renaming a category
     * @param oldCategory the old category to be renamed
     */
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
                dialog.cancel());

        builder.create().show();
    }
}
