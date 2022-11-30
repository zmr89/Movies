package com.example.movies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class FavoriteMoviesViewModel extends AndroidViewModel {
    private final MovieDAO movieDAO;


    public FavoriteMoviesViewModel(@NonNull Application application) {
        super(application);
        movieDAO = MovieDatabase.getInstance(application).getMovieDao();
    }

    public LiveData<List<Movie>> getAllFavoriteMovies(){
        return movieDAO.getAllFavoriteMoviesDB();
    }



}
