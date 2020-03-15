package com.android.vocab_note.Views.Adapters;

import android.util.SparseBooleanArray;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * This abstract class provides an adapter with the ability of
 * long-click to select multi item in action mode
 *
 * @param <VH> the ViewHolder type
 */
public abstract class SelectableAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>
{
    //The array contains all the selected items's index
    private SparseBooleanArray selectedItem;

    public SelectableAdapter()
    {
        selectedItem = new SparseBooleanArray();
    }


    /**
     * Return a List<Integer> of selected items's index
     *
     * @return the List<Integer> of selected items's index
     */
    public List<Integer> getSelectedItems()
    {
        List<Integer> items = new ArrayList<>(selectedItem.size());
        for (int i = 0; i < selectedItem.size(); i++)
        {
            items.add(selectedItem.keyAt(i));
        }

        return items;
    }


    /**
     * Check if an item is selected
     *
     * @param position the index of the item to be check
     * @return true if the item at position is selected
     */
    public boolean isSelected(int position)
    {
        return getSelectedItems().contains(position);
    }


    /**
     * Toggle selection of an item
     *
     * @param position the index of the item
     */
    public void toggleSelection(int position)
    {
        if (selectedItem.get(position, false))
        {
            selectedItem.delete(position);
        }
        else
        {
            selectedItem.put(position, true);
        }

        //enable/disable selected overlay of item's layout
        notifyItemChanged(position);
    }

    /**
     * Clear all selection
     */
    public void clearSelection()
    {
        List<Integer> selection = getSelectedItems();
        selectedItem.clear();

        for (int i = 0; i < selection.size(); i++)
        {
            notifyItemChanged(i);
        }
    }


    /**
     * @return the number of items were selected
     */
    public int getSelectedItemCount()
    {
        return selectedItem.size();
    }
}
