package android.learning.movieviewer.adapter;

import android.content.Context;
import android.learning.movieviewer.R;
import android.learning.movieviewer.data.model.Movie;
import android.learning.movieviewer.ui.main.MainActivity;
import android.learning.movieviewer.ui.main.MovieFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movies;

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
        // each data item is just a string in this case
        TextView title;
        TextView year;
        TextView rating;
        ImageView background;

        MovieViewHolder(View v, Context context) {
            super(v);
            title = v.findViewById(R.id.movieTitleTextView);
            year = v.findViewById(R.id.movieYearTextView);
            rating = v.findViewById(R.id.movieRatingTextView);
            background = v.findViewById(R.id.movieItemBgImageView);
        }

        void bind(Movie movie) {
            title.setText(movie.getName());
            if(movie.getReleaseDate().isEmpty()) year.setText(this.itemView.getContext().getString(R.string.to_be_decided));
            else year.setText(movie.getReleaseDate().substring(0,4));
            rating.setText(movie.getRating());
            Glide.with(itemView)
                    .load(IMAGE_BASE_URL + movie.getImage())
                    .placeholder(R.drawable.movie_item_background_placeholder)
                    .fallback(R.drawable.movie_item_background_placeholder)
                    .centerCrop()
                    .into(background);
        }
    }

    public MovieAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);
        this.context = parent.getContext();
        return new MovieViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        final Movie m = movies.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentJump(m);
            }
        });
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void appendMovies(List<Movie> moviesToAppend) {
        movies.addAll(moviesToAppend);
        notifyDataSetChanged();
    }

    private void fragmentJump(Movie m) {
        MovieFragment movieFragment = new MovieFragment();
        Bundle mBundle = new Bundle();
        mBundle.putParcelable("movie_item", m);
        movieFragment.setArguments(mBundle);
        switchContent(R.id.movieFragment, movieFragment);
    }

    private void switchContent(int id, MovieFragment movieFragment) {
        if (context == null) { return; }
        else if (context instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) context;
            Fragment frag = movieFragment;
            mainActivity.switchContent(id, frag);
        }
    }

}
