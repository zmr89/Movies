package com.example.movies;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface MovieDAO {

    @Query("SELECT * FROM favorite_movies")
    LiveData<List<Movie>> getAllFavoriteMoviesDB();

    @Query("SELECT * FROM favorite_movies WHERE id = :movieId")
    LiveData<Movie> getFavoriteMovieDB(int movieId);

    @Query("DELETE FROM favorite_movies WHERE id = :movieId")
    Completable deleteMovieDB(int movieId);

    @Insert
    Completable insertMovieDB(Movie movie);
}
