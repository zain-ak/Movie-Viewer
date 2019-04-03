package android.learning.movieviewer.data.model;

public class Movie {
    private final String id;
    private final String name;
    private final String description;
    private final String posterImageURL;
    private final String imdbID;
    private final String releaseDate;
    private final String runtime;
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
