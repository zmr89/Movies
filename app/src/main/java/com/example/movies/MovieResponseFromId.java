package com.example.movies;

import com.google.gson.annotations.SerializedName;

public class MovieResponseFromId {

    @SerializedName("videos")
    private Videos videos;

    public Videos getVideos() {
        return videos;
    }

    @Override
    public String toString() {
        return "MovieResponseFromId{" +
                "videos=" + videos +
                '}';
    }

}
