package com.example.myapplication.ui.search;

public class Groups {
    private int image;
    private String name;
    private String members;
    private String photos;

    public Groups(int image, String name, String members, String photos) {
        this.image = image;
        this.name = name;
        this.members = members;
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

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String address) {
        this.photos = photos;
    }
}
