package com.example.movies;

import android.graphics.drawable.Drawable;
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

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
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
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewPoster;
        private final TextView textViewRating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewRating = itemView.findViewById(R.id.textViewRating);
        }


    }
}
