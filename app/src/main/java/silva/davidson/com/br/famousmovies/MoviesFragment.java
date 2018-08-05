package silva.davidson.com.br.famousmovies;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import silva.davidson.com.br.famousmovies.base.BaseFragment;
import silva.davidson.com.br.famousmovies.data.Movie;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends BaseFragment {

    private Movie mMovie;


    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

}
