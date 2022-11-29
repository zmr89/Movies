package com.example.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {

    @SerializedName("docs")
    private List<ReviewMovie> reviewMovieList;

    public ReviewResponse(List<ReviewMovie> reviewMovieList) {
        this.reviewMovieList = reviewMovieList;
    }

    public List<ReviewMovie> getReviewMovieList() {
        return reviewMovieList;
    }

    @Override
    public String toString() {
        return "ReviewResponse{" +
                "reviewMovieList=" + reviewMovieList +
                '}';
    }


}
