package silva.davidson.com.br.famousmovies.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import silva.davidson.com.br.famousmovies.R;
import silva.davidson.com.br.famousmovies.adapters.MoviesRecycleViewAdapter;
import silva.davidson.com.br.famousmovies.base.BaseFragment;
import silva.davidson.com.br.famousmovies.data.Movie;
import silva.davidson.com.br.famousmovies.factory.ViewModelFactory;
import silva.davidson.com.br.famousmovies.interfaces.ItemClickListener;
import silva.davidson.com.br.famousmovies.model.MoviesFilterType;
import silva.davidson.com.br.famousmovies.utilities.PicassoImageLoader;
import silva.davidson.com.br.famousmovies.viewmodel.MovieViewModel;

public class FragmentTopRated extends BaseFragment implements ItemClickListener {

    private MovieViewModel mMovieViewModel;
    private MoviesRecycleViewAdapter movieAdapter;
    @BindView(R.id.recycler_movies)
    RecyclerView mRecyclerView;

    public static FragmentTopRated getInstance(){
        return new FragmentTopRated();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
        mMovieViewModel = ViewModelProviders.of(this, factory).get(MovieViewModel.class);
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.setHasFixedSize(true);

        mMovieViewModel.loadMovies(MoviesFilterType.MOVIE_TYPE_TOP_RATED);

        mMovieViewModel.getMoviesTopRated().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if(movies != null) {
                    movieAdapter = new MoviesRecycleViewAdapter(movies,
                            new PicassoImageLoader());
                    movieAdapter.setClickListener(FragmentTopRated.this);
                    movieAdapter.setMovieViewModel(mMovieViewModel);
                    mRecyclerView.setAdapter(movieAdapter);
                }
            }
        });

    }

    @Override
    public void onClick(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_RECORD, movie);
        startActivity(new Intent(getContext(), MovieDetailActivity.class).putExtras(bundle));
    }

    private void refreshData(){
        mMovieViewModel.loadMovies(MoviesFilterType.MOVIE_TYPE_TOP_RATED);
    }
}
