package com.example.dell.t6tgal.model;

import android.widget.ImageView;

/**
 * Created by dell on 2017/1/4.
 */

public class News {

    private String title;
    private String author;
    private int Image;
    private long time;

    public News(String title, String author, int Image, int time) {
        this.title=title;
        this.author=author;
        this.Image=Image;
        this.time=time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
