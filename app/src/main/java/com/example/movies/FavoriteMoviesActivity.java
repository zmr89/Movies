package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class FavoriteMoviesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavorite;
    private MovieAdapter movieAdapter;
    private FavoriteMoviesViewModel favoriteMoviesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle("Избранные фильмы");

        movieAdapter = new MovieAdapter();
        recyclerViewFavorite = findViewById(R.id.recyclerViewFavorite);
        recyclerViewFavorite.setAdapter(movieAdapter);
        recyclerViewFavorite.setLayoutManager(new GridLayoutManager(this, 2));

        favoriteMoviesViewModel = new ViewModelProvider(this).get(FavoriteMoviesViewModel.class);

        favoriteMoviesViewModel.getAllFavoriteMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovieList(movies);
            }
        });

        movieAdapter.setOnClickMovieItemListener(new MovieAdapter.OnClickMovieItemListener() {
            @Override
            public void onClickMovieItem(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(FavoriteMoviesActivity.this, movie);
                startActivity(intent);
            }
        });


    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, FavoriteMoviesActivity.class);
        return  intent;
    }


}