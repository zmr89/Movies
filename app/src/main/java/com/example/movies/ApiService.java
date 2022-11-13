package com.example.movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface ApiService {

    @GET("movie?token=XYV3HPG-1SB4PV1-JB06YPA-P2PMZJ5&field=rating.kp&search=7-10&sortField=votes.kp&sortType=-1&limit=4")
    Single<MovieResponse> loadMovieResponse();
}
