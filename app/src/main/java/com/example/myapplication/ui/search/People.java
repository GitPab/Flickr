package com.example.myapplication.ui.search;

import com.flickr4java.flickr.people.User;

public class People {
    private int image;
    public String avatar;

    public User getUser() {
        return user;
    }

    public void setUserv(User user) {
        this.user = user;
        this.avatar = user.getSecureBuddyIconUrl();
    }

    private User user;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;

    }

    protected String userid;
    private String name;
    private String photos;

    public People(int image, String name, String photos) {
        this.image = image;
        this.name = name;
        this.photos = photos;

    }

    public int getImage(){
        return image;
    }

    public void setImage(int image){
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String address) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "People{" +
                "\n image " + image +
                "\n avatar " + this.avatar+
                "\n Description " + user.getDescription()+
                '}';
    }
}
