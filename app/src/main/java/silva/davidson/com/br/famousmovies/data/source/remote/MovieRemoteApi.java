package silva.davidson.com.br.famousmovies.data.source.remote;

import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import silva.davidson.com.br.famousmovies.BuildConfig;
import silva.davidson.com.br.famousmovies.data.source.MovieRemoteDataSource;
import silva.davidson.com.br.famousmovies.interfaces.MovieService;
import silva.davidson.com.br.famousmovies.rest.MovieApi;

public class MovieRemoteApi implements MovieRemoteDataSource {

    private static MovieRemoteApi INSTANCE;
    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String ENDPOINT = "https://api.themoviedb.org/3/";
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit mRretrofit;
    private static MovieService sService;


    public static MovieRemoteApi getInstance(){
        if (INSTANCE == null) {
            synchronized (MovieRemoteApi.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieRemoteApi();
                }
            }
        }
        return INSTANCE;
    }

    private MovieRemoteApi(){
        sService = getRetrofit().create(MovieService.class);
    }

    private Retrofit getRetrofit() {
        if(mRretrofit == null) {
            mRretrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRretrofit;
    }


    @Override
    public void getMovieReview(int id, @NonNull GetMovieReviewCallBack callBack) {

        sService.getMovieReview(id, API_KEY).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call,
                                   @NonNull Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    callBack.onReviewsSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResponse> call, @NonNull Throwable t) {
                callBack.onReviewsFailrue(t);
            }
        });
    }

    @Override
    public void getMovieVideos(int id, @NonNull GetMoviesTrailersCallBack callBack) {
        sService.getVideos(id, API_KEY).enqueue(new Callback<VideosResponse>() {
            @Override
            public void onResponse(@NonNull Call<VideosResponse> call,
                                   @NonNull Response<VideosResponse> response) {
                if(response.isSuccessful()) {
                    callBack.onTrailersSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<VideosResponse> call, @NonNull Throwable t) {
                callBack.onTrailersFailure(t);
            }
        });
    }

    @Override
    public void getMovies(@NonNull int page, @NonNull GetMoviesCallBack callBack) {

    }

    @Override
    public void getTopRatedMovies(int page, @NonNull GetMoviesCallBack callBack) {
        sService.getTopRatedMovies(API_KEY, "", 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if(response.isSuccessful()){
                    callBack.onMovieSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, Throwable t) {
                 callBack.onMovieFailure(t);
            }
        });
    }

    @Override
    public void getMostPopularMovies(int page, @NonNull GetMoviesCallBack callBack) {
          sService.getPopularMovies(API_KEY, "", 1).enqueue(new Callback<MovieResponse>() {
              @Override
              public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                  if(response.isSuccessful()) {
                      callBack.onMovieSuccess(response.body());
                  }
              }

              @Override
              public void onFailure(@NonNull Call<MovieResponse> call, Throwable t) {
                  callBack.onMovieFailure(t);
              }
          });
    }
}
