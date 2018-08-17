package silva.davidson.com.br.famousmovies.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import silva.davidson.com.br.famousmovies.data.Movie;

public interface MovieDataSource {

    interface LoadMoviesCallback {

        void onMoviesLoaded(List<Movie> movies);

        void onDataNotAvailable();
    }

    interface GetMovieCallback {

        void onMovieLoaded(Movie movie);

        void onDataNotAvailable();
    }

    void getMovies(@NonNull LoadMoviesCallback callback);

    void getMovie(@NonNull String movieId, @NonNull GetMovieCallback callback);

    void saveMovie(@NonNull Movie movie);

    void deleteMovie(@NonNull Movie movie);

}
