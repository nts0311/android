package com.android.todolist.Views.Adapters;

import android.util.SparseBooleanArray;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class SelectableAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>
{
    private SparseBooleanArray selectedItem;

    public SelectableAdapter()
    {
        selectedItem=new SparseBooleanArray();
    }

    public List<Integer> getSelectedItems()
    {
        List<Integer> items=new ArrayList<>(selectedItem.size());
        for(int i=0;i<selectedItem.size();i++)
        {
            items.add(selectedItem.keyAt(i));
        }

        return items;
    }

    public boolean isSelected(int position)
    {
        return getSelectedItems().contains(position);
    }

    public void toggleSelection(int position)
    {
        if(selectedItem.get(position, false))
        {
            selectedItem.delete(position);
        }
        else
        {
            selectedItem.put(position, true);
        }

        notifyItemChanged(position);
    }

    public void clearSelection()
    {
        List<Integer> selection=getSelectedItems();
        selectedItem.clear();

        for(int i=0;i<selection.size();i++)
        {
            notifyItemChanged(i);
        }
    }

    public int getSelectedItemCount()
    {
        return selectedItem.size();
    }
}
