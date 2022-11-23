package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class MovieDetailActivity extends AppCompatActivity {

    private static final String TAG_MOVIE = "movie";
    private static final String TAG = "MovieDetailActivity";

    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewDescription;
    private RecyclerView recyclerView;

    private MovieDetailViewModel movieDetailViewModel;
    private TrailersAdapter trailersAdapter;


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

        movieDetailViewModel.loadMovieResponseFromId(movie.getId());
        movieDetailViewModel.getListTrailersLV().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                trailersAdapter.setTrailerList(trailers);
                Log.d(TAG,  trailers.toString());
            }
        });


        recyclerView.setAdapter(trailersAdapter);
//LayoutManager для recyclerView установил в разметке activity_movie_datail
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        trailersAdapter.setOnClickTrailerListener(new TrailersAdapter.OnClickTrailerListener() {
            @Override
            public void onClickTrailer(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
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
        recyclerView = findViewById(R.id.recyclerViewDetail);
    }
}