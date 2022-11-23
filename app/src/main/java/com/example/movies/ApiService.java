package com.example.movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie?token=XYV3HPG-1SB4PV1-JB06YPA-P2PMZJ5&field=rating.kp&search=7-10&sortField=votes.kp&sortType=-1&limit=30")
    Single<MovieResponse> loadMovieResponse(@Query("page") int page);

    @GET("movie?token=XYV3HPG-1SB4PV1-JB06YPA-P2PMZJ5&field=id")
    Single<MovieResponseFromId> loadMovieResponseFromId(@Query("search") int id);

//    @GET("review?token=XYV3HPG-1SB4PV1-JB06YPA-P2PMZJ5&field=movieId&search=326&limit=10&sortField=reviewLikes&sortType=-1")
//


}
