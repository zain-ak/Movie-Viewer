package android.learning.movieviewer.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.learning.movieviewer.R;
import android.learning.movieviewer.adapter.MovieAdapter;
import android.learning.movieviewer.data.model.Movie;
import android.learning.movieviewer.data.model.MoviesRepository;
import android.learning.movieviewer.data.model.OnGetMoviesCallback;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFloatingActionButton = null;
    private FloatingActionButton mRatedFAB = null;
    private FloatingActionButton mPopularFAB = null;
    private FloatingActionButton mGrossingFAB = null;
    private LinearLayout ratedLinear = null;
    private LinearLayout popularLinear = null;
    private LinearLayout grossingLinear = null;
    private Boolean isFABOpen = true;

    private MovieAdapter mAdapter = null;
    private RecyclerView movieList = null;
    private MoviesRepository moviesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieList = findViewById(R.id.movieListHome);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_sort_by);
        mRatedFAB = (FloatingActionButton) findViewById(R.id.fab_highest_rated);
        mPopularFAB = (FloatingActionButton) findViewById(R.id.fab_most_popular);
        mGrossingFAB = (FloatingActionButton) findViewById(R.id.fab_highest_grossing);
        ratedLinear = (LinearLayout) findViewById(R.id.linear_rated);
        popularLinear = (LinearLayout) findViewById(R.id.linear_popular);
        grossingLinear = (LinearLayout) findViewById(R.id.linear_grossing);

        movieList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && mFloatingActionButton.getVisibility() == View.VISIBLE) {
                    AlphaAnimation animation1 = new AlphaAnimation(1, 0);
                    animation1.setDuration(250);
                    animation1.setStartOffset(0);
                    animation1.setFillAfter(true);
                    mFloatingActionButton.setAnimation(animation1);
                    mFloatingActionButton.setVisibility(View.INVISIBLE);
                } else if (dy < 0 && mFloatingActionButton.getVisibility() != View.VISIBLE) {
                    AlphaAnimation animation1 = new AlphaAnimation(0, 1);
                    animation1.setDuration(250);
                    animation1.setStartOffset(0);
                    animation1.setFillAfter(true);
                    mFloatingActionButton.setAnimation(animation1);
                    mFloatingActionButton.setVisibility(View.VISIBLE);
                }
            }
        });

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    Log.d("eh", "eh");
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        moviesRepository = MoviesRepository.getInstance();

        //loadMovies();

    }

    private void closeFABMenu() {
        isFABOpen=false;
        AlphaAnimation animation1 = new AlphaAnimation(1, 0);
        animation1.setDuration(250);
        animation1.setStartOffset(0);
        animation1.setFillAfter(true);
        ratedLinear.setAnimation(animation1);
        ratedLinear.setVisibility(View.INVISIBLE);

        popularLinear.setAnimation(animation1);
        popularLinear.setVisibility(View.INVISIBLE);

        grossingLinear.setAnimation(animation1);
        grossingLinear.setVisibility(View.INVISIBLE);
    }

    private void showFABMenu() {
        isFABOpen=true;
        AlphaAnimation animation1 = new AlphaAnimation(0, 1);
        animation1.setDuration(250);
        animation1.setStartOffset(0);
        animation1.setFillAfter(true);
        ratedLinear.setAnimation(animation1);
        ratedLinear.setVisibility(View.VISIBLE);

        popularLinear.setAnimation(animation1);
        popularLinear.setVisibility(View.VISIBLE);

        grossingLinear.setAnimation(animation1);
        grossingLinear.setVisibility(View.VISIBLE);
    }


    private void loadMovies() {
        movieList.setLayoutManager(new GridLayoutManager(getParent(), 3));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.itemOffset);
        movieList.addItemDecoration(itemDecoration);

        moviesRepository.getMovies(new OnGetMoviesCallback() {
            @Override
            public void onSuccess(List<Movie> movies) {
                mAdapter = new MovieAdapter(movies, getApplicationContext());
                movieList.setAdapter(mAdapter);
            }

            @Override
            public void onError() {
                Toast.makeText(MainActivity.this,
                        "Error Loading Movies",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });



    }

//    static class MoviesAsync extends AsyncTask<Void, Void, Void> {
//
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            loadMovies();
//            return null;
//        }
//    }
}
