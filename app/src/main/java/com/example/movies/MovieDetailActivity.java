package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String TAG_MOVIE = "movie";
    private static final String TAG = "MovieDetailActivity";
    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewDescription;
    private RecyclerView recyclerViewTrailer;
    private RecyclerView recyclerViewReview;
    private TrailersAdapter trailersAdapter;
    private ReviewAdapter reviewAdapter;
    private MovieDetailViewModel movieDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_datail);

        initViews();

        movieDetailViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);

        Movie movie = (Movie) getIntent().getSerializableExtra(TAG_MOVIE);
        Glide.with(this).load(movie.getPoster().getUrl()).into(imageViewPoster);
        textViewTitle.setText(movie.getName());
        textViewYear.setText(String.valueOf(movie.getYear()));
        textViewDescription.setText(movie.getDescription());

        trailersAdapter = new TrailersAdapter();
        recyclerViewTrailer.setAdapter(trailersAdapter);

        movieDetailViewModel.loadMovieResponseFromId(movie.getId());
        movieDetailViewModel.getListTrailersLV().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                trailersAdapter.setTrailerList(trailers);
            }
        });

        trailersAdapter.setOnClickTrailerListener(new TrailersAdapter.OnClickTrailerListener() {
            @Override
            public void onClickTrailer(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });

        reviewAdapter = new ReviewAdapter();
        recyclerViewReview.setAdapter(reviewAdapter);

        movieDetailViewModel.loadReviewResponse(movie.getId());
        movieDetailViewModel.getListReviewMoviesLV().observe(this, new Observer<List<ReviewMovie>>() {
            @Override
            public void onChanged(List<ReviewMovie> reviewMovies) {
                reviewAdapter.setReviewList(reviewMovies);
            }
        });
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(TAG_MOVIE, movie);
        return intent;
    }

    private void initViews() {
        imageViewPoster = findViewById(R.id.imageViewPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewYear = findViewById(R.id.textViewYear);
        textViewDescription = findViewById(R.id.textViewDescription);
        recyclerViewTrailer = findViewById(R.id.recyclerViewTrailer);
        recyclerViewReview = findViewById(R.id.recyclerViewReview);
    }
}