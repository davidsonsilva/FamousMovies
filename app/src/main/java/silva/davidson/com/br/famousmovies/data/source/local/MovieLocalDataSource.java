package silva.davidson.com.br.famousmovies.data.source.local;

import android.support.annotation.NonNull;
import android.util.Log;

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
        mExecutorService.execute(() -> callback.onMoviesLoaded(mMovieDao.getAllFavoriteMovies().getValue()));

    }

    @Override
    public void getMovie(@NonNull String movieId, @NonNull GetMovieCallback callback) {

    }

    @Override
    public void saveMovie(@NonNull final Movie movie) {
        mExecutorService.execute(() -> mMovieDao.insertFavoriteMovie(movie));
        Log.e("saveMovie", "Movie add to favorite");

    }

    @Override
    public void deleteMovie(@NonNull String movieId) {
        mExecutorService.execute(() -> mMovieDao.deleteFavoriteMovie(Integer.valueOf(movieId)));
        Log.e("deleteMovie", "Movie remove to favorite");

    }
}
