package android.learning.movieviewer.ui.main;

import android.learning.movieviewer.R;
import android.learning.movieviewer.data.model.Movie;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MovieFragment extends Fragment {
    Movie movie;
    TextView movieName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        movie = bundle.getParcelable("movie_item");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_card, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP){
                    Toast.makeText(v.getContext(), "Moving up", Toast.LENGTH_SHORT).show();
                    Toast.makeText(v.getContext(), movie.getName(), Toast.LENGTH_SHORT).show();
                }

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Toast.makeText(v.getContext(), "Moving down", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        return view;
    }
}
