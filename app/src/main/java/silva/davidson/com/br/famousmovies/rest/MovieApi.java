package silva.davidson.com.br.famousmovies.rest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import okhttp3.OkHttpClient.Builder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import silva.davidson.com.br.famousmovies.BuildConfig;
import silva.davidson.com.br.famousmovies.data.source.remote.ReviewResponse;
import silva.davidson.com.br.famousmovies.data.source.remote.VideosResponse;
import silva.davidson.com.br.famousmovies.interfaces.MovieService;
import silva.davidson.com.br.famousmovies.interfaces.ReviewListener;
import silva.davidson.com.br.famousmovies.interfaces.VideosListener;

public class MovieApi {

    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String ENDPOINT = "https://api.themoviedb.org/3/";
    private Builder httpClient = new Builder();
    private static Retrofit retrofit;
    private static MovieService sService;
    private Context mContext;

    private  volatile static MovieApi INSTANCE = null;

    public static MovieApi getInstance(@NonNull ReviewListener responderReview,
                                       @NonNull VideosListener responderVideos){
        if (INSTANCE == null) {
            synchronized (MovieApi.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieApi(responderReview, responderVideos);
                }
            }
        }
        return INSTANCE;
    }

    private ReviewListener reviewListenerDelegate;
    private VideosListener videosListenerDelegate;

    private MovieApi(ReviewListener responderReview,
                     VideosListener responderVideos){
        sService = getInstanceRetrofit().create(MovieService.class);
        this.reviewListenerDelegate = responderReview;
        this.videosListenerDelegate = responderVideos;
    }

    private Retrofit getInstanceRetrofit() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public void getMovieReview(int id) {
            sService.getMovieReview(id, API_KEY).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call,
                                   @NonNull Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    reviewListenerDelegate.success(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResponse> call, @NonNull Throwable t) {
                Toast.makeText(mContext, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getMovieVideos(int id){
        sService.getVideos(id, API_KEY).enqueue(new Callback<VideosResponse>() {
            @Override
            public void onResponse(@NonNull Call<VideosResponse> call,
                                   @NonNull Response<VideosResponse> response) {
                if(response.isSuccessful()) {
                    videosListenerDelegate.success(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<VideosResponse> call, @NonNull Throwable t) {
                Toast.makeText(mContext, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}