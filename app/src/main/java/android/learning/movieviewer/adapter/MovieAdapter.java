package android.learning.movieviewer.adapter;

import android.content.Context;
import android.learning.movieviewer.R;
import android.learning.movieviewer.data.model.Movie;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final Context context;
    private List<Movie> movies;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
        // each data item is just a string in this case
        TextView title;
        TextView year;
        TextView rating;
        ImageView background;

        public MovieViewHolder(View v, Context context) {
            super(v);
            title = v.findViewById(R.id.movieTitleTextView);
            year = v.findViewById(R.id.movieYearTextView);
            rating = v.findViewById(R.id.movieRatingTextView);
            background = v.findViewById(R.id.movieItemBgImageView);
        }

        public void bind(Movie movie) {
            title.setText(movie.getName());
            if(movie.getReleaseDate().isEmpty()) {year.setText("TBD");}
            else year.setText(movie.getReleaseDate().substring(0,4));
            rating.setText(movie.getRating());
            Glide.with(itemView)
                    .load(IMAGE_BASE_URL + movie.getImage())
                    .placeholder(R.drawable.movie_item_background_placeholder)
                    .fallback(R.drawable.movie_item_background_placeholder)
                    .into(background);
        }
    }

    public MovieAdapter(List<Movie> movies, Context context) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

}
