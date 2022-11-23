package com.example.movies;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {

    private List<Trailer> trailerList = new ArrayList<>();
    private OnClickTrailerListener onClickTrailerListener;

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
//Не забываем notifyDataSetChanged() чтобы отобразить изменения в RecyclerView!!!!!
        notifyDataSetChanged();
    }

    public void setOnClickTrailerListener(OnClickTrailerListener onClickTrailerListener) {
        this.onClickTrailerListener = onClickTrailerListener;
    }

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailers_item, parent, false);
        return new TrailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder holder, int position) {
        Trailer trailer = trailerList.get(position);
        holder.textViewTrailers.setText(trailer.getName());

        holder.textViewTrailers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickTrailerListener != null) {
                    onClickTrailerListener.onClickTrailer(trailer);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public static class TrailersViewHolder extends RecyclerView.ViewHolder{
        private final TextView textViewTrailers;

        public TrailersViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTrailers = itemView.findViewById(R.id.textViewTrailerName);
        }
    }

    interface OnClickTrailerListener{
        void onClickTrailer(Trailer trailer);
    }

}
