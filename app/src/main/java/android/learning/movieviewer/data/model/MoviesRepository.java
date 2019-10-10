package android.learning.movieviewer.data.model;

import android.content.Context;
import android.learning.movieviewer.R;
import android.learning.movieviewer.util.App;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesRepository {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";

    private static MoviesRepository repository;

    private TMDbService api;

    /* private constructor to ensure only one instance is created */
    private MoviesRepository(TMDbService api) {
        this.api = api;
    }

    public static MoviesRepository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new MoviesRepository(retrofit.create(TMDbService.class));
        }

        return repository;
    }

    public void getMovies(final OnGetMoviesCallback callback) {
        api.getBestRated("eecdd7e4d2f0d8a98f7b97e561b0eb1c", LANGUAGE, 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getResults() != null) {
                                callback.onSuccess(moviesResponse.getResults());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        callback.onError();
                    }



        });
    }
}
