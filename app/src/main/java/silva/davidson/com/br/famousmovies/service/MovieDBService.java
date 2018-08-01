package silva.davidson.com.br.famousmovies.service;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.net.MalformedURLException;
import java.net.URL;

import silva.davidson.com.br.famousmovies.BuildConfig;

public abstract class MovieDBService {

    private static final String API_KEY = BuildConfig.API_KEY;

    private static final String BASE_URL_API = "https://api.themoviedb.org/3/";
    private static final String POPULAR_MOVIES = "movie/popular?api_key=" + API_KEY;
    private static final String TOP_RATED = "movie/top_rated?api_key=" + API_KEY;
    private static final String IMAGE_YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";
    private static final String IMAGE_YOUTUBE_THUMBNAIL_BASE_URL = "https://img.youtube.com/vi/%s/0.jpg";
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";
    private static final String WEB_URL = "https://www.themoviedb.org/movie/";

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
        return IMAGE_YOUTUBE_BASE_URL.concat(youtubeId);
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
