package silva.davidson.com.br.famousmovies.data.source.local;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutorService;

import silva.davidson.com.br.famousmovies.data.Movie;
import silva.davidson.com.br.famousmovies.data.source.MovieDataSource;

public class MovieLocalDataSource implements MovieDataSource {

    private static volatile MovieLocalDataSource INSTANCE;
    private MovieDao mMovieDao;
    private ExecutorService mExecutorService;

    public static MovieLocalDataSource getInstance(@NonNull ExecutorService executorService,
                                                   @NonNull MovieDao movieDao) {
        if (INSTANCE == null) {
            synchronized (MovieLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieLocalDataSource(executorService, movieDao);
                }
            }
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private MovieLocalDataSource(@NonNull ExecutorService executorService,
                                 @NonNull MovieDao movieDao) {
        mExecutorService = executorService;
        mMovieDao = movieDao;
    }

    @Override
    public void getMovies(@NonNull LoadMoviesCallback callback) {
        final List<Movie> movies = mMovieDao.getAllFavoriteMovies().getValue();
        if (movies.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onMoviesLoaded(movies);
        }
    }

    @Override
    public void getMovie(@NonNull String movieId, @NonNull GetMovieCallback callback) {

    }

    @Override
    public void saveMovie(@NonNull final Movie movie) {
        mExecutorService.execute(() -> mMovieDao.insertFavoriteMovie(movie));
    }

    @Override
    public void deleteMovie(@NonNull String movieId) {
        mExecutorService.execute(() -> mMovieDao.deleteFavoriteMovie(Integer.valueOf(movieId)));
    }
}
