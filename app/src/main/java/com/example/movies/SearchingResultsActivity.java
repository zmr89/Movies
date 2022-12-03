package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

public class SearchingResultsActivity extends AppCompatActivity {

    private SearchingResultsViewModel searchingResultsViewModel;
    private int page = 1;
    private String movieName;
    private static final String TAG = "SearchingResultsA";
    private static final String TAG_INTENT = "movieName";
    private MovieAdapter movieAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_results);

        ProgressBar progressBar = findViewById(R.id.progressBarLoadingSearch);
        RecyclerView recyclerViewSearching = findViewById(R.id.recyclerViewSearching);
        movieAdapter = new MovieAdapter();
        recyclerViewSearching.setAdapter(movieAdapter);
        recyclerViewSearching.setLayoutManager(new GridLayoutManager(this, 2));

        Intent intent = getIntent();
        movieName = intent.getStringExtra(TAG_INTENT);

        searchingResultsViewModel = new ViewModelProvider(this).get(SearchingResultsViewModel.class);
        if (movieName != null) {
            searchingResultsViewModel.loadSearchMovieResponse(page, movieName);
        }
        searchingResultsViewModel.getMoviesListLD().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovieList(movies);
                Log.d(TAG, movies.toString());
            }
        });

        movieAdapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                page++;
                searchingResultsViewModel.loadSearchMovieResponse(page, movieName);
            }
        });

        movieAdapter.setOnClickMovieItemListener(new MovieAdapter.OnClickMovieItemListener() {
            @Override
            public void onClickMovieItem(Movie movie) {
                Movie movie1 = movie;
                Intent intent = MovieDetailActivity.newIntent(SearchingResultsActivity.this, movie);
                startActivity(intent);
            }
        });

        searchingResultsViewModel.getIsLoadingLD().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading){
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    public static Intent newIntent(Context context, String movieName) {
        Intent intent = new Intent(context, SearchingResultsActivity.class);
        intent.putExtra(TAG_INTENT, movieName);
        return  intent;
    }


}