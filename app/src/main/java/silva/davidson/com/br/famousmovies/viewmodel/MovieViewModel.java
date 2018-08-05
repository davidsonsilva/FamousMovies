package silva.davidson.com.br.famousmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import silva.davidson.com.br.famousmovies.data.Movie;
import silva.davidson.com.br.famousmovies.data.source.MovieRemoteDataSource;
import silva.davidson.com.br.famousmovies.data.source.remote.MovieRemoteApi;
import silva.davidson.com.br.famousmovies.data.source.remote.MovieResponse;
import silva.davidson.com.br.famousmovies.interfaces.ReviewListener;
import silva.davidson.com.br.famousmovies.interfaces.VideosListener;
import silva.davidson.com.br.famousmovies.model.MoviesFilterType;
import silva.davidson.com.br.famousmovies.model.Review;
import silva.davidson.com.br.famousmovies.model.Videos;
import silva.davidson.com.br.famousmovies.data.source.local.MovieDatabase;
import silva.davidson.com.br.famousmovies.data.source.remote.ReviewResponse;
import silva.davidson.com.br.famousmovies.data.source.remote.VideosResponse;

public class MovieViewModel extends AndroidViewModel {

    private MovieDatabase mDb;
    private MovieRemoteApi mMovieApi;
    private MutableLiveData<List<Movie>> mMovies = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> mMoviesTopRated = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> mMoviesMostPopular = new MutableLiveData<>();
    private MutableLiveData<List<Videos>> mVideos =  new MutableLiveData<>();
    private MutableLiveData<List<Review>> mReviews = new MutableLiveData<>();

    public MovieViewModel(@NonNull Application application, MovieDatabase db) {
        super(application);
        mDb = db;
        mMovieApi = MovieRemoteApi.getInstance();
    }

    public void start() {
        loadMovies();
    }

    private void loadMovies(){
        loadMovies(MoviesFilterType.MOVIE_TYPE_MOST_POPULAR);
    }

    public void loadMovies(MoviesFilterType type) {
        switch (type){
            case MOVIE_TYPE_MOST_POPULAR :{
                getMostPopularMovies();
                break;
            }
            case MOVIE_TYPE_TOP_RATED : {
                getTopRatedMovies();
                break;
            }
            default:break;
        }

    }

    public MutableLiveData<List<Movie>> getMovies() {
        return mMovies;
    }

    public LiveData<List<Movie>> getMoviesMostPopular() {
        return mMoviesMostPopular;
    }

    public MutableLiveData<List<Review>> getReviews() {
        return mReviews;
    }

    public MutableLiveData<List<Videos>> getVideos() {
        return mVideos;
    }

    private void getMostPopularMovies(){
       mMovieApi.getMostPopularMovies(1, new MovieRemoteDataSource.GetMoviesCallBack() {
           @Override
           public void onMovieSuccess(MovieResponse response) {
               //mMoviesMostPopular.setValue(response.getMovies());
               mMovies.setValue(response.getMovies());
           }

           @Override
           public void onMovieFailure(Throwable error) {
               Log.e("getMostPopularMovies", error.getMessage());
           }
       });
    }

    private void getTopRatedMovies(){
        mMovieApi.getTopRatedMovies(1, new MovieRemoteDataSource.GetMoviesCallBack() {
            @Override
            public void onMovieSuccess(MovieResponse response) {
                //mMoviesTopRated.setValue(response.getMovies());
                mMovies.setValue(response.getMovies());
            }

            @Override
            public void onMovieFailure(Throwable error) {
                Log.e("getTopRatedMovies", error.getMessage());
            }
        });
    }


    public void getReviews(int movieId) {
        mMovieApi.getMovieReview(movieId, new MovieRemoteDataSource.GetMovieReviewCallBack() {
            @Override
            public void onReviewsSuccess(ReviewResponse response) {
                mReviews.setValue(response.getReviews());
            }

            @Override
            public void onReviewsFailrue(Throwable error) {
                Log.e("getReviews", error.getMessage());
            }
        });
    }

    public void getTrailers(int movieId){
        mMovieApi.getMovieVideos(movieId, new MovieRemoteDataSource.GetMoviesTrailersCallBack() {
            @Override
            public void onTrailersSuccess(VideosResponse response) {
                mVideos.setValue(response.getVideos());
            }

            @Override
            public void onTrailersFailure(Throwable error) {
                Log.e("getTrailers", error.getMessage());
            }
        });
    }

}
