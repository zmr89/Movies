package com.example.movies;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase movieDatabaseInstance = null;
    private static final String DATABASE_NAME = "movie.db";

    public static MovieDatabase getInstance(Application application) {
        if (movieDatabaseInstance == null) {
            movieDatabaseInstance = Room.databaseBuilder(application, MovieDatabase.class, DATABASE_NAME).build();
        }
        return movieDatabaseInstance;
    }


    public abstract MovieDAO getMovieDao();
}
