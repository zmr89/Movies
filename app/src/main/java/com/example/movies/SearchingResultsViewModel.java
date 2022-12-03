package com.example.movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchingResultsViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Movie>> moviesListLD = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingLD = new MutableLiveData<>(false);
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final String TAG = "SearchingResultsVM";

    public SearchingResultsViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadSearchMovieResponse(int page, String movieNameSearch) {
        if (isLoadingLD.getValue()){
            return;
        }
        Disposable disposable = ApiServiceFactory.API_SERVICE.loadSearchMovieResponse(page, movieNameSearch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<MovieResponse, List<Movie>>() {
                    @Override
                    public List<Movie> apply(MovieResponse movieResponse) throws Throwable {
                        return movieResponse.getMovies();
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isLoadingLD.setValue(true);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isLoadingLD.setValue(false);
                    }
                })
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> listMoviesResponse) throws Throwable {
                        List<Movie> allMoviesList = moviesListLD.getValue();
                        if (allMoviesList == null) {
                            moviesListLD.setValue(listMoviesResponse);
                        } else {
                            allMoviesList.addAll(listMoviesResponse);
                            moviesListLD.setValue(allMoviesList);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public LiveData<List<Movie>> getMoviesListLD() {
        return moviesListLD;
    }

    public LiveData<Boolean> getIsLoadingLD() {
        return isLoadingLD;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
