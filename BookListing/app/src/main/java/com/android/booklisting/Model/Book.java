package com.android.booklisting.Model;

import android.graphics.Bitmap;

public class Book
{
    private String title;
    private String subTitle;
    private String authors;
    private String publisher;
    private String publishDate;
    private int pageCount;
    private String categories;
    private String thumbnailURL;
    private String previewURL;
    private String description;
    private Bitmap thumbnail;

    public Book(String title, String subTitle, String authors, String publisher, String publishDate, int pageCount, String categories, String thumbnailURL, String previewURL, String description)
    {
        this.title = title;
        this.subTitle = subTitle;
        this.authors = authors;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.pageCount = pageCount;
        this.categories = categories;
        this.thumbnailURL = thumbnailURL;
        this.previewURL = previewURL;
        this.description = description;
    }

    public String getTitle()
    {
        return title;
    }

    public String getSubTitle()
    {
        return subTitle;
    }

    public String getAuthors()
    {
        return authors;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public String getPublishDate()
    {
        return publishDate;
    }

    public int getPageCount()
    {
        return pageCount;
    }

    public String getCategories()
    {
        return categories;
    }

    public String getThumbnailURL()
    {
        return thumbnailURL;
    }

    public String getPreviewURL()
    {
        return previewURL;
    }

    public String getDescription()
    {
        return description;
    }

    public Bitmap getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail)
    {
        this.thumbnail = thumbnail;
    }
}
