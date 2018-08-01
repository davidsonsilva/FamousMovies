package silva.davidson.com.br.famousmovies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import silva.davidson.com.br.famousmovies.adapters.MovieAdapter;
import silva.davidson.com.br.famousmovies.base.BaseActivity;
import silva.davidson.com.br.famousmovies.behaviors.BottomNavigationViewBehavior;
import silva.davidson.com.br.famousmovies.interfaces.AsyncTaskDelegate;
import silva.davidson.com.br.famousmovies.interfaces.ReviewListener;
import silva.davidson.com.br.famousmovies.model.Movie;
import silva.davidson.com.br.famousmovies.model.Review;
import silva.davidson.com.br.famousmovies.rest.MovieApi;
import silva.davidson.com.br.famousmovies.rest.ReviewResponse;
import silva.davidson.com.br.famousmovies.service.FetchMovies;
import silva.davidson.com.br.famousmovies.service.MovieDBService;
import silva.davidson.com.br.famousmovies.ui.MovieDetailActivity;
import silva.davidson.com.br.famousmovies.utilities.NetworkUtils;
import silva.davidson.com.br.famousmovies.utilities.PicassoImageLoader;

public class MainActivity extends BaseActivity implements AsyncTaskDelegate,
        BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.grid_view)
    GridView mGridView;
    @BindView(R.id.navigation)
    BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Movie movie = (Movie) parent.getItemAtPosition(position);
                MovieDetailActivity.startActivity(MainActivity.this, movie);
            }
        });

        if (verifyConnection()) {
            //Default Value for begin
            fetchMovies(MovieDBService.getPopularMovies());
        } else {
            Toast.makeText(this, R.string.no_conection_text, Toast.LENGTH_LONG).show();
        }
        setTitle(R.string.app_name);

        /*CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mBottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());*/

        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
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
                fetchMovies(MovieDBService.getPopularMovies());
            } else if (itemSelected == R.id.order_by_top_rated) {
                setTitle(R.string.top_rated_text);
                fetchMovies(MovieDBService.getTopRated());

            } else {
                    Toast.makeText(this, R.string.no_conection_text, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, R.string.no_conection_text, Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchMovies(String type) {
        FetchMovies fetchMovies = new FetchMovies(this, this);
        fetchMovies.execute(MovieDBService.buildUrl(type));
    }


    private boolean verifyConnection() {
        return NetworkUtils.networkStatus(this);
    }

    @Override
    public void processFinish(Object output) {
        if(output != null){
            //Recupero a lista retornada pelo asynctask
            List<Movie> movies = (List<Movie>) output;
            //atualizo o grid
            mGridView = findViewById(R.id.grid_view);
            mGridView.setAdapter(new MovieAdapter(this, movies, new PicassoImageLoader()));
        }else{
            Toast.makeText(this, R.string.no_results_error , Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemSelected = item.getItemId();
          if (verifyConnection()) {
                if (itemSelected == R.id.navigation_popular) {
                    setTitle(R.string.most_popular_text);
                    fetchMovies(MovieDBService.getPopularMovies());
                } else if (itemSelected == R.id.navigation_rated) {
                    setTitle(R.string.top_rated_text);
                    fetchMovies(MovieDBService.getTopRated());
                } else if (itemSelected == R.id.navigation_favorites){
                    Snackbar.make(this.mBottomNavigationView, item.getTitle(),
                            Snackbar.LENGTH_LONG).show();
                }
            } else {
              Snackbar.make(this.mBottomNavigationView, R.string.no_conection_text,
                      Snackbar.LENGTH_LONG).show();
                //Toast.makeText(this, R.string.no_conection_text, Toast.LENGTH_LONG).show();
            }

        return super.onOptionsItemSelected(item);

    }
}



