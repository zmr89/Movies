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
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private int page = 1;
    private static final String TAG = "MainViewModel";
    private final MutableLiveData<List<Movie>> moviesLD = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingLD = new MutableLiveData<>(false);
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
        loadMovies();
    }

    public void loadMovies(){
        Boolean isLoading = isLoadingLD.getValue();
        if (isLoading != null && isLoading){
            return;
        }

        Disposable disposable = ApiServiceFactory.API_SERVICE.loadMovieResponse(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Throwable {
                        List<Movie> listAllMovies = moviesLD.getValue();
                        if (listAllMovies != null){
                            listAllMovies.addAll(movieResponse.getMovies());
                            moviesLD.setValue(listAllMovies);
                        } else {
                            moviesLD.setValue(movieResponse.getMovies());
                        }
                        Log.d("MainViewModel","page " + page);
                        page++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public LiveData<List<Movie>> getMoviesLD() {
        return moviesLD;
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
