package com.example.movies;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Videos {

    @SerializedName("trailers")
    private List<Trailer> listTrailers = new ArrayList<>();

    public List<Trailer> getListTrailers() {
        return listTrailers;
    }

    @Override
    public String toString() {
        return "Videos{" +
                "listTrailers=" + listTrailers +
                '}';
    }
}
