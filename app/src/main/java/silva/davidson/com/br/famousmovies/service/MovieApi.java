package silva.davidson.com.br.famousmovies.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class MovieApi {

    private static Retrofit retrofit = null;
    private static MovieApi instance;

    private MovieApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MovieDBService.getBaseUrlApi())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static MovieApi getInstance() {
        if(instance == null) {
            instance = new MovieApi();
        }

        return instance;
    }

    public<T> T create(Class<T> service) {
        return retrofit.create(service);
    }

    /*public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.themoviedb.org/3/")
                    .build();
        }
        return retrofit;
    }*/
}