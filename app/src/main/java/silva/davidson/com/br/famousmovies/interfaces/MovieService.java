package silva.davidson.com.br.famousmovies.interfaces;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import silva.davidson.com.br.famousmovies.data.Movie;
import silva.davidson.com.br.famousmovies.data.source.remote.MovieResponse;
import silva.davidson.com.br.famousmovies.data.source.remote.ReviewResponse;
import silva.davidson.com.br.famousmovies.data.source.remote.VideosResponse;

public interface MovieService {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pageIndex
    );

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pageIndex
    );

    @GET("movie/{id}/reviews")
    Call<ReviewResponse> getMovieReview(
            @Path("id") int movieId,
            @Query("api_key") String apiKey
    );

    @GET("movie/{id}/videos")
    Call<VideosResponse> getVideos(@Path("id") int movieId,
                                   @Query("api_key") String apiKey

    );
}