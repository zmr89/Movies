package com.example.movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Trailer>> listTrailersLV = new MutableLiveData<>();
    private final MutableLiveData<List<ReviewMovie>> listReviewMoviesLV = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MovieDAO movieDAO;
    private static final String TAG = "MovieDetailViewModel";


    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        movieDAO = MovieDatabase.getInstance(application).getMovieDao();
    }

    public void loadMovieResponseFromId(int id){
        Disposable disposable = ApiServiceFactory.API_SERVICE.loadMovieResponseFromId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<MovieResponseFromId, List<Trailer>>() {
                    @Override
                    public List<Trailer> apply(MovieResponseFromId movieResponseFromId) throws Throwable {
                        return movieResponseFromId.getVideos().getListTrailers();
                    }
                })
                .subscribe(new Consumer<List<Trailer>>() {
            @Override
            public void accept(List<Trailer> trailerList) throws Throwable {
                listTrailersLV.setValue(trailerList);

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Throwable {
                Log.d(TAG, throwable.getMessage());
            }
        });
        compositeDisposable.add(disposable);
    }

    public void loadReviewResponse(int id){
        Disposable disposable = ApiServiceFactory.API_SERVICE.loadReviewResponse(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ReviewResponse, List<ReviewMovie>>() {
                    @Override
                    public List<ReviewMovie> apply(ReviewResponse reviewResponse) throws Throwable {
                        return reviewResponse.getReviewMovieList();
                    }
                })
                .subscribe(new Consumer<List<ReviewMovie>>() {
                    @Override
                    public void accept(List<ReviewMovie> reviewMovieList) throws Throwable {
                        listReviewMoviesLV.setValue(reviewMovieList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public LiveData<Movie> getFavoriteMovie(int movieId) {
        return movieDAO.getFavoriteMovieDB(movieId);
    }

    public void insertMovieDB(Movie movie) {
        Disposable disposable = movieDAO.insertMovieDB(movie).subscribeOn(Schedulers.io()).subscribe(new Action() {
            @Override
            public void run() throws Throwable {
                Log.d(TAG, "insertMovieDB() successful");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Throwable {
                Log.d(TAG, throwable.getMessage());
            }
        });
        compositeDisposable.add(disposable);
    }

    public void deleteMovie (int movieId) {
        Disposable disposable = movieDAO.deleteMovieDB(movieId).subscribeOn(Schedulers.io()).subscribe(new Action() {
            @Override
            public void run() throws Throwable {
                Log.d(TAG, "insertMovieDB() successful");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Throwable {
                Log.d(TAG, throwable.getMessage());
            }
        });
        compositeDisposable.add(disposable);
    }

    public LiveData<List<Trailer>> getListTrailersLV() {
        return listTrailersLV;
    }

    public LiveData<List<ReviewMovie>> getListReviewMoviesLV() {
        return listReviewMoviesLV;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

}
