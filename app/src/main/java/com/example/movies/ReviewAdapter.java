package com.example.movies;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<ReviewMovie> reviewList = new ArrayList<>();
    private static final String TYPE_POSITIVE = "Позитивный";
    private static final String TYPE_NEGATIVE = "Негативный";

    public void setReviewList(List<ReviewMovie> reviewList) {
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        ReviewMovie reviewMovie = reviewList.get(position);
        holder.textViewAuthorReview.setText(reviewMovie.getAuthor());
        holder.textViewReview.setText(reviewMovie.getReview());

        String reviewType = reviewMovie.getType();
        int colorResId = android.R.color.holo_orange_light;;
        if (reviewType != null) {
            switch (reviewType){
                case TYPE_POSITIVE : colorResId = android.R.color.holo_green_light;
                    break;
                case TYPE_NEGATIVE : colorResId = android.R.color.holo_red_light;
                    break;
            }
        }
        int color = ContextCompat.getColor(holder.itemView.getContext(), colorResId);
        holder.linearLayoutReviewItem.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder{
        private final LinearLayout linearLayoutReviewItem;
        private final TextView textViewAuthorReview;
        private final TextView textViewReview;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayoutReviewItem = itemView.findViewById(R.id.linearLayoutReviewItem);
            textViewAuthorReview = itemView.findViewById(R.id.textViewAuthorReview);
            textViewReview = itemView.findViewById(R.id.textViewReview);
        }
    }

}
