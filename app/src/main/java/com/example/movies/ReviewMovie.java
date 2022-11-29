package com.example.movies;

import com.google.gson.annotations.SerializedName;

public class ReviewMovie {

    @SerializedName("author")
    private String author;

    @SerializedName("review")
    private String review;

    @SerializedName("type")
    private String type;

    public ReviewMovie(String author, String review, String type) {
        this.author = author;
        this.review = review;
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public String getReview() {
        return review;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ReviewMovie{" +
                "author='" + author + '\'' +
                ", review='" + review + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
