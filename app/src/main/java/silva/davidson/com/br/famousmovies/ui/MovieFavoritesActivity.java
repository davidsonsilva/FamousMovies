package silva.davidson.com.br.famousmovies.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import silva.davidson.com.br.famousmovies.R;
import silva.davidson.com.br.famousmovies.adapters.MovieAdapter;
import silva.davidson.com.br.famousmovies.adapters.MoviesRecycleViewAdapter;
import silva.davidson.com.br.famousmovies.base.BaseActivity;
import silva.davidson.com.br.famousmovies.data.Movie;
import silva.davidson.com.br.famousmovies.factory.ViewModelFactory;
import silva.davidson.com.br.famousmovies.interfaces.ItemClickListener;
import silva.davidson.com.br.famousmovies.utilities.PicassoImageLoader;
import silva.davidson.com.br.famousmovies.viewmodel.MovieViewModel;

public class MovieFavoritesActivity extends BaseActivity implements ItemClickListener {

    private static final String BUNDLE_RECORD = "MovieRecord";
    private static final String TAG = "MovieFavoritesActivity";
    private MovieViewModel movieViewModel;
    private MoviesRecycleViewAdapter movieAdapter;
    private RecyclerView mRecyclerView;

    public static void startActivity (BaseActivity activity, Movie record){
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_RECORD, record);
        activity.startActivity(new Intent(activity, MovieFavoritesActivity.class).putExtras(bundle));
    }

    public static void startActivity (BaseActivity activity){
        activity.startActivity(new Intent(activity, MovieFavoritesActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_collapsing_toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.favorite_movies_title));

        mRecyclerView = findViewById(R.id.favorite_movie_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setHasFixedSize(true);


        final ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        movieViewModel = ViewModelProviders.of(this, factory).get(MovieViewModel.class);

        movieViewModel.getFavoriteMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieAdapter =  new MoviesRecycleViewAdapter(MovieFavoritesActivity.this,
                        movies, new PicassoImageLoader());
                movieAdapter.setClickListener(MovieFavoritesActivity.this);
                mRecyclerView.setAdapter(movieAdapter);
            }
        });

    }

    @Override
    public void onClick(Movie movie) {
        MovieDetailActivity.startActivity(this, movie);
    }
}
