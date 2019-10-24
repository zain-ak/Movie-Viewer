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
import android.widget.FrameLayout;
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
    private boolean isFABOpen = false;

    private GridLayoutManager manager = null;
    private MovieAdapter mAdapter = null;
    private RecyclerView movieList = null;
    private MoviesRepository moviesRepository;
    private boolean isFetchingMovies = false;
    private int currentPage = 0;
    private String currentGenre = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup RecyclerView
        movieList = findViewById(R.id.movieListHome);
        manager = new GridLayoutManager(getParent(), 3);
        movieList.setLayoutManager(manager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.itemOffset);
        movieList.addItemDecoration(itemDecoration);

        //Setup FloatingActionButtons & LinearLayouts containing them
        mFloatingActionButton = findViewById(R.id.fab_sort_by);
        mRatedFAB = findViewById(R.id.fab_highest_rated);
        mPopularFAB = findViewById(R.id.fab_most_popular);
        mGrossingFAB = findViewById(R.id.fab_highest_grossing);
        ratedLinear = findViewById(R.id.linear_rated);
        ratedLinear.bringToFront();
        ratedLinear.setVisibility(View.INVISIBLE);
        popularLinear = findViewById(R.id.linear_popular);
        popularLinear.bringToFront();
        popularLinear.setVisibility(View.INVISIBLE);
        grossingLinear = findViewById(R.id.linear_grossing);
        grossingLinear.bringToFront();
        grossingLinear.setVisibility(View.INVISIBLE);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        mRatedFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loadBestRatedMovies();
            }
        });

        mPopularFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Popular", "Function Running");
                currentPage = 0;
                loadPopularMovies(currentPage);
            }
        });

        mGrossingFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // loadHighestGrossingMovies();
            }
        });

        moviesRepository = MoviesRepository.getInstance();

        setupOnScrollListener();
        loadPopularMovies(currentPage + 1);
    }

    private void setupOnScrollListener() {

        movieList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //Scrolling down
                if (dy > 0 && mFloatingActionButton.getVisibility() == View.VISIBLE) {
                    AlphaAnimation animation1 = new AlphaAnimation(1, 0);
                    animation1.setDuration(250);
                    animation1.setStartOffset(0);
                    animation1.setFillAfter(true);
                    mFloatingActionButton.setAnimation(animation1);
                    grossingLinear.setAnimation(animation1);
                    ratedLinear.setAnimation(animation1);
                    popularLinear.setAnimation(animation1);
                    mFloatingActionButton.setVisibility(View.INVISIBLE);
                    grossingLinear.setVisibility(View.INVISIBLE);
                    ratedLinear.setVisibility(View.INVISIBLE);
                    popularLinear.setVisibility(View.INVISIBLE);
                } else if (dy < 0 && mFloatingActionButton.getVisibility() != View.VISIBLE) {   //Scrolling up
                    AlphaAnimation animation1 = new AlphaAnimation(0, 1);
                    animation1.setDuration(250);
                    animation1.setStartOffset(0);
                    animation1.setFillAfter(true);
                    mFloatingActionButton.setAnimation(animation1);
                    grossingLinear.setAnimation(animation1);
                    ratedLinear.setAnimation(animation1);
                    popularLinear.setAnimation(animation1);
                    mFloatingActionButton.setVisibility(View.VISIBLE);
                    grossingLinear.setVisibility(View.VISIBLE);
                    ratedLinear.setVisibility(View.VISIBLE);
                    popularLinear.setVisibility(View.VISIBLE);
                }

                int totalItemCount = manager.getItemCount();
                int visibleItemCount = manager.getChildCount();
                int firstVisibleItem = manager.findFirstVisibleItemPosition();

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    if (!isFetchingMovies && currentGenre.equals("popular")) {
                        loadPopularMovies(currentPage + 1);
                    }
//                    if (!isFetchingMovies && currentGenre.equals("rated")) {
//                        (currentPage + 1);
//                    }
//                    if (!isFetchingMovies && currentGenre.equals("grossing")) {
//                        loadPopularMovies(currentPage + 1);
//                    }
                }
            }
        });
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


//    private void loadBestRatedMovies() {
//        movieList.setLayoutManager(new GridLayoutManager(getParent(), 3));
//        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.itemOffset);
//        movieList.addItemDecoration(itemDecoration);
//
//        moviesRepository.getBestRatedMovies(new OnGetMoviesCallback() {
//            @Override
//            public void onSuccess(int page, List<Movie> movies) {
//                mAdapter = new MovieAdapter(movies, getApplicationContext());
//                movieList.setAdapter(mAdapter);
//            }
//
//            @Override
//            public void onError() {
//                Toast.makeText(MainActivity.this,
//                        "Error Loading Movies",
//                        Toast.LENGTH_SHORT)
//                        .show();
//            }
//        });
//
//
//
//    }

    private void loadPopularMovies(int page) {
        isFetchingMovies = true;
        moviesRepository.getPopularMovies(page, new OnGetMoviesCallback() {
            @Override
            public void onSuccess(int page, List<Movie> movies) {
                if (mAdapter == null) {
                    mAdapter = new MovieAdapter(movies, getApplicationContext());
                    movieList.setAdapter(mAdapter);
                }
                else {
                    mAdapter.appendMovies(movies);
                }
                currentPage = page;
                isFetchingMovies = false;

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

//    private void loadHighestGrossingMovies() {
//        movieList.setLayoutManager(new GridLayoutManager(getParent(), 3));
//        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.itemOffset);
//        movieList.addItemDecoration(itemDecoration);
//
//        moviesRepository.getHighestGrossing(1, new OnGetMoviesCallback() {
//            @Override
//            public void onSuccess(List<Movie> movies) {
//                mAdapter = new MovieAdapter(movies, getApplicationContext());
//                movieList.setAdapter(mAdapter);
//            }
//
//            @Override
//            public void onError() {
//                Toast.makeText(MainActivity.this,
//                        "Error Loading Movies",
//                        Toast.LENGTH_SHORT)
//                        .show();
//            }
//        });
//
//    }

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
