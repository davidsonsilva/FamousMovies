package silva.davidson.com.br.famousmovies.service;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

import silva.davidson.com.br.famousmovies.BuildConfig;

public abstract class MovieDBService {

    private static final String API_KEY = BuildConfig.API_KEY;

    private static final String BASE_URL_API = "https://api.themoviedb.org/3/";
    private static final String POPULAR_MOVIES = "movie/popular?api_key=" + API_KEY;
    private static final String TOP_RATED = "movie/top_rated?api_key=" + API_KEY;

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getBaseUrlApi() {
        return BASE_URL_API;
    }

    public static String getPopularMovies() {
        return BASE_URL_API + POPULAR_MOVIES;
    }

    public static String getTopRated() {
        return BASE_URL_API + TOP_RATED;
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
