package com.android.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.booklisting.Model.Book;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book>
{
    public BookAdapter(@NonNull Context context, @NonNull List<Book> objects)
    {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Holder holder;

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);

            holder = new Holder();

            holder.thumbnail = convertView.findViewById(R.id.item_small_thumbnail);
            holder.title = convertView.findViewById(R.id.item_title);
            holder.subTitle = convertView.findViewById(R.id.item_sub_title);
            holder.authors = convertView.findViewById(R.id.item_authors);

            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }

        Book book = getItem(position);

        holder.thumbnail.setImageBitmap(book.getThumbnail());
        holder.title.setText(book.getTitle());
        holder.subTitle.setText(book.getSubTitle());
        holder.authors.setText(book.getAuthors());

        return convertView;
    }

    private class Holder
    {
        public ImageView thumbnail;
        public TextView title;
        public TextView subTitle;
        public TextView authors;
    }
}



























