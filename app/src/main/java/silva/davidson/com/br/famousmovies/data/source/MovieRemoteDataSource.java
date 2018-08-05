package silva.davidson.com.br.famousmovies.data.source;

import android.support.annotation.NonNull;

import silva.davidson.com.br.famousmovies.data.source.remote.MovieResponse;
import silva.davidson.com.br.famousmovies.data.source.remote.ReviewResponse;
import silva.davidson.com.br.famousmovies.data.source.remote.VideosResponse;

public interface MovieRemoteDataSource {

    interface GetMovieReviewCallBack {

        void onReviewsSuccess(ReviewResponse response);

        void onReviewsFailrue(Throwable error);
    }

    interface GetMoviesTrailersCallBack {

        void onTrailersSuccess(VideosResponse response);

        void onTrailersFailure(Throwable error);
    }

    interface GetMoviesCallBack {

        void onMovieSuccess(MovieResponse response);

        void onMovieFailure(Throwable error);
    }

    void getMovieReview(int id, @NonNull GetMovieReviewCallBack callBack);

    void getMovieVideos(int id, @NonNull GetMoviesTrailersCallBack callBack);

    void getMovies(@NonNull int page, @NonNull GetMoviesCallBack callBack);

    void getTopRatedMovies(int page, @NonNull GetMoviesCallBack callBack);

    void getMostPopularMovies(int page, @NonNull GetMoviesCallBack callBack);

}
