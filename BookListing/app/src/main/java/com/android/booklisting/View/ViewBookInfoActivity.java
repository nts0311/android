package com.android.booklisting.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.booklisting.Model.Book;
import com.android.booklisting.Model.DataRepository;
import com.android.booklisting.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewBookInfoActivity extends AppCompatActivity
{
    private DataRepository dataRepository = DataRepository.getInstance();
    private int bookIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bookIndex = getIntent().getIntExtra("book_index", 0);
        showInfos();

        TextView link = findViewById(R.id.txt_previewLink);
        link.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String text = ((TextView) v).getText().toString();

                if (text.contentEquals(""))
                    return;

                String link = text.split(": ")[1];

                Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(openBrowser);
            }
        });
    }

    private void showInfos()
    {
        Book b = dataRepository.getBook(bookIndex);

        if (b == null)
            return;

        ((ImageView) findViewById(R.id.img_thumbnail)).setImageBitmap(b.getThumbnail());

        setText((TextView) findViewById(R.id.txt_title), b.getTitle());
        setText((TextView) findViewById(R.id.txt_subTitle), b.getSubTitle());
        setText((TextView) findViewById(R.id.txt_authors), b.getAuthors());
        setText((TextView) findViewById(R.id.txt_publisher), b.getPublisher());
        setText((TextView) findViewById(R.id.txt_publishDate), b.getPublishDate());
        setText((TextView) findViewById(R.id.txt_pageCount), b.getPageCount() + "");
        setText((TextView) findViewById(R.id.txt_categories), b.getCategories());
        setText((TextView) findViewById(R.id.txt_previewLink), b.getPreviewURL());
        setText((TextView) findViewById(R.id.txt_description), b.getDescription());
    }

    private void setText(TextView textView, String text)
    {

        if (textView != null)
        {
            if (!text.contentEquals(""))
                textView.setText(textView.getText() + text);
            else
                textView.setText(textView.getText() + "Unknown");
        }

    }

}
