package com.example.movies;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList = new ArrayList<>();
    private OnReachEndListener onReachEndListener;
    private OnClickMovieItemListener onClickMovieItemListener;

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public void setOnClickMovieItemListener(OnClickMovieItemListener onClickMovieItemListener) {
        this.onClickMovieItemListener = onClickMovieItemListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        Log.d("MovieAdapter", "onBindViewHolder position " + position);

        if (movie.getPoster() != null){
            Glide.with(holder.itemView).load(movie.getPoster().getUrl()).into(holder.imageViewPoster);
        } else {
            holder.imageViewPoster.setImageDrawable(ContextCompat
                    .getDrawable(holder.imageViewPoster.getContext(),
                            R.drawable.ic_baseline_local_movies_24));
        }

        int idBackground;
        if (movie.getRating().getKp() > 7) {
            idBackground = R.drawable.circle_green;
        } else if (movie.getRating().getKp() > 5) {
            idBackground = R.drawable.circle_orange;
        } else {
            idBackground = R.drawable.circle_red;
        }

        Drawable backgroundDrawable = ContextCompat.getDrawable(holder.itemView.getContext(), idBackground);
        holder.textViewRating.setBackground(backgroundDrawable);

        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String rating = decimalFormat.format(movie.getRating().getKp());
        holder.textViewRating.setText(rating);
        holder.textViewMovieName.setText(movie.getName());
        holder.textViewYear.setText(String.valueOf(movie.getYear()));

        if (position >= (movieList.size() - 10)){
            Log.d("MovieAdapter", "movieList.size()-10: " + (movieList.size() - 10));
            if (onReachEndListener != null) {
                onReachEndListener.onReachEnd();
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickMovieItemListener != null){
                    onClickMovieItemListener.onClickMovieItem(movie);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewPoster;
        private final TextView textViewRating;
        private final TextView textViewMovieName;
        private final TextView textViewYear;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewMovieName = itemView.findViewById(R.id.textViewMovieName);
            textViewYear = itemView.findViewById(R.id.textViewYear);
        }
    }

    interface OnReachEndListener {
        void onReachEnd();
    }

    interface OnClickMovieItemListener {
        void onClickMovieItem(Movie movie);
    }

}
