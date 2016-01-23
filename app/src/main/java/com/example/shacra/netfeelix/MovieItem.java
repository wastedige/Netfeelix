package com.example.shacra.netfeelix;

/**
 * Created by shacra on 1/21/2016.
 */
public class MovieItem {
    String id;
    String imageurl;
    String title;

    public MovieItem(String id, String imageurl, String title) {
        this.id = id;
        this.imageurl = imageurl;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
