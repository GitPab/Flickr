package com.example.myapplication;

public class PostData {
    public String username;
    public String favorites;
    public String comments;
    public String title;
    public String imageUrl;
    public String avatarUrl;

    public PostData(String username, String favorites, String comments, String title, String imageUrl, String avatarUrl) {
        this.username = username;
        this.favorites = favorites;
        this.comments = comments;
        this.title = title;
        this.imageUrl = imageUrl;
        this.avatarUrl = avatarUrl.replace("http","https");
    }

    @Override
    public String toString() {
        return "PostData{" +
                "username='" + username + '\'' +
                ", favorites='" + favorites + '\'' +
                ", comments='" + comments + '\'' +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}

