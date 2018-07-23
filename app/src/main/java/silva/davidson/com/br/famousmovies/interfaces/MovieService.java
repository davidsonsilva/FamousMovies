package silva.davidson.com.br.famousmovies.interfaces;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import silva.davidson.com.br.famousmovies.model.Movie;
import silva.davidson.com.br.famousmovies.rest.ReviewResponse;
import silva.davidson.com.br.famousmovies.rest.VideosResponse;

public interface MovieService {
/*@GET("movie/top_rated")
    Call<Movie> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pageIndex
    );*/

    /*@GET("movie/popular")
    Call<Movie> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pageIndex
    );*/

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