package android.learning.movieviewer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("id") @Expose
    private final String id;

    @SerializedName("original_title") @Expose
    private final String name;

    @SerializedName("overview") @Expose
    private final String description;

    @SerializedName("poster_path") @Expose
    private final String posterImageURL;

    @SerializedName("imdb_id") @Expose
    private final String imdbID;

    @SerializedName("release_date") @Expose
    private final String releaseDate;

    @SerializedName("runtime") @Expose
    private final String runtime;

    @SerializedName("vote_average") @Expose
    private final String rating;


    private Movie(String id, String name, String description, String posterImageURL, String imdbID,
                 String releaseDate, String runtime, String rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.posterImageURL = posterImageURL;
        this.imdbID = imdbID;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.rating = rating;
    }
}
