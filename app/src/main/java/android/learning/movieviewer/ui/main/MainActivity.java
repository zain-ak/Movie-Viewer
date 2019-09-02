package android.learning.movieviewer.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.learning.movieviewer.R;
import android.learning.movieviewer.adapter.MovieAdapter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
