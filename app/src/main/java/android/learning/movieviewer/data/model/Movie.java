package android.learning.movieviewer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("id") @Expose
    private String id;

    @SerializedName("original_title") @Expose
    private String name;

    @SerializedName("overview") @Expose
    private String description;

    @SerializedName("poster_path") @Expose
    private String posterImageURL;

    @SerializedName("imdb_id") @Expose
    private String imdbID;

    @SerializedName("release_date") @Expose
    private String releaseDate;

    @SerializedName("runtime") @Expose
    private String runtime;

    @SerializedName("vote_average") @Expose
    private String rating;


    public Movie(String id, String name, String description, String posterImageURL, String imdbID,
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

    public String getId() {
        return id;
    }

    public Movie (String name, String releaseDate) {
        this.name = name;
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return this.rating;
    }

    public String getImage() {
        return this.posterImageURL;
    }
}
