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
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Trailer>> listTrailersLV = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final String TAG = "MovieDetailViewModel";

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
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

    public LiveData<List<Trailer>> getListTrailersLV() {
        return listTrailersLV;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

}
