package silva.davidson.com.br.famousmovies;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

import silva.davidson.com.br.famousmovies.adapters.MovieAdapter;
import silva.davidson.com.br.famousmovies.base.BaseActivity;
import silva.davidson.com.br.famousmovies.model.Movie;
import silva.davidson.com.br.famousmovies.service.FetchMovies;
import silva.davidson.com.br.famousmovies.service.MovieDBService;
import silva.davidson.com.br.famousmovies.utilities.NetworkUtils;

public class MainActivity extends BaseActivity {

    private GridView mGridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridview = findViewById(R.id.grid_view);

        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Movie movie = (Movie) parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, movie.getTitle() + " - " + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        if (verifyConnection()) {
            //Defaul Value for begin
            fetchMovies(MovieDBService.getPopularMovies());
        } else {
            Toast.makeText(this, "No connection aviable !", Toast.LENGTH_LONG).show();
        }

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
                fetchMovies(MovieDBService.getPopularMovies());
            } else if (itemSelected == R.id.order_by_top_rated) {
                fetchMovies(MovieDBService.getTopRated());
            }
        } else {
            Toast.makeText(this, "No connection aviable !", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchMovies(String type) {
        FetchMovies fetchMovies = new FetchMovies();
        try {
            List<Movie> mMovies =  fetchMovies.execute(MovieDBService.buildUrl(type)).get();
            mGridview.setAdapter(new MovieAdapter(this, mMovies));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    private boolean verifyConnection() {
        return NetworkUtils.networkStatus(this);
    }
}
