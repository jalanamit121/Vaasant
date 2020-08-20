package com.winbee.vasantvalleyschool.Models;

public class homeItemModel {

    int image ;
    String title ;

    public homeItemModel() {
    }

    public homeItemModel(int image, String title) {
        this.image = image;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "homeItemModel{" +
                "image=" + image +
                ", title='" + title + '\'' +
                '}';
    }
}