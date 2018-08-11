package silva.davidson.com.br.famousmovies.rest;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

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

    private static final String BASE_URL_API = "https://api.themoviedb.org/3/";
    private static final String POPULAR_MOVIES = "movie/popular?api_key=" + API_KEY;
    private static final String TOP_RATED = "movie/top_rated?api_key=" + API_KEY;
    private static final String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";
    private static final String IMAGE_YOUTUBE_THUMBNAIL_BASE_URL = "https://img.youtube.com/vi/%s/0.jpg";
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";
    private static final String WEB_URL = "https://www.themoviedb.org/movie/";


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

    @NonNull
    public static String getApiKey() {
        return API_KEY;
    }

    @NonNull
    public static String getBaseUrlApi() {
        return BASE_URL_API;
    }

    @NonNull
    public static String getPopularMovies() {
        return BASE_URL_API + POPULAR_MOVIES;
    }

    @NonNull
    public static String getTopRated() {
        return BASE_URL_API + TOP_RATED;
    }

    @NonNull
    public static String buildImageURL(String movieId) {
        return IMAGE_BASE_URL.concat(movieId);
    }

    @NonNull
    public static String buildYoutubeUrl(String youtubeId) {
        return YOUTUBE_BASE_URL.concat(youtubeId);
    }

    @NonNull
    public static String buildYoutubeThumbnailUrl(String youtubeId) {
        return String.format(IMAGE_YOUTUBE_THUMBNAIL_BASE_URL, youtubeId);
    }

    @NonNull
    public static String buildWebURL(int movieId) {
        return WEB_URL.concat(String.valueOf(movieId));
    }

    public static URL buildUrl(String baseUrl) {
        Uri builtUri = Uri.parse(baseUrl).buildUpon().build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

}