package android.learning.movieviewer.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.learning.movieviewer.R;
import android.learning.movieviewer.adapter.MovieAdapter;
import android.learning.movieviewer.data.model.Movie;
import android.learning.movieviewer.data.model.MoviesRepository;
import android.learning.movieviewer.data.model.OnGetMoviesCallback;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFloatingActionButton = null;
    private LinearLayout ratedLinear = null;
    private LinearLayout popularLinear = null;
    private LinearLayout grossingLinear = null;
    private ConstraintLayout movieFragContainer = null;
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
        movieFragContainer = findViewById(R.id.movieFragmentContainer);

        //Setup RecyclerView
        movieList = findViewById(R.id.movieListHome);
        manager = new GridLayoutManager(getParent(), 3) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                lp.width= getWidth() / 3;
                lp.height = (int) (lp.width * 1.33);
                return true;
            }
        };
        movieList.setLayoutManager(manager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.itemOffset);
        movieList.addItemDecoration(itemDecoration);

        //Setup FloatingActionButtons & LinearLayouts containing them
        mFloatingActionButton = findViewById(R.id.fab_sort_by);
        FloatingActionButton mRatedFAB = findViewById(R.id.fab_highest_rated);
        FloatingActionButton mPopularFAB = findViewById(R.id.fab_most_popular);
        FloatingActionButton mGrossingFAB = findViewById(R.id.fab_highest_grossing);
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
                mAdapter = null;
                currentPage = 1;
                loadBestRatedMovies(currentPage);
            }
        });

        mPopularFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter = null;
                currentPage = 1;
                loadPopularMovies(currentPage);
            }
        });

        mGrossingFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter = null;
                currentPage = 1;
                loadHighestGrossingMovies(currentPage);
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
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //Scrolling down
                if (dy > 0 && mFloatingActionButton.getVisibility() == View.VISIBLE) {
                    AlphaAnimation animation1 = new AlphaAnimation(1, 0);
                    animation1.setDuration(250);
                    animation1.setStartOffset(0);
                    animation1.setFillAfter(true);

                    if (isFABOpen)
                        closeFABMenu();

                    mFloatingActionButton.setAnimation(animation1);
                    mFloatingActionButton.setVisibility(View.INVISIBLE);

                } else if (dy < 0 && mFloatingActionButton.getVisibility() != View.VISIBLE) {   //Scrolling up
                    AlphaAnimation animation1 = new AlphaAnimation(0, 1);
                    animation1.setDuration(250);
                    animation1.setStartOffset(0);
                    animation1.setFillAfter(true);
                    mFloatingActionButton.setAnimation(animation1);
                    mFloatingActionButton.setVisibility(View.VISIBLE);
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

    private void loadBestRatedMovies(int page) {
        isFetchingMovies = true;
        moviesRepository.getBestRatedMovies(page, new OnGetMoviesCallback() {
            @Override
            public void onSuccess(int page, List<Movie> movies) {
                if (mAdapter == null) {
                    mAdapter = new MovieAdapter(movies, getParent());
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

    private void loadPopularMovies(int page) {
        isFetchingMovies = true;
        moviesRepository.getPopularMovies(page, new OnGetMoviesCallback() {
            @Override
            public void onSuccess(int page, List<Movie> movies) {
                if (mAdapter == null) {
                    mAdapter = new MovieAdapter(movies, getParent());
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

    private void loadHighestGrossingMovies(int page) {
        isFetchingMovies = true;
        moviesRepository.getHighestGrossing(page, new OnGetMoviesCallback() {
            @Override
            public void onSuccess(int page, List<Movie> movies) {
                if (mAdapter == null) {
                    mAdapter = new MovieAdapter(movies, getParent());
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

    public void switchContent(int id, Fragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_item_animation_slide_from_bottom, 0);
        ft.replace(id, frag, frag.toString());
        ft.addToBackStack(null);
        movieFragContainer.setVisibility(View.VISIBLE);
        ft.commit();
    }
}
