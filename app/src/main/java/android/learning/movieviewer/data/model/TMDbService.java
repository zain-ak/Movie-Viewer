package android.learning.movieviewer.data.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDbService {

    @GET("discover/movie?sort_by=popularity.desc")
    Call<MovieResponse> getPopularMovies(
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("page") int page
    );

    @GET("discover/movie?sort_by=popularity.asc")
    Call<MovieResponse> getLeastPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("discover/movie?sort_by=vote_average.desc")
    Call<MovieResponse> getBestRated(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("discover/movie?sort_by=revenue.desc")
    Call<MovieResponse> getHighestGrossing(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

}
