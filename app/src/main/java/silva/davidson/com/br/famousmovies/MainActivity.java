package silva.davidson.com.br.famousmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import silva.davidson.com.br.famousmovies.adapters.MoviesRecycleViewAdapter;
import silva.davidson.com.br.famousmovies.base.BaseActivity;
import silva.davidson.com.br.famousmovies.behaviors.BottomNavigationBehavior;
import silva.davidson.com.br.famousmovies.data.Movie;
import silva.davidson.com.br.famousmovies.factory.ViewModelFactory;
import silva.davidson.com.br.famousmovies.interfaces.ItemClickListener;
import silva.davidson.com.br.famousmovies.model.MoviesFilterType;
import silva.davidson.com.br.famousmovies.ui.MovieDetailActivity;
import silva.davidson.com.br.famousmovies.ui.MovieFavoritesActivity;
import silva.davidson.com.br.famousmovies.utilities.NetworkUtils;
import silva.davidson.com.br.famousmovies.utilities.PicassoImageLoader;
import silva.davidson.com.br.famousmovies.viewmodel.MovieViewModel;

public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        ItemClickListener {

    @BindView(R.id.recycler_movies)
    RecyclerView mRecyclerView;
    @BindView(R.id.navigation)
    BottomNavigationView mBottomNavigationView;
    private MovieViewModel movieViewModel;
    private MoviesRecycleViewAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setHasFixedSize(true);
        setupViewModel();

        setTitle(R.string.app_name);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)
                mBottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        if (verifyConnection()) {
            movieViewModel.start();
        } else {
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.no_conection_text,
                    Snackbar.LENGTH_LONG).show();
        }

    }

    private void setupViewModel() {
        final ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        movieViewModel = ViewModelProviders.of(this, factory).get(MovieViewModel.class);

        movieViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if(movies != null) {
                    movieAdapter = new MoviesRecycleViewAdapter(MainActivity.this, movies,
                            new PicassoImageLoader());
                    movieAdapter.setClickListener(MainActivity.this);
                    mRecyclerView.setAdapter(movieAdapter);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemSelected = item.getItemId();

        if (verifyConnection()) {
            if (itemSelected == R.id.order_by_most_popular) {
                setTitle(R.string.most_popular_text);
                movieViewModel.loadMovies(MoviesFilterType.MOVIE_TYPE_MOST_POPULAR);
            } else if (itemSelected == R.id.order_by_top_rated) {
                setTitle(R.string.top_rated_text);
                movieViewModel.loadMovies(MoviesFilterType.MOVIE_TYPE_TOP_RATED);
            }
        } else {
            openMyFavoriteMovies();
            /*Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.no_conection_text,
                    Snackbar.LENGTH_LONG).show();*/
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean verifyConnection() {
        return NetworkUtils.networkStatus(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemSelected = item.getItemId();
          if (verifyConnection()) {
                if (itemSelected == R.id.navigation_popular) {
                    setTitle(R.string.most_popular_text);
                    movieViewModel.loadMovies(MoviesFilterType.MOVIE_TYPE_MOST_POPULAR);
                } else if (itemSelected == R.id.navigation_rated) {
                    setTitle(R.string.top_rated_text);
                    movieViewModel.loadMovies(MoviesFilterType.MOVIE_TYPE_TOP_RATED);
                } else if (itemSelected == R.id.navigation_favorites){
                    openMyFavoriteMovies();
                }
            } else {
              Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.no_conection_text,
                      Snackbar.LENGTH_LONG).show();
            }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(Movie movie) {
        MovieDetailActivity.startActivity(this, movie);
    }

    private void openMyFavoriteMovies(){
        startActivity(new Intent(getApplicationContext(), MovieFavoritesActivity.class));

        //MovieFavoritesActivity.startActivity(this);
    }
}



