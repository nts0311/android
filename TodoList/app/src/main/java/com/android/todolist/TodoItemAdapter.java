package com.android.todolist;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.todolist.Model.TodoItem;

import java.util.List;

public class TodoItemAdapter extends RecyclerView.Adapter<TodoItemAdapter.TodoItemViewHolder>
{
    private Context context;
    private List<TodoItem> todoItems;

    public TodoItemAdapter(Context context, List<TodoItem> todoItems)
    {
        this.context = context;
        this.todoItems = todoItems;
    }

    public List<TodoItem> getTodoItems()
    {
        return todoItems;
    }

    public void setTodoItems(List<TodoItem> todoItems)
    {
        this.todoItems = todoItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TodoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View todoItemLayout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo_layout, parent, false);

        return new TodoItemViewHolder(todoItemLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoItemViewHolder holder, int position)
    {
        holder.bindData(todoItems.get(position));
    }

    @Override
    public int getItemCount()
    {
        return todoItems.size();
    }

    public void onCleanUp()
    {
        context = null;
    }

    private int getPriorityColor(int priority)
    {
        int result = 0;
        switch (priority)
        {
            case 1:
                result = ContextCompat.getColor(context, R.color.materialYellow);
                break;

            case 2:
                result = ContextCompat.getColor(context, R.color.materialOrange);
                break;

            case 3:
                result = ContextCompat.getColor(context, R.color.materialRed);
                break;
        }
        return result;
    }

    class TodoItemViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvDetail;
        private TextView tvDate;
        private TextView tvPriority;

        public TodoItemViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tvDetail = itemView.findViewById(R.id.tv_detail);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvPriority = itemView.findViewById(R.id.tv_priority_circle);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                }
            });
        }

        public void bindData(TodoItem todoItem)
        {
            tvDetail.setText(todoItem.getDetail());
            tvDate.setText(todoItem.getCreateDate().toString());
            tvPriority.setText(todoItem.getPriority());

            int priorityColor = getPriorityColor(todoItem.getPriority());

            if (priorityColor != 0)
            {
                GradientDrawable priorityCircle = (GradientDrawable) tvPriority.getBackground();
                priorityCircle.setColor(priorityColor);
            }
        }
    }
}
