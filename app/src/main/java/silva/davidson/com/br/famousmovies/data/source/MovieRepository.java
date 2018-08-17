package silva.davidson.com.br.famousmovies.data.source;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import silva.davidson.com.br.famousmovies.data.Movie;

public class MovieRepository implements MovieDataSource {

    private volatile static MovieRepository INSTANCE = null;

    private final MovieDataSource mMoviesLocalDataSource;

    public MovieRepository(@NonNull MovieDataSource moviesLocalDataSource) {
        this.mMoviesLocalDataSource = moviesLocalDataSource;
    }

    public static MovieRepository getInstance(MovieDataSource moviesLocalDataSource) {
        if (INSTANCE == null) {
            synchronized (MovieRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieRepository(moviesLocalDataSource);
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public void getMovies(@NonNull LoadMoviesCallback callback) {

    }

    @Override
    public void getMovie(@NonNull String movieId, @NonNull GetMovieCallback callback) {

    }

    @Override
    public void saveMovie(@NonNull Movie movie) {

    }

    @Override
    public void deleteMovie(@NonNull Movie movie) {

    }

   }
