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

public class WordAdapter2 extends SelectableAdapter<WordAdapter2.WordViewHolder>
{

    private List<Word> words;

    //click-listener
    private OnWordClickListener onWordClickListener;
    private OnnWordLongClickListener onnWordLongClickListener;

    public List<Word> getWords()
    {
        return words;
    }

    public void setWords(List<Word> words)
    {
        this.words = words;
        notifyDataSetChanged();
    }

    public void setOnWordClickListener(WordAdapter2.OnWordClickListener onWordClickListener)
    {
        this.onWordClickListener = onWordClickListener;
    }

    public void setOnnWordLongClickListener(OnnWordLongClickListener onnWordLongClickListener)
    {
        this.onnWordLongClickListener = onnWordLongClickListener;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View wordLayout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word_layout, parent, false);

        return new WordViewHolder(wordLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position)
    {
        holder.bindData(words.get(position));

        //toggle the selected overlay
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
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
        void onWordClick(Word word, int position);
    }

    public interface OnnWordLongClickListener
    {
        void onWordLongClick(Word word, int position);
    }

    class WordViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvWord;
        public TextView tvMeaning;
        public View selectedOverlay;

        public WordViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tvWord = itemView.findViewById(R.id.tv_word);
            tvMeaning = itemView.findViewById(R.id.tv_meaning);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);

            itemView.setOnClickListener(v ->
            {
                if (onWordClickListener != null)
                    onWordClickListener.onWordClick(words.get(getAdapterPosition()), getAdapterPosition());
            });

            itemView.setOnLongClickListener(v ->
            {
                if (onnWordLongClickListener != null)
                {
                    onnWordLongClickListener.onWordLongClick(
                            words.get(getAdapterPosition()), getAdapterPosition());
                    return true;
                }

                return false;
            });
        }

        public void bindData(Word word)
        {
            tvWord.setText(word.getWord());
            tvMeaning.setText(word.getMeaning());
        }
    }
}
