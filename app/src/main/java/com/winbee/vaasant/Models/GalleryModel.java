package com.winbee.vaasant.Models;

public class GalleryModel {


    String imageurl, title;

    public GalleryModel() {
    }

    public GalleryModel(String imageurl, String title) {
        this.imageurl = imageurl;
        this.title = title;
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

    @Override
    public String toString() {
        return "GalleryModel{" +
                "imageurl='" + imageurl + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

