package android.learning.movieviewer.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;

public class Movie implements Parcelable {

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

    public Movie(Parcel parcel) {
        id = parcel.readString();
        name  = parcel.readString();
        description = parcel.readString();
        posterImageURL = parcel.readString();
        imdbID = parcel.readString();
        releaseDate = parcel.readString();
        runtime = parcel.readString();
        rating = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(posterImageURL);
        parcel.writeString(imdbID);
        parcel.writeString(releaseDate);
        parcel.writeString(runtime);
        parcel.writeString(rating);
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

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
