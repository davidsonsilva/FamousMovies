package silva.davidson.com.br.famousmovies.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import silva.davidson.com.br.famousmovies.model.TopRated;

public interface MovieService {

    @GET("top_rated")
    Call<TopRated> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pageIndex
    );
}