package com.android.vocab_note.Views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.vocab_note.Model.Entity.Word;
import com.android.vocab_note.R;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.TodoItemViewHolder>
{
    private List<Word> words;

    private OnWordClickListener onWordClickListener;

    public WordAdapter()
    {
    }

    public List<Word> getWords()
    {
        return words;
    }

    public void setWords(List<Word> words)
    {
        this.words = words;
        notifyDataSetChanged();
    }

    public void setOnWordClickListener(OnWordClickListener onWordClickListener)
    {
        this.onWordClickListener = onWordClickListener;
    }

    @NonNull
    @Override
    public TodoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View todoItemLayout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word_layout, parent, false);

        return new TodoItemViewHolder(todoItemLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoItemViewHolder holder, int position)
    {
        holder.bindData(words.get(position));
    }

    @Override
    public int getItemCount()
    {
        if (words != null)
            return words.size();
        return 0;
    }



    public interface OnWordClickListener
    {
        void onWordClick(Word word);
    }

    class TodoItemViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvWord;
        private TextView tvMeaning;

        public TodoItemViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tvWord = itemView.findViewById(R.id.tv_word);
            tvMeaning = itemView.findViewById(R.id.tv_meaning);

            itemView.setOnClickListener(v ->
                    onWordClickListener.onWordClick(words.get(getAdapterPosition())));
        }

        public void bindData(Word word)
        {
            tvWord.setText(word.getWord());
            tvMeaning.setText(word.getMeaning());
        }


    }
}
